package tech.inno.exception;

import java.lang.reflect.Method;

public class NonStaticMethodException extends RuntimeException {

    public NonStaticMethodException(Method method, Class<?> annotation) {
        super("Method '%s()' annotated with '@%s' must be static."
                .formatted(method.getName(), annotation.getSimpleName()));
    }

}
