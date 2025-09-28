package com.algorithms.metrics;

public class MetricsCollector {
    private long comparisons;
    private long allocations;
    private long startTime;
    private long endTime;
    private int maxDepth;
    private int currentDepth;

    public void reset() {
        comparisons = 0;
        allocations = 0;
        maxDepth = 0;
        currentDepth = 0;
        startTime = 0;
        endTime = 0;
    }

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        endTime = System.nanoTime();
    }

    public void incrementComparisons() {
        comparisons++;
    }

    public void incrementAllocations(int size) {
        allocations += size;
    }

    public void enterRecursion() {
        currentDepth++;
        maxDepth = Math.max(maxDepth, currentDepth);
    }

    public void exitRecursion() {
        currentDepth--;
    }

    public long getComparisons() { return comparisons; }
    public long getAllocations() { return allocations; }
    public long getElapsedTime() { return endTime - startTime; }
    public int getMaxDepth() { return maxDepth; }
}