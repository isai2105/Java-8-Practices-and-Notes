package com.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.bean.Apple;

public class PredicateExamples {
	public static void main(String[] args) {
		
        List<Apple> inventory1 = createApplesList();
        System.out.println("Old way: " + filterGreenApples(inventory1).stream().map(it -> it.getType()).collect(Collectors.joining(", ")));
        
        List<Apple> inventory2 = createApplesList();
        System.out.println("New Java 8 - Predicate way: " + filterApples(inventory2, Apple::isGreenApple).stream().map(it -> it.getType()).collect(Collectors.joining(", ")));
        
        /*
         * LAMBDAS
         * */
        List<Apple> inventory3 = createApplesList();
        System.out.println("LAMBDAS way: " + filterApples(inventory3, (Apple a) -> "green".equals(a.getColor())).stream().map(it -> it.getType()).collect(Collectors.joining(", ")));
	}
	
	/*
	 * Old way:
	 * Filtering apples by "green" color.
	 * If we wanted to filter by any other color ... we could pass the color as a parameter...
	 * But if filtering by weight, or any complex ranges of any value ... we would need to copy paste the method and
	 * modify only the "if" condition.
	 * */
	public static List<Apple> filterGreenApples(List<Apple> inventory) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if ("green".equals(apple.getColor())) {
				result.add(apple);
			}
		}
		return result;
	}
	
	/*
	 * New way: (passing methods as values)
	 * Example: filterApples(inventory, Apple::isGreenApple);
	 * */
	static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple: inventory){
			if (p.test(apple)) {
				result.add(apple);
			}
		}
		return result;	
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
