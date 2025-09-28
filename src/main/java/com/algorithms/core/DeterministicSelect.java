package com.algorithms.core;

import com.algorithms.metrics.DepthTracker;
import com.algorithms.metrics.MetricsCollector;
import com.algorithms.util.ArrayUtils;
import java.util.Arrays;

public class DeterministicSelect {
    private MetricsCollector metrics;

    public DeterministicSelect() {
        this.metrics = new MetricsCollector();
    }

    public int select(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k < 0 || k >= arr.length) {
            throw new IllegalArgumentException("Invalid input");
        }

        metrics.reset();
        DepthTracker.reset();
        metrics.startTimer();

        int result = select(arr, 0, arr.length - 1, k);

        metrics.stopTimer();
        return result;
    }

    private int select(int[] arr, int left, int right, int k) {
        DepthTracker.enter();
        metrics.enterRecursion();

        try {
            if (left == right) {
                return arr[left];
            }

            // For small arrays, just sort
            if (right - left < 10) {
                Arrays.sort(arr, left, right + 1);
                return arr[left + k];
            }

            int pivot = medianOfMedians(arr, left, right);
            int pivotIndex = partition(arr, left, right, pivot);
            int relativePos = pivotIndex - left;

            metrics.incrementComparisons();
            if (k == relativePos) {
                return arr[pivotIndex];
            } else if (k < relativePos) {
                return select(arr, left, pivotIndex - 1, k);
            } else {
                return select(arr, pivotIndex + 1, right, k - relativePos - 1);
            }
        } finally {
            metrics.exitRecursion();
            DepthTracker.exit();
        }
    }

    private int medianOfMedians(int[] arr, int left, int right) {
        int n = right - left + 1;

        if (n <= 5) {
            Arrays.sort(arr, left, right + 1);
            return arr[left + n / 2];
        }

        // Divide into groups of 5
        int numGroups = (n + 4) / 5;
        int[] medians = new int[numGroups];
        metrics.incrementAllocations(numGroups);

        for (int i = 0; i < numGroups; i++) {
            int groupLeft = left + i * 5;
            int groupRight = Math.min(groupLeft + 4, right);
            Arrays.sort(arr, groupLeft, groupRight + 1);
            medians[i] = arr[groupLeft + (groupRight - groupLeft) / 2];
        }

        // Find median of medians recursively
        return select(medians, 0, numGroups - 1, numGroups / 2);
    }

    private int partition(int[] arr, int left, int right, int pivotValue) {
        int pivotIndex = left;
        for (int i = left; i <= right; i++) {
            if (arr[i] == pivotValue) {
                pivotIndex = i;
                break;
            }
        }

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