package tech.inno;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool(5);

        for (int i = 0; i < 10; i++) {
            pool.execute(() -> {
                Thread th = Thread.currentThread();
                System.out.println("test task in thread: " + th.getName());
//                try {
//                    TimeUnit.SECONDS.sleep(10);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
            });
        }

        TimeUnit.SECONDS.sleep(3);

        pool.execute(() -> System.out.println("Again"));

        TimeUnit.SECONDS.sleep(30);

        pool.shutdown();

    }

}