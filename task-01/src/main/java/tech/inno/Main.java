package tech.inno;

import tech.inno.runner.TestRunner;
import tech.inno.test.MyTest;

public class Main {

    public static void main(String[] args) {
        TestRunner.runTests(MyTest.class);
    }

}