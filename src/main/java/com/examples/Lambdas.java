package com.examples;

import com.bean.Apple;

public class Lambdas {

	public static void main(String[] args) {
		/*
		 * LOOK AT THE WAY OF INSTANTIATION HERE
		 * */
		Predicate<Apple> predicate = (Apple apple) -> "red".equals(apple.getColor());
		
		/*
		 * Predicate that return an integer
		 * */
		ReturnIntPredicate<String> p1 = (String s) -> s.length();
		
		/*
		 * Predicate that return a boolean
		 * */
		ReturnBooleanPredicate<String> p2 = (String a) -> a.length() > 150;
		
		/*
		 * Predicate that returns void
		 * */
		ReturnVoidPredicate<String> p3 = (String s) -> {
			System.out.println("Result:");
			System.out.println(s);
		};
	}
	
	public interface Predicate<T>{
		boolean test(T t);
	}
	
	public interface ReturnBooleanPredicate<T>{
		boolean test(T t);
	}
	
	public interface ReturnIntPredicate<T>{
		int test(T t);
	}
	
	public interface ReturnVoidPredicate<T>{
		void test(T t);
	}
}
