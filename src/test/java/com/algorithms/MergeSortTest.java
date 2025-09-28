package com.algorithms;

import com.algorithms.core.MergeSort;
import com.algorithms.metrics.DepthTracker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class MergeSortTest {
    private MergeSort sorter;

    @BeforeEach
    void setUp() {
        sorter = new MergeSort();
    }

    @Test
    void testEmptyArray() {
        int[] arr = {};
        sorter.sort(arr);
        assertEquals(0, arr.length);
    }

    @Test
    void testSingleElement() {
        int[] arr = {42};
        sorter.sort(arr);
        assertArrayEquals(new int[]{42}, arr);
    }

    @Test
    void testSortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        sorter.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testReversedArray() {
        int[] arr = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        sorter.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testDuplicates() {
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        int[] expected = {1, 1, 2, 3, 3, 4, 5, 5, 6, 9};
        sorter.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 100, 1000, 10000})
    void testRandomArrays(int size) {
        Random rand = new Random(42);
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(size);
        }

        int[] expected = arr.clone();
        Arrays.sort(expected);

        sorter.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testRecursionDepth() {
        int size = 1024;
        int[] arr = new Random().ints(size, 0, size).toArray();

        DepthTracker.reset();
        sorter.sort(arr);
        int depth = DepthTracker.getMaxDepth();

        // For merge sort, depth should be approximately log2(n)
        int expectedMaxDepth = (int) Math.ceil(Math.log(size) / Math.log(2)) + 2;
        assertTrue(depth <= expectedMaxDepth,
                "Depth " + depth + " exceeds expected " + expectedMaxDepth);
    }
}