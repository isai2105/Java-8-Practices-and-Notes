package java8.notes.functional;

import java.util.function.Function;

public class FunctionalProgrammingTechniquesNotes {

	
	/*
	 * IMPORTANT NOTE 1 
	 * 
	 * First Class functions:
	 * 
	 * Functions that can be used as another values.
	 * Can be assigned to variables, passed as arguments, 
	 * stored in data structures, etc...
	 * */
	
	/*
	 * IMPORTANT NOTE 2
	 * 
	 * Higher-order functions
	 * 
	 * Functions that can (besides the first class characteristics):
	 * - take one or more functions as parameter
	 * - return a function as result
	 * 
	 * Just as functions accepted on stream operations... these should be
	 * side-effects free.. and you should document that if you develop a higher-order
	 * function. We saw the horrible side effects of altering external things
	 * on parallel streams.
	 * 
	 * Typical higher-order functions in Java include:
	 * - comparing
	 * - andThen
	 * - compose.
	 * */
	
	/*
	 * IMPORTANT NOTE 3
	 * 
	 * Currying
	 * 
	 * Currying is a technique that lets you modularize functions and reuse code.
	 * 
	 * Technique where a function "f" of N arguments is seen instead as a
	 * function "g" of (N-1) arguments that return a function also of one argument.
	 * The value returned by the "g" function is the same as the value returned
	 * by the "f" function.
	 * Meaning: f(x,y) = (g(x))(y)
	 * The order of arguments is not important ... it can be done as needed
	 * 
	 * For example:
	 * 
	 *  The idea is to reuse the following conversion logic that applies
	 *  to many types of conversion.
	 *  
		static DoubleUnaryOperator curriedConverter(double f, double b){
			return (double x) -> x * f + b;
		}
		
		// we partially apply that:
		DoubleUnaryOperator convertCtoF = curriedConverter(9.0/5, 32);
		DoubleUnaryOperator convertUSDtoGBP = curriedConverter(0.6, 0);
		DoubleUnaryOperator convertKmtoMi = curriedConverter(0.6214, 0);
		
		double gbp = convertUSDtoGBP.applyAsDouble(1000);
	 * 
	 */
	
	/*
	 * IMPORTANT NOTE 4
	 * 
	 * Destructive updated vs Functional Updates
	 * 
	 * Functional Updated do not update the data structure.
	 * It creates a new one containing the updates .. but does not 
	 * update the existing ones, so it is safe.
	 * 
	 */
	
	/*
	 * IMPORTANT NOTE 5
	 * 
	 * Lazy Data Structures
	 * 
	 * Allows you to get values on demand rather than havong those on memory.
	 * For example, adding a Supplier to a list .. and only getting the value when needed.
	 * But most of the time this works for small data structures .. because
	 * having all those many instances and calling them may affect performance.
	 */
	
	/*
	 * IMPORTANT NOTE 6
	 * Persistent Data structures vs partially persistent data structures
	 * 
	 * A persistent data structure is a data structure that always preserves 
	 * the previous version of itself when it is modified. 
	 * Such data structures are effectively immutable, as their operations 
	 * do not (visibly) update the structure in-place, but instead always 
	 * yield a new updated structure.
	 * 
	 * A data structure is partially persistent if all versions can be accessed 
	 * but only the newest version can be modified. 
	 */
	
	/*
	 * IMPORTANT NOTE 7
	 * 
	 * Pattern Matching
	 * Not available in Java
	 * 
	 * */
	
	/*
	 * IMPORTANT NOTE 8
	 * 
	 * Combinators
	 * 
	 * A High-order function that allows two or more functions and returns a function
	 * somehow combining these functions.
	 * 
	 * Example:
		static <A,B,C> Function<A,C> compose(Function<B,C> g, Function<A,B> f) {
			return x -> g.apply(f.apply(x));
		}
		
		It takes functions f and g as arguments and returns a function whose effect is to do f
		first and then g.
		
		The following is an example of High-order function (compose)
		used with recursion:
	 * 
	 */
	
	static <A> Function<A,A> repeat(int n, Function<A,A> f) {
		// x -> x is the "do nothing" identity function
		return n==0 ? x -> x : compose(f, repeat(n-1, f));
	}
	
	static <A,B,C> Function<A,C> compose(Function<B,C> g, Function<A,B> f) {
		return x -> g.apply(f.apply(x));
	}
	
	public static void main (String args[]) {
		// n==1 (Integer x) -> 2*x)
		// n==2 (Integer x) -> 2*[(Integer x) -> 2*x)
		// n==3 (Integer x) -> 2*[(Integer x) -> 2*[(Integer x) -> 2*x)])])
		// 80
		System.out.println(repeat(3, (Integer x) -> 2*x).apply(10));
	}
}
