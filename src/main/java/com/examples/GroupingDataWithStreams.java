package com.examples;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.bean.Dish;

public class GroupingDataWithStreams {

	public static void main(String[] args) {
		// Custom Grouping function
		customGrouping();
		// Multi Level Grouping
		multiLevelGrouping();
		// Collecting Data in SubGroups
		collectingDataInSubgroups();
	}
	
	/*
	 * As we saw in the multi level grouping example ...
	 * We can pass a Collector as a second parameter of the groupingBy
	 * */
	private static void collectingDataInSubgroups() {
		List<Dish> menu = createListOfDishes();
		// Count per Dish Type
		Map<Dish.Type, Long> typesCount = menu.stream().collect(
				Collectors.groupingBy(Dish::getType, Collectors.counting()));
		System.out.println("Collecting Data in Subgroups (Count per Dish Type): " + typesCount);
		// Max calories per type
		Map<Dish.Type, Optional<Dish>> mostCaloricByType =
				menu.stream()
				.collect(
					Collectors.groupingBy(
						Dish::getType,
						Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))));
		System.out.println("Collecting Data in Subgroups (Most Caloric per Dish Type): " + mostCaloricByType);
		// What if you want to get rid of Optional<Dish>
		// The, you could use: collectingAndThen Collector, as shown below:
		// This allows us to map the result to a different type
		// In this case, it is safe to use OPtional get .. because the reducing collector does not
		// take into account empty optionals
		Map<Dish.Type, Dish> mostCaloricByTypeWithoutOptional =
			menu.stream()
			.collect(
				Collectors.groupingBy(
					Dish::getType,
					Collectors.collectingAndThen(
						Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)),
						Optional::get
					)
				)
			);
		
		// groupingBy + mapping
		// The mapping allows transforming elements to the desired type and then accumulate those 
		// in the collectors specified in the second parameter.
		Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType =
			menu.stream().collect(
				Collectors.groupingBy(
					Dish::getType, 
					Collectors.mapping(
						dish -> { // transformation function
							if (dish.getCalories() <= 400) return CaloricLevel.DIET;
							else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
							else return CaloricLevel.FAT; 
						},
						// Note: we could specify which type of Set we want
						// Collectors.toCollection(HashSet::new)
						Collectors.toSet()  // Collector accumulator
					)
				)
			);
		System.out.println("Using groupingBy + mapping = " + caloricLevelsByType);
	}
	
	// NOTE:
	// we can pass another collector as a second argument of groupingBy
	// And so on .. we can have many subgroups within subgroups
	private static void multiLevelGrouping() {
		List<Dish> menu = createListOfDishes();
		Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel =
			menu.stream()
				.collect(
					Collectors.groupingBy(
						Dish::getType,
						Collectors.groupingBy(dish -> {
							if (dish.getCalories() <= 400) return CaloricLevel.DIET;
							else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
							else return CaloricLevel.FAT;
						})
					)
				);
		System.out.println("Using Multilevel Grouping: " + dishesByTypeCaloricLevel);
	}
	
	// It is not always possible to group by a property accessor (getter)
	// sometimes we nneed to implement this classification function
	private static void customGrouping() {
		List<Dish> menu = createListOfDishes();
		Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = 
			menu.stream()
				.collect(
						Collectors.groupingBy(dish -> {
							if (dish.getCalories() <= 400) return CaloricLevel.DIET;
							else if (dish.getCalories() <= 700) return
									CaloricLevel.NORMAL;
							else return CaloricLevel.FAT;
						} )
				);
		System.out.println("Using Custom Grouping: " + dishesByCaloricLevel);
	}
	
	public enum CaloricLevel { DIET, NORMAL, FAT }
	
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
