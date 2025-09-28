package com.algorithms.core;

import com.algorithms.metrics.DepthTracker;
import com.algorithms.metrics.MetricsCollector;
import com.algorithms.model.Point;
import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {
    private MetricsCollector metrics;
    private Point[] auxY;

    public ClosestPair() {
        this.metrics = new MetricsCollector();
    }

    public double findClosestDistance(Point[] points) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("Need at least 2 points");
        }

        metrics.reset();
        DepthTracker.reset();
        metrics.startTimer();

        Point[] sortedByX = points.clone();
        Point[] sortedByY = points.clone();
        Arrays.sort(sortedByX);
        Arrays.sort(sortedByY, Comparator.comparingDouble(p -> p.y));

        auxY = new Point[points.length];
        metrics.incrementAllocations(points.length);

        double result = closestPairRecursive(sortedByX, sortedByY, 0, points.length - 1);

        metrics.stopTimer();
        return result;
    }

    private double closestPairRecursive(Point[] px, Point[] py, int left, int right) {
        DepthTracker.enter();
        metrics.enterRecursion();

        try {
            int n = right - left + 1;

            // Base case: use brute force for small arrays
            if (n <= 3) {
                return bruteForce(px, left, right);
            }

            int mid = left + (right - left) / 2;
            Point midPoint = px[mid];

            // Divide points by y-coordinate for recursive calls
            Point[] pyLeft = new Point[mid - left + 1];
            Point[] pyRight = new Point[right - mid];
            int li = 0, ri = 0;

            for (Point p : py) {
                if (p.x <= midPoint.x && li < pyLeft.length) {
                    pyLeft[li++] = p;
                } else if (ri < pyRight.length) {
                    pyRight[ri++] = p;
                }
            }

            double dl = closestPairRecursive(px, pyLeft, left, mid);
            double dr = closestPairRecursive(px, pyRight, mid + 1, right);

            double d = Math.min(dl, dr);

            // Check strip
            return Math.min(d, stripClosest(py, midPoint.x, d));

        } finally {
            metrics.exitRecursion();
            DepthTracker.exit();
        }
    }

    private double bruteForce(Point[] points, int left, int right) {
        double minDist = Double.POSITIVE_INFINITY;
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                metrics.incrementComparisons();
                double dist = points[i].distanceTo(points[j]);
                minDist = Math.min(minDist, dist);
            }
        }
        return minDist;
    }

    private double stripClosest(Point[] strip, double midX, double d) {
        int j = 0;
        for (Point p : strip) {
            if (Math.abs(p.x - midX) < d) {
                auxY[j++] = p;
            }
        }

        double minDist = d;
        for (int i = 0; i < j; i++) {
            for (int k = i + 1; k < j && (auxY[k].y - auxY[i].y) < minDist; k++) {
                metrics.incrementComparisons();
                double dist = auxY[i].distanceTo(auxY[k]);
                minDist = Math.min(minDist, dist);
            }
        }
        return minDist;
    }

    public MetricsCollector getMetrics() {
        return metrics;
    }
}