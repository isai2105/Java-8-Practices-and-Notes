package com.examples;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Currency;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bean.Dish;
import com.bean.Trader;
import com.bean.Transaction;

public class CollectingDataWithStreams {

	public static void main(String[] args) {
		// Group transactions by currency
		groupTransactionsByCurrency();
		// Finding max & min in a Stream of values
		findMinAndMaxValuesInStream();
		// Summing & Average
		summingAndAverageAnsStatictics();
		// General Reducing
		generalReducing();
	}
	
	// All collectors can be implemented using the Collectors.reducing()
	// But Java8 have the specialized ones to help programmers!
	private static void generalReducing() {
		List<Dish> menu = createListOfDishes();
		// The following can be replace with a specific Collector
		// Notice how this reduce could be replaced with the existing:
		// Collectors.summarizingInt(Dish::getCalories)
		int totalCalories = 
				menu.stream()
				.collect(
					Collectors.reducing(0,// 0 is the initial value
						Dish::getCalories, // transformation function
						Integer::sum) // Aggregation function
				);
		
		Optional<Dish> mostCalorieDish =
			menu.stream()
				.collect(
					Collectors.reducing(
							(d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)
				);
	}
	
	private static void summingAndAverageAnsStatictics() {
		List<Dish> menu = createListOfDishes();
		// Average
		double avgCalories =
				menu.stream().collect(Collectors.averagingInt(Dish::getCalories));
		System.out.println("Average calories in menu: " + avgCalories);
		// Summing
		double sumCalories =
				menu.stream().collect(Collectors.summingInt(Dish::getCalories));
		System.out.println("Summing calories in menu: " + sumCalories);
		// Statistics Object
		IntSummaryStatistics menuStatistics =
				menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
		System.out.println(
			String.format(
				"Count: %s, Average: %s, Sum: %s ... etc ...", 
				menuStatistics.getCount(), 
				menuStatistics.getAverage(),
				menuStatistics.getSum())
		);
	}
	
	private static void findMinAndMaxValuesInStream() {
		List<Dish> menu = createListOfDishes();
		Comparator<Dish> dishCaloriesComparator =
				Comparator.comparingInt(Dish::getCalories);
		Optional<Dish> mostCalorieDish =
		menu.stream()
		.collect(Collectors.maxBy(dishCaloriesComparator));
		// Another solution:
		// .collect(Collectors.maxBy((a, b) -> a.getCalories() >= b.getCalories() ? 1 : -1 ));
		if (mostCalorieDish.isPresent()) {
			System.out.println("Most Calories Dish: " + mostCalorieDish.get().getName());
		}
		Optional<Dish> lessCalorieDish =
		menu.stream()
		.collect(Collectors.minBy(dishCaloriesComparator));
		// Another solution:
		// .collect(Collectors.minBy((a, b) -> a.getCalories() >= b.getCalories() ? 1 : -1 ));
		if (lessCalorieDish.isPresent()) {
			System.out.println("Less Calories Dish: " + lessCalorieDish.get().getName());
		}
	}
	
	private static void groupTransactionsByCurrency() {
		List<Transaction> transactions = createTransactions();
		// important use of groupingBy
		Map<Currency, List<Transaction>> transactionsByCurrencies =
				transactions.stream()
				.collect(Collectors.groupingBy(Transaction::getCurrency));
		
		transactionsByCurrencies.forEach(
			(currency, listTransactions) -> {
				System.out.println(
						"Currency: " + currency + 
						"  transactions: " +
						listTransactions.stream()
							.map(it -> "Value: " + it.getValue())
							.collect(Collectors.joining(", ")));
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
	
	private static List<Transaction> createTransactions() {
		Trader raoul = new Trader("Raoul", "Cambridge");
		Trader mario = new Trader("Mario","Milan");
		Trader alan = new Trader("Alan","Cambridge");
		Trader brian = new Trader("Brian","Cambridge");
		List<Transaction> transactions = 
			Arrays.asList(
				new Transaction(brian, 2011, 300, Currency.getInstance(Locale.US)),
				new Transaction(raoul, 2012, 1000, Currency.getInstance(Locale.UK)),
				new Transaction(raoul, 2011, 400, Currency.getInstance(Locale.UK)),
				new Transaction(mario, 2012, 710, Currency.getInstance(Locale.US)),
				new Transaction(mario, 2012, 700, Currency.getInstance(Locale.US)),
				new Transaction(alan, 2012, 950, Currency.getInstance(Locale.US))
			);
		return transactions;
	}
	
}
