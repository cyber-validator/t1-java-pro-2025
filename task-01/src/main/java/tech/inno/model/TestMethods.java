package tech.inno.model;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public record TestMethods(List<Method> beforeMethods,
                          Map<Integer, List<Method>> testMethods,
                          List<Method> afterMethods) {

}
