package tech.inno.runner;

import tech.inno.model.Person;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Functions {

    public static final Function<List<Integer>, Integer> findThirdMaxIntegerFunc = Executions::findThirdMaxInteger;

    public static final Function<List<Integer>, Integer> findThirdMaxUniqueIntegerFunc = Executions::findThirdMaxUniqueInteger;

    public static final Function<List<Person>, List<String>> findThreeOldestEngineerNamesSortedByAgeDescFunc = Executions::findThreeOldestEngineerNamesSortedByAgeDesc;

    public static final Function<List<Person>, Double> findEngineersAverageAgeFunc = Executions::findEngineersAverageAge;

    public static final Function<List<String>, String> findTheLongestWordFunc = Executions::findTheLongestWord;

    public static final Function<String, Map<String, Integer>> countWordsFromTextFunc = Executions::countWordsFromText;

    public static final Function<List<String>, Void> printStringsSortedByLengthThenByNameAscFunc = Executions::printStringsSortedByLengthThenByNameAsc;

    public static final Function<String[], String> findTheLongestWordInArrayOfFiveWordStringsFunc = Executions::findTheLongestWordInArrayOfFiveWordStrings;

}
