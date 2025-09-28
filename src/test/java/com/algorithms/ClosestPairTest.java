package com.algorithms;

import com.algorithms.core.ClosestPair;
import com.algorithms.model.Point;
import org.junit.jupiter.api.*;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class ClosestPairTest {
    private ClosestPair closestPair;
    private static final double EPSILON = 1e-9;

    @BeforeEach
    void setUp() {
        closestPair = new ClosestPair();
    }

    @Test
    void testSimpleCase() {
        Point[] points = {
                new Point(0, 0),
                new Point(1, 1),
                new Point(3, 3),
                new Point(1, 0)
        };
        double result = closestPair.findClosestDistance(points);
        assertEquals(1.0, result, EPSILON);
    }

    @Test
    void testCollinearPoints() {
        Point[] points = {
                new Point(0, 0),
                new Point(1, 0),
                new Point(2, 0),
                new Point(3, 0)
        };
        double result = closestPair.findClosestDistance(points);
        assertEquals(1.0, result, EPSILON);
    }

    @Test
    void testCompareWithBruteForce() {
        Random rand = new Random(42);
        int n = 100;
        Point[] points = new Point[n];

        for (int i = 0; i < n; i++) {
            points[i] = new Point(rand.nextDouble() * 100, rand.nextDouble() * 100);
        }

        double fastResult = closestPair.findClosestDistance(points);
        double bruteForceResult = bruteForceClosest(points);

        assertEquals(bruteForceResult, fastResult, EPSILON);
    }

    private double bruteForceClosest(Point[] points) {
        double minDist = Double.POSITIVE_INFINITY;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double dist = points[i].distanceTo(points[j]);
                minDist = Math.min(minDist, dist);
            }
        }
        return minDist;
    }
}