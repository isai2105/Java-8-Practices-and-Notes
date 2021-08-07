package com.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import com.bean.Dish;
import com.bean.collector.PrimeNumbersCollector;
import com.bean.collector.ToListCollector;

public class CustomCollectorExample {

	public static void main(String[] args) {
		// Custom Collector implementing the Collector interface
		implementingCollectorInterface();
		// Custom collect WITHOUT implementing the Collector Interface
		customCollectWithoutImplementingCollectorInterface();
		// A more complex custom collector (prime vs nonprime numbers)
		customPrimeNumbersCollector();
		// Testing performances (implementing Collector interface vs Overloaded collect() method)
		testingPerformances();
	}
	
	private static void testingPerformances() {
		// Testing the performance of our custom Prime numbers Collector
		long fastest = Long.MAX_VALUE;
		for (int i = 0; i < 10; i++) { // running the test 10 times
			long start = System.nanoTime();
			partitionPrimesWithCustomCollector(1_000_000);
			long duration = (System.nanoTime() - start) / 1_000_000;
			if (duration < fastest) fastest = duration;
		}
		System.out.println(
			"Custom Prime number Collector ... Fastest execution done in " + 
			fastest + 
			" msecs"
		);
		
		// Testing the performance of using a custom collector by
		// using the overloaded collect() method
		fastest = Long.MAX_VALUE;
		for (int i = 0; i < 10; i++) { // running the test 10 times
			long start = System.nanoTime();
			primesWithCustomCollectorNoInterfaceImpl(1_000_000);
			long duration = (System.nanoTime() - start) / 1_000_000;
			if (duration < fastest) fastest = duration;
		}
		System.out.println(
			"Using overloaded collect() method ... Fastest execution done in " + 
			fastest + 
			" msecs"
		);
		
	}
	
	/*
	 * Stream has an overloaded collect() method that accepts:
	 * - supplier, accumulator and combiner
	 * - This methods assumes always a Function.identityFunction() as 
	 * a result of the finisher() method (because it does not accept it as parameter).
	 * 
	 * It always behaves as an IDENTITY_FINISH and CONCURRENT
	 * but not UNORDERED collector.
	 * */
	private static void customCollectWithoutImplementingCollectorInterface() {
		List<Dish> menu = createListOfDishes();
		List<Dish> dishes = menu.stream().collect(
				ArrayList::new,
				List::add,
				List::addAll);
		System.out.println("Custom Collector using overloaded collect() method ... " + dishes);
	}
	
	/*
	 * Using a custom Collector: ToListCollector
	 * Developed by implementing the Collector interface.
	 * */
	private static final void implementingCollectorInterface() {
		List<Dish> menu = createListOfDishes();
		List<Dish> dishes = menu.stream().collect(new ToListCollector<Dish>());
		System.out.println("Custom Collector from Custom Interface ... " + dishes);
	}
	
	private static void customPrimeNumbersCollector() {
		System.out.println(
			"Custom Prime Numbers Collector: " +
			partitionPrimesWithCustomCollector(21)
		);
	}
	
	private static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n) {
		return 
			IntStream.rangeClosed(2, n)
				.boxed()
				.collect(new PrimeNumbersCollector());
	}
	
	private static Map<Boolean, List<Integer>> primesWithCustomCollectorNoInterfaceImpl
	(int n) {
		return
			IntStream.rangeClosed(2, n).boxed()
			.collect(
				() -> 
					new HashMap<Boolean, List<Integer>>() {
						{
							put(true, new ArrayList<Integer>());
							put(false, new ArrayList<Integer>());
						}
					},
				(acc, candidate) -> {
					acc.get( PrimeNumbersCollector.isPrime(acc.get(true), candidate) )
						.add(candidate);
				},
				(map1, map2) -> {
					map1.get(true).addAll(map2.get(true));
					map1.get(false).addAll(map2.get(false));
				}
			);
	}
	
	private static List<Dish> createListOfDishes() {
		List<Dish> menu = Arrays.asList(
				new Dish("pork", false, 800, Dish.Type.MEAT),
				new Dish("beef", false, 700, Dish.Type.MEAT),
				new Dish("chicken", false, 400, Dish.Type.MEAT),
				new Dish("french fries", true, 530, Dish.Type.OTHER),
				new Dish("rice", true, 350, Dish.Type.OTHER),
				new Dish("season fruit", true, 120, Dish.Type.OTHER),
				new Dish("pizza", true, 550, Dish.Type.OTHER),
				new Dish("prawns", false, 300, Dish.Type.FISH),
				new Dish("salmon", false, 450, Dish.Type.FISH) );
		return menu;
	}
}
