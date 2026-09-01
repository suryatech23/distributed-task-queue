package com.taskqueue;
import java.util.UUID;

public class Task implements Comparable<Task> {
    private final String id;
    private final String payload;
    private final TaskPriority priority;
    private final long createdAt;

    public Task(String payload, TaskPriority priority) {
        this.id = UUID.randomUUID().toString();
        this.payload = payload;
        this.priority = priority;
        this.createdAt = System.currentTimeMillis();
    }

    public String getId() { return id; }
    public String getPayload() { return payload; }
    public TaskPriority getPriority() { return priority; }
    public long getCreatedAt() { return createdAt; }

    @Override
    public int compareTo(Task other) {
        // Higher priority comes first; if equal priority, older tasks come first (FIFO)
        if (this.priority.getLevel() != other.priority.getLevel()) {
            return Integer.compare(other.priority.getLevel(), this.priority.getLevel());
        }
        return Long.compare(this.createdAt, other.createdAt);
    }
}
