package com.algorithms.core;

import com.algorithms.metrics.DepthTracker;
import com.algorithms.metrics.MetricsCollector;
import com.algorithms.util.ArrayUtils;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class QuickSort {
    private static final int CUTOFF = 10;
    private final Random random = ThreadLocalRandom.current();
    private MetricsCollector metrics;

    public QuickSort() {
        this.metrics = new MetricsCollector();
    }

    public void sort(int[] arr) {
        if (arr == null || arr.length <= 1) return;

        metrics.reset();
        DepthTracker.reset();
        metrics.startTimer();

        quickSort(arr, 0, arr.length - 1);

        metrics.stopTimer();
    }

    private void quickSort(int[] arr, int left, int right) {
        while (left < right) {
            if (right - left + 1 <= CUTOFF) {
                ArrayUtils.insertionSort(arr, left, right);
                return;
            }

            int pivotIndex = left + random.nextInt(right - left + 1);
            int partitionIndex = partition(arr, left, right, pivotIndex);

            // Recurse on smaller partition, iterate on larger
            if (partitionIndex - left < right - partitionIndex) {
                DepthTracker.enter();
                metrics.enterRecursion();
                quickSort(arr, left, partitionIndex - 1);
                metrics.exitRecursion();
                DepthTracker.exit();
                left = partitionIndex + 1;
            } else {
                DepthTracker.enter();
                metrics.enterRecursion();
                quickSort(arr, partitionIndex + 1, right);
                metrics.exitRecursion();
                DepthTracker.exit();
                right = partitionIndex - 1;
            }
        }
    }

    private int partition(int[] arr, int left, int right, int pivotIndex) {
        int pivotValue = arr[pivotIndex];
        ArrayUtils.swap(arr, pivotIndex, right);
        int storeIndex = left;

        for (int i = left; i < right; i++) {
            metrics.incrementComparisons();
            if (arr[i] < pivotValue) {
                ArrayUtils.swap(arr, i, storeIndex);
                storeIndex++;
            }
        }
        ArrayUtils.swap(arr, storeIndex, right);
        return storeIndex;
    }

    public MetricsCollector getMetrics() {
        return metrics;
    }
}