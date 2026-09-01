package com.taskqueue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        PriorityTaskQueue queue = new PriorityTaskQueue(100);
        WorkerPool workerPool = new WorkerPool(3, queue);

        workerPool.start();

        queue.enqueue(new Task("Low priority background job", TaskPriority.LOW));
        queue.enqueue(new Task("CRITICAL system alert sync", TaskPriority.CRITICAL));
        queue.enqueue(new Task("Medium user data export", TaskPriority.MEDIUM));
        queue.enqueue(new Task("High priority security patch", TaskPriority.HIGH));

        Thread.sleep(3000);
        workerPool.shutdown();
        System.out.println("Task Queue execution finished cleanly.");
    }
}
