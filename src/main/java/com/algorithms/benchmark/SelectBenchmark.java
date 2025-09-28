package com.algorithms.benchmark;

import com.algorithms.core.DeterministicSelect;
import org.openjdk.jmh.annotations.*;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
public class SelectBenchmark {

    @Param({"100", "1000", "10000"})
    private int size;

    private int[] data;
    private DeterministicSelect selector;

    @Setup
    public void setup() {
        data = new Random().ints(size).toArray();
        selector = new DeterministicSelect();
    }

    @Benchmark
    public int testSelect() {
        return selector.select(data.clone(), size / 2);
    }

    @Benchmark
    public int testSortBasedSelect() {
        int[] copy = data.clone();
        Arrays.sort(copy);
        return copy[size / 2];
    }
}