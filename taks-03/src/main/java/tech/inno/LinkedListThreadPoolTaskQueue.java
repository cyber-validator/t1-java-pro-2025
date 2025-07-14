package tech.inno;

import java.util.LinkedList;

public class LinkedListThreadPoolTaskQueue implements ThreadPoolTaskQueue {

    private final LinkedList<Runnable> taskQueue = new LinkedList<>();

    @Override
    public void put(Runnable task) {
        //@TODO: sync
        taskQueue.add(task);
    }

    @Override
    public Runnable take() {
        //@TODO: sync
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
        //@TODO: sync
        taskQueue.clear();
    }

}
