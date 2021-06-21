package com.examples;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StreamReducingExamples {

	public static void main(String[] args) {
		sumAllElementsUsingReduce();
		getMaxUsingReduce();
		getMinUsingReduce();
		countElementsUsingReduce();
	}
	
	public static void countElementsUsingReduce() {
		List<Integer> numbers = Arrays.asList(14, 3, 50, 10, 33, 4, 89);
		int count = numbers.stream().reduce(0, (a, b) -> a + 1);
		System.out.println("Count elements of a Stream (My solution): " + count);
		int count2 = numbers.stream()
				.map(d -> 1) // notice how we map element to a 1
				.reduce(0, (a, b) -> a + b);
		System.out.println("Count elements of a Stream (Book solution): " + count2);
	}
	
	public static void getMaxUsingReduce() {
		List<Integer> numbers = Arrays.asList(14, 3, 50, 10, 33);
		int max1 = numbers.stream().reduce(10, (a, b) -> a > b ? a : b);
		System.out.println("1. Max using reduce: " + max1);
		int max2 = numbers.stream().reduce(10, Integer::max);
		System.out.println("2. Max using Integer::max : " + max2);
	}
	
	public static void getMinUsingReduce() {
		List<Integer> numbers = Arrays.asList(14, 3, 50, 10, 33);
		int min1 = numbers.stream().reduce(0, (a, b) -> a > b ? b : a);
		System.out.println("1. Min using reduce: " + min1);
		int min2 = numbers.stream().reduce(0, Integer::min);
		System.out.println("2. Min using Integer::max : " + min2);
	}
	
	public static void sumAllElementsUsingReduce() {
		List<Integer> numbers = Arrays.asList(1, 3, 5, 10, 33);
		int sum = numbers.stream().reduce(0, (a, b) -> a + b);
		System.out.println("First Sum using reduce: " + sum);
		int sum2 = numbers.stream().reduce(1, (a, b) -> a + b);
		System.out.println("Second Sum using reduce: " + sum2);
		int sum3 = numbers.stream().reduce(1, Integer::sum);
		System.out.println("Second Sum using Integer::sum " + sum3);
		
		// Notice how without initial value, an Optional is returned
		Optional<Integer> optionalSum = numbers.stream().reduce((a, b) -> (a + b));
	}
}
