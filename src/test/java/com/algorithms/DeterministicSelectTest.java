package com.algorithms;

import com.algorithms.core.DeterministicSelect;
import org.junit.jupiter.api.*;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class DeterministicSelectTest {
    private DeterministicSelect selector;

    @BeforeEach
    void setUp() {
        selector = new DeterministicSelect();
    }

    @Test
    void testSelectMedian() {
        int[] arr = {3, 7, 2, 1, 4, 6, 5};
        int k = arr.length / 2;
        int result = selector.select(arr.clone(), k);
        assertEquals(4, result);
    }

    @Test
    void testSelectMinimum() {
        int[] arr = {3, 7, 2, 1, 4, 6, 5};
        int result = selector.select(arr.clone(), 0);
        assertEquals(1, result);
    }

    @Test
    void testSelectMaximum() {
        int[] arr = {3, 7, 2, 1, 4, 6, 5};
        int result = selector.select(arr.clone(), arr.length - 1);
        assertEquals(7, result);
    }

    @Test
    void testRandomComparisonWithSort() {
        Random rand = new Random(42);

        for (int trial = 0; trial < 100; trial++) {
            int size = rand.nextInt(1000) + 1;
            int[] arr = rand.ints(size, -1000, 1000).toArray();
            int k = rand.nextInt(size);

            int[] sorted = arr.clone();
            Arrays.sort(sorted);
            int expected = sorted[k];

            int result = selector.select(arr.clone(), k);
            assertEquals(expected, result,
                    "Failed on trial " + trial + " with k=" + k);
        }
    }
}