package com.algorithms.cli;

import com.algorithms.core.*;
import com.algorithms.metrics.*;
import com.algorithms.model.Point;
import java.io.IOException;
import java.util.Random;

public class AlgorithmRunner {

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: java AlgorithmRunner <algorithm> <size>");
            System.exit(1);
        }

        String algorithm = args[0];
        int size = Integer.parseInt(args[1]);

        CSVWriter writer = new CSVWriter(algorithm + "_metrics.csv");
        writer.writeHeader("Algorithm", "Size", "Time(ns)", "Depth", "Comparisons");

        switch (algorithm.toLowerCase()) {
            case "mergesort":
                runMergeSort(size, writer);
                break;
            case "quicksort":
                runQuickSort(size, writer);
                break;
            case "select":
                runSelect(size, writer);
                break;
            case "closest":
                runClosestPair(size, writer);
                break;
            default:
                System.out.println("Unknown algorithm: " + algorithm);
        }
    }

    private static void runMergeSort(int size, CSVWriter writer) throws IOException {
        int[] arr = generateRandomArray(size);
        MergeSort sorter = new MergeSort();

        sorter.sort(arr);

        MetricsCollector metrics = sorter.getMetrics();
        writer.appendRow("MergeSort", size, metrics.getElapsedTime(),
                metrics.getMaxDepth(), metrics.getComparisons());

        System.out.println("MergeSort completed for size " + size);
    }

    private static void runQuickSort(int size, CSVWriter writer) throws IOException {
        int[] arr = generateRandomArray(size);
        QuickSort sorter = new QuickSort();

        sorter.sort(arr);

        MetricsCollector metrics = sorter.getMetrics();
        writer.appendRow("QuickSort", size, metrics.getElapsedTime(),
                metrics.getMaxDepth(), metrics.getComparisons());

        System.out.println("QuickSort completed for size " + size);
    }

    private static void runSelect(int size, CSVWriter writer) throws IOException {
        int[] arr = generateRandomArray(size);
        DeterministicSelect selector = new DeterministicSelect();

        int k = size / 2; // Find median
        selector.select(arr, k);

        MetricsCollector metrics = selector.getMetrics();
        writer.appendRow("Select", size, metrics.getElapsedTime(),
                metrics.getMaxDepth(), metrics.getComparisons());

        System.out.println("Select completed for size " + size);
    }

    private static void runClosestPair(int size, CSVWriter writer) throws IOException {
        Point[] points = generateRandomPoints(size);
        ClosestPair cp = new ClosestPair();

        cp.findClosestDistance(points);

        MetricsCollector metrics = cp.getMetrics();
        writer.appendRow("ClosestPair", size, metrics.getElapsedTime(),
                metrics.getMaxDepth(), metrics.getComparisons());

        System.out.println("ClosestPair completed for size " + size);
    }

    private static int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(size * 10);
        }
        return arr;
    }

    private static Point[] generateRandomPoints(int size) {
        Random rand = new Random();
        Point[] points = new Point[size];
        for (int i = 0; i < size; i++) {
            points[i] = new Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
        }
        return points;
    }
}