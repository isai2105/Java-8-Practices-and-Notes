package com.examples;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bean.Dish;

public class PartitioningDataWithStreams {

	/*
	 * 
	 Partitioning is a special case of grouping: having a predicate 
	 (a function returning a boolean), called a partitioning function.
	 * */
	public static void main(String[] args) {
		// Simple Partitioning
		simplePartitioning();
		// Grouped Partitioning (overloaded version)
		overloadedPartitioning();
		// Multilevel Partitioning
		multiLevelPartitioning();
	}
	
	private static void multiLevelPartitioning() {
		List<Dish> menu = createListOfDishes();
		Map<Boolean, Dish> mostCaloricPartitionedByVegetarian =
			menu
				.stream()
				.collect(
					Collectors.partitioningBy(
						Dish::isVegetarian,
						Collectors.collectingAndThen(
							Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)),
							Optional::get
						)
					)
				);
		System.out.println("Multi Level partitioning: " + mostCaloricPartitionedByVegetarian);
	}
	
	private static void overloadedPartitioning() {
		List<Dish> menu = createListOfDishes();
		Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType =
			menu.stream()
				.collect(
					Collectors.partitioningBy(
						Dish::isVegetarian,
						Collectors.groupingBy(Dish::getType)
					)
				);
		System.out.println("overloadedPartitioning: " + vegetarianDishesByType);
		System.out.println("Counting as Collector: " +
			menu.stream()
				.collect(
					Collectors.partitioningBy(
						Dish::isVegetarian,
						Collectors.counting()
					)
				)
		);
	}
	
	private static void simplePartitioning() {
		List<Dish> menu = createListOfDishes();
		Map<Boolean, List<Dish>> partitionedMenu =
				menu.stream()
					.collect(
						Collectors.partitioningBy(Dish::isVegetarian));
		System.out.println("simplePartitioning: " + partitionedMenu);
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
