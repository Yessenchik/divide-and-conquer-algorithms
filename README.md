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