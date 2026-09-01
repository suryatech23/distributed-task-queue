# Distributed Event-Driven Task Queue

A lightweight, thread-safe asynchronous task execution engine engineered in Java. Designed to manage high-throughput event processing using custom priority heaps and concurrent worker threads without external heavy frameworks.

## Architecture Highlights
- **Custom Priority Heap (`PriorityQueue`)**: Implements strict ordering based on task priority levels with FIFO tie-breaking for matching priority ranks.
- **Thread-Safety & Synchronization**: Utilizes `ReentrantLock` and condition variables (`Condition`) to manage safe producer-consumer operations and thread coordination.
- **Backpressure Handling**: Enforces capacity limits on queue ingestion to protect worker pools from memory overflow under peak loads.

## Getting Started
1. Compile the source code:
   ```bash
   javac src/main/java/com/taskqueue/*.java
