package com.examples;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import com.bean.Apple;

public class ComposingChaining {

	/*
	 * IMPORTANTE NOTE
	 * 
	 * How it is that interfaces that can only have one abstract method ... 
	 * have the following methods to chain... 
	 * This is because of the possibility of DEFAULT METHODS.
	 * */
	public static void main(String[] args) {
		
		// Chaining COMPARATORS
		List<Apple> applesList = createApplesList();
		applesList.sort(Comparator.comparing(Apple::getWeight)
				.reversed()
				.thenComparing(Apple::getColor));
		
		// Chaining PREDICATES (negate, and, or)
		Predicate<Apple> redApple = 
				a -> a.getColor().equalsIgnoreCase("red");
		Predicate<Apple> notRedApple = redApple.negate();
		// Which reads:
		// (red || heavy) && green
		// read exactly as read from left to right
		Predicate<Apple> redOrHeavyAppleAndGreen =
				redApple.or(a -> a.getWeight() > 150)
				.and(a -> "green".equals(a.getColor()));
		
		
		// Chaining Functions (andThen && Compose)
		Function<Integer, Integer> f0 = x -> x + 1;
		Function<Integer, Integer> g0 = x -> x * 2;
		Function<Integer, Integer> h0 = f0.andThen(g0);// g(f(x))
		int result0 = h0.apply(1); // result = 4
		
		Function<Integer, Integer> f1 = x -> x + 1;
		Function<Integer, Integer> g1 = x -> x * 2;
		Function<Integer, Integer> h1 = f1.compose(g1); // f(g(x))
		int result1 = h1.apply(1); // result = 3
	}
	
    private static List<Apple> createApplesList() {
    	List<Apple> applesList = new ArrayList<>();
    	Apple apple1 = new Apple(0, 160F, "type1", "red");
    	Apple apple2 = new Apple(0, 2F, "type2", "green");
    	Apple apple3 = new Apple(0, 5F, "type3", "red");
    	applesList.add(apple1);
    	applesList.add(apple2);
    	applesList.add(apple3);
    	return applesList;
    }
}
