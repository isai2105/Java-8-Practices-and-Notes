package com.bean.forkJoin;

public class ForkJoinSumCalculator extends java.util.concurrent.RecursiveTask<Long> {

	private final long[] numbers;
	private final int start;
	private final int end;
	
	public static final long THRESHOLD = 10_000;
	
	public ForkJoinSumCalculator(long[] numbers) {
		this(numbers, 0, numbers.length);
	}
	
	private ForkJoinSumCalculator(long[] numbers, int start, int end) {
		this.numbers = numbers;
		this.start = start;
		this.end = end;
	}
	
	@Override
	protected Long compute() {
		int length = end - start;
		if (length <= THRESHOLD) {
			return computeSequentially();
		}
		ForkJoinSumCalculator leftTask =
				new ForkJoinSumCalculator(numbers, start, start + length/2);
		leftTask.fork(); // we put it in the pool
		ForkJoinSumCalculator rightTask =
				new ForkJoinSumCalculator(numbers, start + length/2, end);
		Long rightResult = rightTask.compute(); // instead of putting this in the pool, we reuse the current thread
		Long leftResult = leftTask.join(); // the ones in the pool need to be joined
		return leftResult + rightResult;
	}
	
	private long computeSequentially() {
		long sum = 0;
		for (int i = start; i < end; i++) {
			sum += numbers[i];
		}
		return sum;
	}
}
