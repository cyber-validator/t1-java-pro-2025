package tech.inno.runner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tech.inno.anno.AfterSuite;
import tech.inno.anno.BeforeSuite;
import tech.inno.anno.CsvSource;
import tech.inno.anno.Test;
import tech.inno.exception.InvalidPriorityValueException;
import tech.inno.exception.NonStaticMethodException;
import tech.inno.exception.TooMuchMethodsException;
import tech.inno.model.TestMethods;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TestRunner {

    private static final Logger LOGGER = LogManager.getLogger(TestRunner.class);

    private TestRunner() {
    }

    public static void runTests(Class<?> clazz) {
        Object testClass = createInstance(clazz);
        TestMethods allTestMethods = findAllPublicTestMethods(testClass.getClass());

        invokeMethods(allTestMethods.beforeMethods(), testClass);
        Map<Integer, List<Method>> testMethods = allTestMethods.testMethods();
        for (Map.Entry<Integer, List<Method>> entry : testMethods.entrySet()) {
            List<Method> methods = entry.getValue();
            invokeMethods(methods, testClass);
        }
        invokeMethods(allTestMethods.afterMethods(), testClass);
    }

    private static Object createInstance(Class<?> clazz) {
        try {
            Class<?> c = Class.forName(clazz.getName());
            return c.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void invokeMethods(List<Method> methods, Object instance) {
        for (Method m : methods) {
            try {
                runMethod(m, instance);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static TestMethods findAllPublicTestMethods(Class<?> clazz) {
        List<Method> beforeMethods = new ArrayList<>();
        Map<Integer, List<Method>> testMethods = new TreeMap<>();
        List<Method> afterMethods = new ArrayList<>();

        for (Method method : clazz.getMethods()) {
            checkAndAddBeforeTestMethod(method, beforeMethods);
            if (method.isAnnotationPresent(Test.class)) {
                int priority = method.getAnnotation(Test.class).priority();
                if (priority < 1 || priority > 10) {
                    throw new InvalidPriorityValueException("Wrong priority value: " + priority + ". Range must be from 1 to 10");
                }
                List<Method> list = testMethods.getOrDefault(priority, new ArrayList<>());
                list.add(method);
                testMethods.put(priority, list);
            }
            checkAndAddAfterTestMethod(method, afterMethods);
        }

        return new TestMethods(beforeMethods, testMethods, afterMethods);
    }

    private static void checkAndAddAfterTestMethod(Method method, List<Method> afterMethods) {
        checkAndAddMethod(method, afterMethods, AfterSuite.class);
    }

    private static void checkAndAddBeforeTestMethod(Method method, List<Method> beforeMethods) {
        checkAndAddMethod(method, beforeMethods, BeforeSuite.class);
    }

    private static void checkAndAddMethod(Method method, List<Method> methods, Class<? extends Annotation> clazz) {
        if (method.isAnnotationPresent(clazz)) {
            checkIfStaticMethod(method, clazz);
            if (!methods.isEmpty()) {
                throw new TooMuchMethodsException("Only one method annotated with @" + clazz.getSimpleName() + " is allowed");
            }
            methods.add(method);
        }
    }

    private static void checkIfStaticMethod(Method method, Class<?> annotation) {
        if (!Modifier.isStatic(method.getModifiers())) {
            throw new NonStaticMethodException(method, annotation);
        }
    }

    private static void runMethod(Method method, Object obj) throws Exception {
        LOGGER.debug("Running: {}", method.getName());
        Object[] args = null;
        if (method.isAnnotationPresent(Test.class) && method.isAnnotationPresent(CsvSource.class)) {
            String[] data = Arrays
                    .stream(method.getAnnotation(CsvSource.class).value().split(","))
                    .map(String::trim)
                    .toArray(String[]::new);
            LOGGER.debug("input data: {}", Arrays.toString(data));

            var params = method.getParameterTypes();
            LOGGER.debug("method args types: {}", Arrays.toString(params));

            if (data.length != params.length) {
                throw new RuntimeException("!!! TODO !!!");
            }

            args = new Object[data.length];
            for (int i = 0; i < params.length; i++) {
                var type = params[i];
                args[i] = castStringTo(type, data[i]);
            }
        }
        method.invoke(obj, args);
    }

    private static Object castStringTo(Class<?> type, String data) {
        if (type == (Integer.class) || type == (int.class)) {
            return Integer.valueOf(data);
        }
        if (type == (Boolean.class) || type == (boolean.class)) {
            return Boolean.valueOf(data);
        }
        if (type == (String.class)) {
            return data;
        }
        throw new UnsupportedOperationException("Not supported type " + type);
    }

}
