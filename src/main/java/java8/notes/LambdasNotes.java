package java8.notes;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class LambdasNotes {

	/*
	 * IMPORTANTE NOTE 1
	 * 
	 * How to know when your lambda needs to be a method, and use it with a method reference:
	 * 
	 * 1. The lambda function exceeds a few lines of code and its behavior is not clear at first sight.
	 * 2. The lambda function needs to be used in several places.
	 * */
	
	/*
	 * IMPORTANTE NOTE 2
	 * 
	 * Check why the following is Valid syntax
	 */
	public Callable<String> fetch() {
		return () -> "Tricky example ;-)";
	}
	
	/*
	 * IMPORTANT NOTE 3
	 * 
	 * Special void-compatibility rule
	 * If a lambda has a statement expression as its body, it is
	 * compatible with a function description that returns void.
	 * 
	 * Fox example: Analyze the following valid compatibilities.
	 * */
	
	// Predicate has a boolean return
	Predicate<String> p = s -> new ArrayList<>().add(s);
	// Consumer has a void return
	Consumer<String> b = s -> new ArrayList<>().add(s);
	
	/*
	 * IMPORTANT NOTE 4
	 * Type inference.
	 * Note how the type of the parameter is inferred.
	 */
	Predicate<String> predicate1 = s -> s.length() > 5;
	
	/*
	 * IMPORTANT NOTE 5
	 * Using local variables.
	 * These variables need to be declared as final or be effectively final.
	 */
	int portNumber = 1337;
	Runnable r = () -> System.out.println(portNumber);
	// if we assign the portNumber variable another value ... then ...
	// the compiler is going to complain
}
