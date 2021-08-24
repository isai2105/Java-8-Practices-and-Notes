package java8.notes.functional;

public class FunctionalStyleProgrammingNotes {

	/*
	 * IMPORTANT NOTE 1
	 * 
	 * Functional Programming vs Functional Style Programming
	 * 
	 * Functional programming is about programming in functions (no side effects)
	 * and Functional STyle Programming is like functional programming but with side effects.
	 * 
	 * For example, calling a function with the same parameter should always produce the same result..
	 * but for example ... calling the API to read lines for a line .. always return a different line (next line).
	 * 
	 * IN practice, you can't create a complete program in Java with future functional programming.
	 * */
	
	/*
	 * IMPORTANT NOTE 2
	 * 
	 * Side Effects:
	 * - Modifying data structure or setting a value to an object
	 * - Throwing an exception
	 * - Doing I/O operations such as writing  / reading a file
	 * 
	 * */
	
	/*
	 * IMPORTANT NOTE 3
	 * 
	 * To be called functional style, your method or function should hide the side effects
	 * .. the caller should not be aware.. there should not be any unexpected behavior..
	 *
	 * For example .. a method that debugs logs .. is not pure functional .. but functional style..
	 * if it meets all the other characteristics of functional programming.
	 * */
	
	/*
	 * IMPORTANT NOTE 4
	 * 
	 * Referential transparency (no visible side-effects):
	 * 
	 * Functions always produce the same result, given the same arguments (input).
	 * No matter where or when it is invoked.
	 * 
	 * Example:
	 * - Random.nextInt is NOT functional
	 * - String.replace IS functional
	 * 
	 * Java complication:
	 * Imagine a method that return a list .. imagine it returns the same elements given the same input..
	 * but the list have different references (those are different instances). Here, regarding this
	 * method as "transparent" is subjective (according to your needs or purpose of that result).
	 * */
	
	/*
	 * IMPORTANT NOTE 5
	 * 
	 * Iteration vs Recursion
	 * 
	 * Recursion uses a stack for each recursive call, thus it is more expensive.
	 * Other languages (not java) contains something called tail-optimization in which
	 * the programmer sets the last things of the function to be the recursive call, for example:
	 * 
	 *  - Does not contain tail optimization (n needs to be stored to know the result of its multiplication)
		static long factorialRecursive(long n) {
			return n == 1 ? 1 : n * factorialRecursive(n-1);
		}
		- Does contain tail optimization
		static long factorialHelper(long acc, long n) {
			return n == 1 ? acc : factorialHelper(acc * n, n-1);
		}
	 * 
	 * The compiler can decide to reuse a single stack frame.
	 * */
}
