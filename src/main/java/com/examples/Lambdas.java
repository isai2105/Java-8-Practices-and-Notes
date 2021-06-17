package com.examples;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import com.bean.Apple;

public class Lambdas {

	public static void main(String[] args) {
		/*
		 * LOOK AT THE WAY OF INSTANTIATION HERE
		 * */
		CustomPredicate<Apple> customPredicate = (Apple apple) -> "red".equals(apple.getColor());
		
		/*
		 * Predicate that return an integer
		 * */
		ReturnIntPredicate<String> p1 = (String s) -> s.length();
		
		/*
		 * Predicate that return a boolean
		 * */
		ReturnBooleanPredicate<String> p2 = (String a) -> a.length() > 150;
		
		/*
		 * Predicate that returns void (Consumer)
		 * */
		CustomConsumer<String> p3 = (String s) -> {
			System.out.println("Result:");
			System.out.println(s);
		};
		
		CustomFunction<String, Double> customFunction = 
				(String s) -> Double.valueOf(s);
		
		/*
		 * IMPORTANT NOTE.
		 * 
		 * Predicate ... takes an object T and returns a boolean
		 * Consumer ... takes an object T and returns void
		 * Function ... takes an object T and return a value R
		 * */
		Predicate<String> predicate = (String s) -> s.length() > 0;
		Consumer<Integer> consumer = (Integer i) -> System.out.println(i);
		Function<String, Integer> function = (String s) -> Integer.valueOf(s);
	}
	
	public interface CustomPredicate<T>{
		boolean test(T t);
	}
	
	public interface ReturnBooleanPredicate<T>{
		boolean test(T t);
	}
	
	public interface ReturnIntPredicate<T>{
		int test(T t);
	}
	
	public interface CustomConsumer<T>{
		void test(T t);
	}
	
	public interface CustomFunction<T, R> {
		R apply(T t);
	}
}
