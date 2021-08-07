package com.examples;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelStreamsExamples {

	public static void main(String[] args) {
		// testing performance parallel vs no parallel streams
		firstPerformanceTest(10_000_000);
		// Getting the number of processors available in my machine:
		System.out.println(
			"# of processors available in my machine: " + 
			Runtime.getRuntime().availableProcessors()
		);
	}
	
	private static void firstPerformanceTest(long n) {
		// No SURPRISE (108 msecs)
		System.out.println(
			"Sequential sum done in: " +
			measureSumPerf(t -> sequentialSum(t), n) + 
			" msecs"
		);
		// SURPRISINGLY: the fastest one (3 msecs)
		System.out.println(
			"Iterative sum done in: " +
			measureSumPerf(t -> iterativeSum(t), n) + 
			" msecs"
		);
		// DISSAPOINTING: the parallel sum was the slowest one (100 msecs)
		// 
		/*
		 * IMPORTANT EXPLANATION:
		 * 
		 * 1. Iterate generates boxed objects, which have to be 
		 * unboxed to numbers before they can be added.
		 * 2. Iterate is difficult to divide into independent 
		 * chunks to execute in parallel (the input of one function depends 
		 * of the result of the previous function "i -> i + 1"). This means, difficult to divide in Chunks.
		 * */
		System.out.println(
			"Parallel sum done in: " +
			measureSumPerf(t -> parallelSum(t), n) + 
			" msecs" 
		);
		
		/*
		 * Finally, we can see the parallel stream working correctly by 
		 * using a correct data structure: LongStream
		 * 
		 * LongStream.rangeClosed works on primitive long numbers directly 
		 * so there’s no boxing and unboxing overhead.
		 * 
		 * LongStream.rangeClosed produces ranges of numbers, which can 
		 * be easily split into independent chunks.
		 * */
		System.out.println(
			"Non Parallel range sum done in: " +
			measureSumPerf(t -> rangedSum(t), n) +
			" msecs"
		);
		
		// The FASTEST of all
		System.out.println(
			"Parallel range sum done in: " +
			measureSumPerf(t -> parallelRangedSum(t), n) +
			" msecs"
		);
	}
	
	public static long parallelRangedSum(long n) {
		return LongStream.rangeClosed(1, n)
		.parallel()
		.reduce(0L, Long::sum);
	}
	
	public static long rangedSum(long n) {
		return LongStream.rangeClosed(1, n)
		.reduce(0L, Long::sum);
	}
	
	private static long sequentialSum(long n) {
		return Stream.iterate(1L, i -> i + 1)
		.limit(n)
		.reduce(0L, Long::sum);
	}
	
	private static long iterativeSum(long n) {
		long result = 0;
		for (long i = 1L; i <= n; i++) {
			result += i;
		}
		return result;
	}
	
	private static long parallelSum(long n) {
		return 
			Stream.iterate(1L, i -> i + 1)
				.limit(n)
				.parallel()
				.reduce(0L, Long::sum);
	}
	
	private static long measureSumPerf(Function<Long, Long> adder, long n) {
		long fastest = Long.MAX_VALUE;
		for (int i = 0; i < 10; i++) {
			long start = System.nanoTime();
			long sum = adder.apply(n);
			long duration = (System.nanoTime() - start) / 1_000_000;
			if (duration < fastest) fastest = duration;
		}
		return fastest;
	}
}
