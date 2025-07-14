package tech.inno;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Objects.isNull;

public class ThreadPool {

    private static final String NAME_PREFIX = "JavaProCourseThreadPoolThread";

    private static final AtomicInteger poolInstanceCounter = new AtomicInteger(0);

    private final AtomicBoolean active;

    private final List<ThreadPoolThread> poolThreads;

    private final ThreadPoolTaskQueue taskQueue;

    public ThreadPool(int poolSize) {
        poolInstanceCounter.incrementAndGet();
        this.active = new AtomicBoolean(true);
        this.taskQueue = new LinkedListThreadPoolTaskQueue();
//        this.taskQueue = new ConcurrentLinkedQueueThreadPoolTaskQueue();
        this.poolThreads = initPool(poolSize);
    }

    private List<ThreadPoolThread> initPool(int poolSize) {
        List<ThreadPoolThread> threads = new ArrayList<>();
        for (int i = 1; i <= poolSize; i++) {
            String threadName = "%s-%d-%d".formatted(NAME_PREFIX, poolInstanceCounter.get(), i);
            ThreadPoolThread thread = new ThreadPoolThread(threadName, active, taskQueue);
            threads.add(thread);
        }
        return threads;
    }

    public void execute(Runnable task) {
        if (isNull(task)) {
            throw new IllegalArgumentException("Runnable can't be null");
        }
        if (active.get()) {
            taskQueue.put(task);
        } else {
            throw new IllegalStateException("Thread pool is terminated");
        }
    }

    public void shutdown() {
        if (active.get()) {
            taskQueue.clear();
            active.set(false);
        }
    }

}
