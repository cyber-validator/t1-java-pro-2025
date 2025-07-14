package tech.inno;

public interface ThreadPoolTaskQueue {

    void put(Runnable task);

    Runnable take();

    int size();

    boolean isEmpty();

    void clear();

}
