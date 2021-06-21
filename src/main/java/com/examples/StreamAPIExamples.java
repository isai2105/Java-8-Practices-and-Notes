package com.examples;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.bean.Dish;
import com.bean.Trader;
import com.bean.Transaction;

public class StreamAPIExamples {

	public static void main(String[] args) {
		someStreamExamples();
		// Trying the Lazy nature of streams
		lazyNatureOfStreams();
		// Map vs FlatMap
		mapVsFlatMap();
		// Another use of FlatMap
		anotherUseOfFlatMap();
		// Book Exercises
		bookExercises();
		// Pythagorean Triples
		pythagoreanTriples();
		// Creating Streams of different ways
		creatingStreamsFromDiffWays();
		// Infinite Streams
		infiniteStreams();
		// Fibonacci Using Iterate
		fibonacciUsingIterate();
		// Fibonacci Using Generate (infinite Stream)
		fibonacciUsingGenerate();
	}
	
	private static void fibonacciUsingIterate() {
		System.out.println("Fibonacci using ITERATE: " +
			Stream.iterate(
					new int[]{0, 1}, t -> new int[]{t[1], t[0]+t[1]})
				.limit(20)
				.map(it -> it[0])
				.collect(Collectors.toList())
		);
	}
	
	// Example of an advanced use of the Infinite stream
	private static void fibonacciUsingGenerate() {
		// The Generate stream method + an anonymous class
		// allows "saving" the state of the stream (even though generate accepts a Supplier only)
		IntSupplier fib = new IntSupplier() {
			private int previous = 0;
			private int current = 1;
			public int getAsInt() {
				int oldPrevious = this.previous;
				int nextValue = this.previous + this.current;
				this.previous = this.current;
				this.current = nextValue;
				return oldPrevious;
			}
		};
		System.out.println("Fibonacci using GENERATE: " +
			IntStream.generate(fib).limit(20).boxed().collect(Collectors.toList())
		);
	}
	
	private static void infiniteStreams() {
		// Stream from functions (Infinite Streams)
		// ITERATE
		// When you need a stream of successive values
		System.out.println("Infinite Stream using ITERATE: " +
			Stream.iterate(0, n -> n + 2)
			.limit(10)
			.collect(Collectors.toList())
		);
		// GENERATE
		System.out.println("Infinite Stream using GENERATE: " +
			Stream.generate(Math::random)
			.limit(5)
			.collect(Collectors.toList())
		);
	}
	
	private static void creatingStreamsFromDiffWays() {
		//Stream from values
		Stream<String> streamFromValues = Stream.of("Java 8 ", "Lambdas ", "In ", "Action");
		// Stream from arrays
		int[] numbers = {2, 3, 5, 7, 11, 13};
		int sum = Arrays.stream(numbers).sum();
		// From  a File
		long uniqueWords = 0;
		try(Stream<String> lines = // Streams are autoclosable
				Files.lines(Paths.get("data.txt"), Charset.defaultCharset())){
			// Generating a stream of words:
			uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
					.distinct()
					.count();
		}
		catch(IOException e) {
		}
		// Stream from functions (Infinite Streams)
		// ITERATE
		Stream.iterate(0, n -> n + 2)
		.limit(10);
		// GENERATE
		Stream.generate(Math::random)
		.limit(5);
	}
	
	private static void pythagoreanTriples() {
		String pythagoreanTriples2 =
				IntStream.rangeClosed(1, 100).boxed()
				.flatMap(a ->
				IntStream.rangeClosed(a, 100)
				.mapToObj(
				b -> new double[]{a, b, Math.sqrt(a*a + b*b)})
				.filter(t -> t[2] % 1 == 0))
				.map(t -> "( " + t[0] + ", " + t[1] + ", " + t[2] + " )")
				.collect(Collectors.joining(", "));
		System.out.println("pythagoreanTriples = " + pythagoreanTriples2);
	}
	
