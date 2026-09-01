package com.taskqueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WorkerPool {
    private final ExecutorService pool;
    private final PriorityTaskQueue taskQueue;
    private volatile boolean running = true;

    I want to ensure thread safety across concurrent workers.

    public WorkerPool(int workerCount, PriorityTaskQueue taskQueue) {
        this.pool = Executors.newFixedThreadPool(workerCount);
        this.taskQueue = taskQueue;
    }

    public void start() {
        for (int i = 0; i < 3; i++) {
            pool.submit(() -> {
                while (running) {
                    try {
                        Task task = taskQueue.dequeue();
                        processTask(task);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
        }
    }

    private void processTask(Task task) {
        System.out.println("Processing [Priority: " + task.getPriority() + "] Task ID: " + task.getId() + " | Payload: " + task.getPayload());
        try {
            Thread.sleep(500); // Simulate task execution time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        running = false;
        pool.shutdown();
        try {
            if (!pool.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
        }
    }
}
