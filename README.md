# Divide and Conquer Algorithms Implementation

## Overview
Implementation of classic divide-and-conquer algorithms with performance metrics collection and analysis.

## Algorithms Implemented
1. **MergeSort** - O(n log n) stable sorting with buffer reuse
2. **QuickSort** - Randomized with bounded recursion depth
3. **Deterministic Select** - O(n) median-of-medians selection
4. **Closest Pair of Points** - O(n log n) 2D geometric algorithm

## Architecture Notes

### Recursion Depth Control
- **QuickSort**: Implements tail recursion optimization by iterating on larger partition
- **All algorithms**: Use ThreadLocal depth tracking to monitor stack usage
- **Small-n cutoff**: Switch to insertion sort for arrays < 10 elements

### Memory Allocation Control
- **MergeSort**: Reuses single buffer allocation throughout recursion
- **Select**: Allocates median array proportional to n/5
- **Metrics tracking**: Monitors all array allocations

## Recurrence Analysis

### MergeSort
- **Recurrence**: T(n) = 2T(n/2) + Θ(n)
- **Master Theorem Case 2**: a = 2, b = 2, f(n) = Θ(n)
- **Result**: Θ(n log n)

### QuickSort (Average Case)
- **Recurrence**: T(n) = T(n/4) + T(3n/4) + Θ(n) (expected)
- **Akra-Bazzi**: p = 1, gives Θ(n log n)
- **Note**: Worst case Θ(n²) avoided via randomization

### Deterministic Select
- **Recurrence**: T(n) ≤ T(n/5) + T(7n/10) + Θ(n)
- **Akra-Bazzi**: p < 1, gives Θ(n)
- **Guaranteed linear time regardless of input

### Closest Pair
- **Recurrence**: T(n) = 2T(n/2) + Θ(n)
- **Master Theorem Case 2**: Same as MergeSort
- **Result**: Θ(n log n)

## Building and Running
```bash
# Build
mvn clean compile

# Run tests
mvn test

# Run specific algorithm
mvn exec:java -Dexec.mainClass="com.algorithms.cli.AlgorithmRunner" \
              -Dexec.args="mergesort 10000"

# Generate metrics CSV
java -cp target/classes com.algorithms.cli.AlgorithmRunner quicksort 10000
```
# Divide and Conquer Algorithms Implementation

## Table of Contents
- [Overview](#overview)
- [Algorithms Implemented](#algorithms-implemented)
- [Architecture Notes](#architecture-notes)
- [Recurrence Analysis](#recurrence-analysis)
- [Performance Results](#performance-results)
- [Build and Run Instructions](#build-and-run-instructions)
- [Testing Strategy](#testing-strategy)
- [Conclusions](#conclusions)

## Overview

This project implements four classic divide-and-conquer algorithms with comprehensive performance metrics collection and analysis. Each algorithm demonstrates different recurrence patterns and complexity classes, validated through both theoretical analysis and empirical measurements.

**Key Features:**
- Safe recursion patterns with bounded stack depth
- Reusable memory buffers to minimize allocations
- Small-n cutoff optimizations
- Comprehensive metrics tracking (time, depth, comparisons)
- Automated testing and benchmarking

## Algorithms Implemented

### 1. MergeSort
- **Time Complexity:** O(n log n) guaranteed
- **Space Complexity:** O(n) with buffer reuse
- **Stability:** Yes (preserves relative order)
- **Key Optimizations:**
    - Single buffer allocation reused throughout recursion
    - Insertion sort cutoff for n < 10
    - Iterative merging to reduce function call overhead

### 2. QuickSort
- **Time Complexity:** O(n log n) expected, O(n²) worst case
- **Space Complexity:** O(log n) stack space expected
- **Stability:** No
- **Key Optimizations:**
    - Randomized pivot selection
    - Tail recursion optimization (recurse on smaller partition)
    - Bounded stack depth ≈ 2⌊log₂ n⌋ + O(1)
    - Insertion sort cutoff for small subarrays

### 3. Deterministic Select (Median-of-Medians)
- **Time Complexity:** O(n) guaranteed
- **Space Complexity:** O(log n) stack space
- **Key Features:**
    - Groups of 5 for optimal constant factors
    - In-place partitioning
    - Guaranteed worst-case linear time

### 4. Closest Pair of Points
- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)
- **Key Features:**
    - Divide-and-conquer with geometric pruning
    - Strip optimization (maximum 7-8 point comparisons)
    - Efficient handling of boundary cases

## Architecture Notes

### Recursion Depth Control

The project implements multiple strategies to control recursion depth and prevent stack overflow:

1. **Tail Recursion Optimization (QuickSort)**
```java
   // Recurse on smaller partition, iterate on larger
   if (partitionIndex - left < right - partitionIndex) {
       quickSort(arr, left, partitionIndex - 1);  // Recurse (smaller)
       left = partitionIndex + 1;                  // Iterate (larger)
   }
```
