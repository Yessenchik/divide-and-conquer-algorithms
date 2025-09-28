package com.algorithms.metrics;

public class DepthTracker {
    private static ThreadLocal<Integer> depth = ThreadLocal.withInitial(() -> 0);
    private static ThreadLocal<Integer> maxDepth = ThreadLocal.withInitial(() -> 0);

    public static void enter() {
        int current = depth.get() + 1;
        depth.set(current);
        maxDepth.set(Math.max(maxDepth.get(), current));
    }

    public static void exit() {
        depth.set(Math.max(0, depth.get() - 1));
    }

    public static int getMaxDepth() {
        return maxDepth.get();
    }

    public static void reset() {
        depth.set(0);
        maxDepth.set(0);
    }
}