	private static void bookExercises() {
		Trader raoul = new Trader("Raoul", "Cambridge");
		Trader mario = new Trader("Mario","Milan");
		Trader alan = new Trader("Alan","Cambridge");
		Trader brian = new Trader("Brian","Cambridge");
		List<Transaction> transactions = 
			Arrays.asList(
				new Transaction(brian, 2011, 300, null),
				new Transaction(raoul, 2012, 1000, null),
				new Transaction(raoul, 2011, 400, null),
				new Transaction(mario, 2012, 710, null),
				new Transaction(mario, 2012, 700, null),
				new Transaction(alan, 2012, 950, null)
			);
		// 1- Find all transactions in the year 2011 and sort them by value (small to high).
		System.out.println("1 - All transactions in the year 2011 and sorted: " +
			transactions.stream()
				.filter(t -> t.getYear() == 2011)
				.sorted((t1, t2) -> t1.getValue() >= t2.getValue() ? 1 : -1)
				// .sorted(Comparator.comparing(Transaction::getValue))  => BOOK Solution
				.map(t -> ("[ Transaction Amount: " + t.getValue() + ", Trader Name: " + t.getTrader().getName() + " ]"))
				.collect(Collectors.joining(", "))
		);
			
		// 2- What are all the unique cities where the traders work?
		System.out.println("2 - ALl the unique cities are: " +
			transactions.stream()
				.map(t -> t.getTrader().getCity())
				.distinct()
				.collect(Collectors.joining(", "))
				// .collect(Collectors.toSet()) => with this, we avoid the distinct() [Book Solution]
		);
		// 3- Find all trader from Cambridge and sort them by name
		System.out.println("3 - All traders from Cambridge: " +
			transactions.stream()
				.filter(t -> t.getTrader().getCity().equalsIgnoreCase("cambridge"))
				.map(t -> t.getTrader().getName())
				.distinct()
				.sorted(String::compareTo)
				.collect(Collectors.joining(", "))
		);
		// 4- Return a string of all traders’ names sorted alphabetically.
		System.out.println("4 - All traders: " +
			transactions.stream()
				.map(t -> t.getTrader().getName())
				.distinct()
				.sorted(String::compareTo)
				.collect(Collectors.joining(", "))
		);
		// 5- Are any traders based in Milan?
		System.out.println("5 - Are any traders based in Milan?: " +
			transactions.stream()
				.anyMatch(t -> t.getTrader().getCity().equalsIgnoreCase("Milan"))
		);
		// 6-  Print all transactions’ values from the traders living in Cambridge.
		System.out.println("6 - all transactions’ values from the traders living in Cambridge: " +
			transactions.stream()
				.filter(t -> t.getTrader().getCity().equalsIgnoreCase("cambridge"))
				.map(t -> t.getValue())
				.sorted(Integer::compareTo)
				.collect(Collectors.toList())
		);
		// 7- What’s the highest value of all the transactions?
		System.out.println("7 - The highest value of all the transactions: " +
				transactions.stream()
				.map(t -> t.getValue())
				.reduce(0, (a, b) -> a >= b ? a : b)// Integer::max 
		);
		// 8- Find the transaction with the smallest value.
		Optional<Integer> minValueOpt = 
				transactions.stream()
				.map(t -> t.getValue())
				.reduce((a, b) -> a >= b ? b : a);// Integer::min
		System.out.println(
				"8 - The transaction with the smallest value: " + minValueOpt.get()
			);
		// The BOOK Solution for #8:
		Optional<Transaction> smallestTransaction =
				transactions.stream()
				.min(Comparator.comparing(Transaction::getValue));
	}
	
	/*
	 * Given 2 lists of numbers, return all pairs of numbers whose sum is divisible by 3
	 * For example, given:
	 * List1 = [1,2,3] and List2 = [3,4]
	 * 
	 * return [(1, 3), (1, 4), (2, 3), (2, 4), (3, 3), (3, 4)]
	 * */
	private static void anotherUseOfFlatMap() {
		List<Integer> numbers1 = Arrays.asList(1, 2, 3);
		List<Integer> numbers2 = Arrays.asList(3, 4);
		List<String> pairs =
			numbers1.stream()
			.flatMap(i -> 
					numbers2.stream()
					.filter(j -> (i + j) % 3 == 0)
					.map(j -> "{" + i + ", " + j + "}")
			)
			.collect(Collectors.toList());
		System.out.println("pairs = " + pairs);
	}
	
	private static void mapVsFlatMap() {
		List<String> words = 
				Arrays.asList("Hello", "World");
		List<String> uniqueCharacters =
				words.stream()
				.map(w -> w.split("")) // Stream<String[]>
				.flatMap(Arrays::stream) // flatMap unifies the streams created by the Arrays::stream method
				.distinct()
				.collect(Collectors.toList());
		System.out.println("- uniqueCharacters = " + uniqueCharacters);
	}
	
	private static void lazyNatureOfStreams() {
		List<Dish> menu = createListOfDishes();
		List<String> names =
			menu.stream()
			.filter(d -> {
				System.out.println("filtering" + d.getName());
				return d.getCalories() > 300;
			})
			.map(d -> {
				System.out.println("mapping" + d.getName());
				return d.getName();
			})
			.limit(3)
			.collect(Collectors.toList());
			System.out.println(names);
			/*
			 * filteringpork
				mappingpork
				filteringbeef
				mappingbeef
				filteringchicken
				mappingchicken
				[pork, beef, chicken]
			 * */
	}
	
	private static void someStreamExamples() {
		List<Dish> menu = createListOfDishes();
		// Three High Calories Dishes
		List<String> threeHighCaloricDishNames =
				menu.stream()
				.filter(it -> it.getCalories() > 300)
				.map(Dish::getName)
				.limit(3)
				.collect(Collectors.toList());
		System.out.println("Three High Caloric Dishes: " + threeHighCaloricDishNames);
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
