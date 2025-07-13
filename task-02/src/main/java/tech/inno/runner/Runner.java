package tech.inno.runner;

import tech.inno.model.Person;

import java.util.List;
import java.util.function.Function;

import static tech.inno.runner.Functions.countWordsFromTextFunc;
import static tech.inno.runner.Functions.findEngineersAverageAgeFunc;
import static tech.inno.runner.Functions.findTheLongestWordFunc;
import static tech.inno.runner.Functions.findTheLongestWordInArrayOfFiveWordStringsFunc;
import static tech.inno.runner.Functions.findThirdMaxIntegerFunc;
import static tech.inno.runner.Functions.findThirdMaxUniqueIntegerFunc;
import static tech.inno.runner.Functions.findThreeOldestEngineerNamesSortedByAgeDescFunc;
import static tech.inno.runner.Functions.printStringsSortedByLengthThenByNameAscFunc;

public class Runner {

    public static void run() {

        runFunction(List.of(5, 2, 10, 9, 4, 3, 10, 1, 13), findThirdMaxIntegerFunc);

        runFunction(List.of(5, 2, 10, 9, 4, 3, 10, 1, 13), findThirdMaxUniqueIntegerFunc);

        runFunction(List.of(
                new Person("Employee1", 30, "Tester"),
                new Person("Employee2", 20, "Tester"),
                new Person("Employee3", 50, "Engineer"),
                new Person("Employee4", 45, "Engineer"),
                new Person("Employee5", 37, "Engineer"),
                new Person("Employee6", 60, "Engineer"),
                new Person("Employee7", 60, "Manager"),
                new Person("Employee8", 20, "Tester"),
                new Person("Employee9", 50, "Engineer"),
                new Person("Employee0", 50, "Tester")
        ), findThreeOldestEngineerNamesSortedByAgeDescFunc);

        runFunction(List.of(
                new Person("Employee1", 30, "Tester"),
                new Person("Employee2", 20, "Tester"),
                new Person("Employee3", 70, "Engineer"),
                new Person("Employee4", 70, "Engineer"),
                new Person("Employee5", 50, "Engineer"),
                new Person("Employee6", 50, "Engineer"),
                new Person("Employee7", 60, "Manager"),
                new Person("Employee8", 20, "Tester"),
                new Person("Employee9", 10, "Engineer"),
                new Person("Employee0", 50, "Tester")
        ), findEngineersAverageAgeFunc);

        runFunction(List.of("qwerty", "qwe", "asdf"), findTheLongestWordFunc);

        runFunction("asf asdf asdfg q asd asf asdf qwe qwe qwe qwe", countWordsFromTextFunc);

        runFunction(List.of("qwerty", "qwert", "asdfg", "qwe", "er"), printStringsSortedByLengthThenByNameAscFunc);

        runFunction(new String[]{
                "1asd1 1asd2 1asd3 1asd4 1asd5",
                "2asd1 2asd-long2 2asd3 2asd4 2asd5",
                "3as-d1 3as-ad2 3as-aad3 3as-aaad4 3as-aaaad5",
                "4asd1 4asd2 4asd-the-longest3 4asd4 4asd5",
                "5asd1 5asd2 5asd3 5asd4 5asd5",
                "6asd1 6asd-the-longest2 6asd3 6asd4 6asd5"
        }, findTheLongestWordInArrayOfFiveWordStringsFunc);

    }

    private static <T, V> void runFunction(T inputData, Function<T, V> function) {
        try {
            System.out.println(">>>>   START");
            V res = function.apply(inputData);
            if (res != null) {
                System.out.println(res);
            }
            System.out.println("<<<<   FINISH\n\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
