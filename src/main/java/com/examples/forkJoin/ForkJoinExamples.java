package com.examples.forkJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

import com.bean.forkJoin.ForkJoinSumCalculator;

public class ForkJoinExamples {

	public static void main(String[] args) {
		System.out.println("Summing the first: 10.000.000 numbers ...");
		System.out.println("Result: " + forkJoinSum( 10000000 ));
	}
	
	public static long forkJoinSum(long n) {
		long[] numbers = LongStream.rangeClosed(1, n).toArray();
		ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
		// it is usually a good idea to have this ForkJoinPool
		// in a static variable so we can reuse it among other pieces of code
		return new ForkJoinPool().invoke(task); // "invoke" can only be called in sequential code, never inside parallel code
	}
	
}
