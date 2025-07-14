package tech.inno;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.Objects.nonNull;

public class ThreadPoolThread extends Thread {

    private final AtomicBoolean active;

    private final ThreadPoolTaskQueue taskQueue;

    public ThreadPoolThread(String name, AtomicBoolean active, ThreadPoolTaskQueue taskQueue) {
        super(name);
        this.active = active;
        this.taskQueue = taskQueue;
        this.start();
    }

    @Override
    public void run() {
        Runnable task;
        try {
            while (active.get() || !taskQueue.isEmpty()) {
                while (nonNull(task = taskQueue.take())) {
                    task.run();
                }
                TimeUnit.MILLISECONDS.sleep(1_500);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
