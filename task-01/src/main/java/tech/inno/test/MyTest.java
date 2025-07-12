package tech.inno.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tech.inno.anno.AfterSuite;
import tech.inno.anno.BeforeSuite;
import tech.inno.anno.CsvSource;
import tech.inno.anno.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

public class MyTest {

    private static final Logger LOGGER = LogManager.getLogger(MyTest.class);

    public static final String EXECUTION_MESSAGE = "'%s' METHOD EXECUTION WITH PRIORITY = %d";

    @BeforeSuite
    public static void setUp() {
        handleExecution("before suit");
    }

    @AfterSuite
    public static void tearDown() {
        handleExecution("after suit");
    }

    @Test
    public void myTestDefaultPriority() {
        execute("myTestDefaultPriority");
    }

    @Test(priority = 7)
    public void myTestHighPriority() {
        execute("myTestHighPriority");
    }

    @Test(priority = 3)
    public void myTestLowPriority() {
        execute("myTestLowPriority");
    }

    @Test(priority = 10)
    public void myTestHighestPriority() {
        execute("myTestHighestPriority");
    }

    @Test(priority = 1)
    public void myTestLowestPriority() {
        execute("myTestLowestPriority");
    }

    @CsvSource("10, Java, 20, true")
    @Test
    public void testCsvSourceMethod(int a, String b, int c, boolean d) {
        execute("testCsvSourceMethod");
        LOGGER.info("method args: a={}, b={}, c={}, d={}", a, b, c, d);
    }

    private void execute(String methodName) {
        int priority = getPriority(methodName);
        handleExecution((EXECUTION_MESSAGE).formatted(methodName, priority));
    }

    private int getPriority(String methodName) {
        Method method = Arrays.stream(this.getClass().getMethods())
                .filter(m -> m.getName().equals(methodName))
                .findFirst()
                .orElseThrow();
        return method.getAnnotation(Test.class).priority();
    /*
        Если сделать так, то оба метода приводят к исключению NoSuchMethodException на методе с названием 'testCsvSourceMethod'
        private void testMethod(String methodName, Class<?>[] args) {
            var m1 = this.getClass().getMethod(methodName);
            var m2 = this.getClass().getDeclaredMethod(methodName, args);
        }
    */
    }

    private static void handleExecution(String message) {
        LOGGER.info("execution -> {}", message);
    }

}
