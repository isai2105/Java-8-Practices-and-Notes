package com.examples;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;

import com.bean.Apple;

public class MethodReference {

	
	public static void main(String[] args) {
		/*
		 * IMPORTANT NOTE 1
		 * */
		// 1. Method reference to static methods
		Function<String, Integer> f1 = Integer::parseInt;
		// 2. Method reference to an instance method of an instance object
		Supplier<Integer> supplier1 = "string value"::length;
		// 3. Method reference to an instance method of an arbitrary type
		List<String> str = Arrays.asList("a","b","A","B");
		str.sort((s1, s2) -> s1.compareToIgnoreCase(s2));
		// Note: Note the following line and compare it with the one above
		str.sort(String::compareToIgnoreCase);
		BiPredicate<List<String>, String> contains = List::contains;
		
		/*
		 * IMPORTANTE NOTE
		 * Constructor reference
		 * */
		Supplier<Apple> c1 = Apple::new;
		Apple a1 = c1.get();
		
		// Method references on constructors that contains parameter.
		Function<String, Apple> c2 = Apple::new;
		Apple a2 = c2.apply("apple type");
		
		BiFunction<String, String, Apple> c3 = Apple::new;
		Apple a3 = c3.apply("apple type", "green");
		
		Map<String, CustomFunction<Apple>> factory = new HashMap<>();
		factory.put("appleType1", Apple::new);
		factory.put("appleType2", Apple::new);
		// Then we can create apples on the fly with different combinations of
		// attribute values, such as:
		Apple apple1 = factory.get("appleType1").produce(1, 1f, "type1", "green");
		Apple apple2 = factory.get("appleType1").produce(2, 2f, "type2", "red");
		
	}
	
	public interface CustomFunction<T> {
		public T produce(int age, Float weight, String type, String color);
	}
}
