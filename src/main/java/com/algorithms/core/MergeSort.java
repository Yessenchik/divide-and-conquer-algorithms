package com.algorithms.core;

import com.algorithms.metrics.DepthTracker;
import com.algorithms.metrics.MetricsCollector;
import com.algorithms.util.ArrayUtils;

public class MergeSort {
    private static final int CUTOFF = 10;
    private int[] buffer;
    private MetricsCollector metrics;

    public MergeSort() {
        this.metrics = new MetricsCollector();
    }

    public void sort(int[] arr) {
        if (arr == null || arr.length <= 1) return;

        metrics.reset();
        DepthTracker.reset();
        metrics.startTimer();

        buffer = new int[arr.length];
        metrics.incrementAllocations(arr.length);

        mergeSort(arr, 0, arr.length - 1);

        metrics.stopTimer();
    }

    private void mergeSort(int[] arr, int left, int right) {
        DepthTracker.enter();
        metrics.enterRecursion();

        try {
            if (right - left + 1 <= CUTOFF) {
                ArrayUtils.insertionSort(arr, left, right);
                return;
            }

            if (left < right) {
                int mid = left + (right - left) / 2;
                mergeSort(arr, left, mid);
                mergeSort(arr, mid + 1, right);
                merge(arr, left, mid, right);
            }
        } finally {
            metrics.exitRecursion();
            DepthTracker.exit();
        }
    }

    private void merge(int[] arr, int left, int mid, int right) {
        // Copy to buffer
        for (int i = left; i <= right; i++) {
            buffer[i] = arr[i];
        }

        int i = left, j = mid + 1, k = left;

        while (i <= mid && j <= right) {
            metrics.incrementComparisons();
            if (buffer[i] <= buffer[j]) {
                arr[k++] = buffer[i++];
            } else {
                arr[k++] = buffer[j++];
            }
        }

        while (i <= mid) {
            arr[k++] = buffer[i++];
        }
    }

    public MetricsCollector getMetrics() {
        return metrics;
    }
}