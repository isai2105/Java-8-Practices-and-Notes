package java8.notes;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import com.bean.Apple;

public class MethodReferenceNotes {

	/*
	 * IMPORTANT NOTE 1
	 * 
	 1. Method reference to static methods
	 2. Method reference to an instance method of an instance object
	 3. Method reference to an instance method of an arbitrary type
	 * */
	
	/*
	 * IMPORTANTE NOTE 2
	 * 
	 * There are constructor references as well.
	 * */
	Supplier<Apple> c1 = Apple::new;
	Apple a1 = c1.get();
	
	/*
	 * IMPORTANTE NOTE 3
	 * 
	 * The constructor that have parameter can match functional interfaces.
	 * */
	Function<String, Apple> c2 = Apple::new;
	Apple a2 = c2.apply("apple type");
	
	BiFunction<String, String, Apple> c3 = Apple::new;
	Apple a3 = c3.apply("apple type", "green");
}
