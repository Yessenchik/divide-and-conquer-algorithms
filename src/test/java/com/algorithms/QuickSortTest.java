package com.algorithms;

import com.algorithms.core.QuickSort;
import com.algorithms.metrics.DepthTracker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest {
    private QuickSort sorter;

    @BeforeEach
    void setUp() {
        sorter = new QuickSort();
    }

    @Test
    void testRandomArraySorting() {
        Random rand = new Random(42);
        for (int trial = 0; trial < 100; trial++) {
            int size = rand.nextInt(1000) + 1;
            int[] arr = rand.ints(size, -1000, 1000).toArray();
            int[] expected = arr.clone();
            Arrays.sort(expected);

            sorter.sort(arr);
            assertArrayEquals(expected, arr, "Failed on trial " + trial);
        }
    }

    @Test
    void testBoundedRecursionDepth() {
        // Test that recursion depth is bounded to ~2*log2(n)
        for (int size : new int[]{100, 1000, 10000}) {
            int[] arr = new Random().ints(size, 0, size).toArray();

            DepthTracker.reset();
            sorter.sort(arr);
            int depth = DepthTracker.getMaxDepth();

            int expectedMax = 2 * (int) Math.floor(Math.log(size) / Math.log(2)) + 5;
            assertTrue(depth <= expectedMax,
                    "Size " + size + ": depth " + depth + " > " + expectedMax);
        }
    }

    @Test
    void testWorstCaseArray() {
        // All equal elements
        int[] arr = new int[1000];
        Arrays.fill(arr, 42);
        int[] expected = arr.clone();

        sorter.sort(arr);
        assertArrayEquals(expected, arr);
    }
}