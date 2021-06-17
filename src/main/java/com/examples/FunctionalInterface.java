package com.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import com.bean.Apple;

/*
 * IMPORTANT NOTE:
 * Any interface with a SAM(Single Abstract Method) is a functional interface.
 * 
 * */

public class FunctionalInterface {

	public static void main(String[] args) {
		List<Apple> inventory = createApplesList();
		inventory = process(inventory, (Apple apple) -> "red".equals(apple.getColor()));
		System.out.println("After Lambda call: " + inventory.stream().map(it -> it.getType()).collect(Collectors.joining(", ")));
		
		// Using Anonymous classes
		List<Apple> inventory2 = createApplesList();
		inventory2 = process(inventory2, new Predicate<Apple>() {
			public boolean test(Apple apple) {
				return "green".equalsIgnoreCase(apple.getColor());
			}
		});
		System.out.println("After Anonymous Class: " + inventory2.stream().map(it -> it.getType()).collect(Collectors.joining(", ")));
		
		
		/*
		 * IMPORTANT: Common functional interfaces
		 * Note how some parameters do not need the name of the variable... only the type...
		 * 
		 * IMPORTANT: Note how each of the following has their versions to support primitive types.
		 * */
		Predicate<String> predicate = 
				(String) -> true;
		Consumer<String> consumer = 
				(String) -> {};
		Function<String, Integer> function = 
				(String s) -> s.length();
		Supplier<String> supplier = 
				() -> "new string value supplied";
		UnaryOperator<String> unaryOperator = 
				(String ss) -> "string value";
		/*
		 * IMPORTANT:
		 * Note how the Unary and Binary receive and return the same type.
		 * */
		BinaryOperator<String> binaryOperator = 
				(String s1, String s2) -> "string value";
		BiPredicate<String, Integer> biPredicate = 
				(String, Integer) -> true;
		BiConsumer<String, Float> biConsumer = 
				(String, Float) -> {};
		BiFunction<String, Float, Double> biFunction = 
				(String, Float) -> 1d;
	}
	
	
	private static <T> List<T> process(List<T> list, Predicate<T> predicate) {
		List<T> result = new ArrayList<T>();
		for(T e: list) {
			if(predicate.test(e)) {
				result.add(e);
			}
		}
		return result;
	}
	
    public interface Predicate<T>{
    	boolean test(T t);
    }
    
    private static List<Apple> createApplesList() {
    	List<Apple> applesList = new ArrayList<>();
    	Apple apple1 = new Apple(0, 10F, "Green Apple", "green");
    	Apple apple2 = new Apple(0, 2F, "Red Apple", "red");
    	Apple apple3 = new Apple(0, 5F, "Blue Apple", "blue");
    	applesList.add(apple1);
    	applesList.add(apple2);
    	applesList.add(apple3);
    	return applesList;
    }
}
