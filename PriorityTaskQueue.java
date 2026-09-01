package com.taskqueue;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PriorityTaskQueue {
    private final PriorityQueue<Task> minHeap = new PriorityQueue<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final int capacity;

    public PriorityTaskQueue(int capacity) {
        this.capacity = capacity;
    }

    public boolean enqueue(Task task) {
        lock.lock();
        try {
            if (minHeap.size() >= capacity) {
                return false; // Queue full (backpressure management)
            }
            minHeap.offer(task);
            notEmpty.signal();
            return true;
        } finally {
            lock.unlock();
        }
    }

    public Task dequeue() throws InterruptedException {
        lock.lock();
        try {
            while (minHeap.isEmpty()) {
                notEmpty.await();
            }
            return minHeap.poll();
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return minHeap.size();
        } finally {
            lock.unlock();
        }
    }
}
