package tech.inno.runner;

import tech.inno.exception.ExecutionException;
import tech.inno.model.Person;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Executions {

    public static Integer findThirdMaxInteger(List<Integer> integers) {
        return integers.stream()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .skip(2)
                .findFirst()
                .orElseThrow(() -> new ExecutionException("List must contain at least 3 elements"));
    }

    public static Integer findThirdMaxUniqueInteger(List<Integer> integers) {
        return integers.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .skip(2)
                .findFirst()
                .orElseThrow(() -> new ExecutionException("List must contain at least 3 DISTINCT elements"));
    }

    public static List<String> findThreeOldestEngineerNamesSortedByAgeDesc(List<Person> employees) {
        return employees.stream()
                .filter(employee -> employee.position().equals("Engineer"))
                .sorted((employee1, employee2) -> Integer.compare(employee2.age(), employee1.age()))
                .limit(3)
                .map(Person::name)
                .toList();
    }

    public static double findEngineersAverageAge(List<Person> employees) {
        return employees.stream()
                .filter(employee -> employee.position().equals("Engineer"))
                .mapToInt(Person::age)
                .average()
                .orElseThrow(() -> new ExecutionException("Calculating error, please check input data"));
    }

    public static String findTheLongestWord(List<String> words) {
        return words.stream()
                .max(Comparator.comparingInt(String::length))
                .orElseThrow(() -> new ExecutionException("Can't get result, please check input data"));
    }

    public static Map<String, Integer> countWordsFromText(String lowerCaseText) {
        return Arrays.stream(lowerCaseText.split(" "))
                .collect(Collectors.toMap(word -> word, word -> 1, Integer::sum));
    }

    public static Void printStringsSortedByLengthThenByNameAsc(List<String> words) {
        words.stream()
                .sorted(Comparator.comparingInt(String::length).thenComparing(String::compareToIgnoreCase))
                .forEachOrdered(System.out::println);
        return null;
    }

    public static String findTheLongestWordInArrayOfFiveWordStrings(String[] fiveWordStrings) {
        return Arrays.stream(fiveWordStrings)
                .flatMap(string -> Arrays.stream(string.split(" ")))
                .max(Comparator.comparingInt(String::length))
                .orElseThrow(() -> new ExecutionException("Can't get result, please check input data"));
    }

}
