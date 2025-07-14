package tech.inno;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueThreadPoolTaskQueue implements ThreadPoolTaskQueue {

    private final ConcurrentLinkedQueue<Runnable> taskQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void put(Runnable task) {
        taskQueue.add(task);
    }

    @Override
    public Runnable take() {
        return taskQueue.poll();
    }

    @Override
    public int size() {
        return taskQueue.size();
    }

    @Override
    public boolean isEmpty() {
        return taskQueue.isEmpty();
    }

    @Override
    public void clear() {
        taskQueue.clear();
    }

}
