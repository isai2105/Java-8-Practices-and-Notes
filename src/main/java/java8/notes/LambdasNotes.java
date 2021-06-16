package java8.notes;

import java.util.concurrent.Callable;

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
}
