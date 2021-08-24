package java8.notes;

public class StreamAPINotes {

	/*
	 * IMPORTANT NOTE 1
	 * 
	 * STreams avoids the need for us to write code that uses 'synchronized'.
	 * Java 8 can transparently run your pipeline of 
	 * Stream operations on several CPU cores on disjoint parts of the input—this is parallelism 
	 * almost for free instead of hard work using Threads.
	 * 
	 * But, the lambdas should be built in a way that do not alter / depend on instance variables, etc..
	 * */
	
	/*
	 * IMPORTANT NOTE 2
	 * 
	 * For introductory purposes, a stream 
	 * is a sequence of data items that are conceptually produced one at a time.
	 * 
	 * */
	
	/*
	 * IMPORTANT NOTE 3
	 * 
	 * Streams use internal iteration (you do not need to write code for iterating).
	 * Collections use external iteration.
	 * Streams let you manipulate collections of data in a declarative way.
	 * */
	
	/*
	 * IMPORTANT NOTE 4
	 * 
	 * Note that generating a stream from an ordered collection preserves the ordering.
	 * 
	 * */
	
	/*
	 * IMPORTANT NOTE 5
	 * 
	 * Streams can be consumed only once!
	 * For example, the following code fails when executed:
	 * 
		List<String> title = Arrays.asList("Java8", "In", "Action");
		Stream<String> s = title.stream();
		s.forEach(System.out::println);
		s.forEach(System.out::println);
	*/
	
	/*
	 * IMPORTANT NOTE 6
	 * 
	 * Intermediate Operations:
	 * 
	 * These return another stream as their return type, this, can be chained.
	 */
	
	/*
	 * IMPORTANT NOTE 7
	 * 
	 * Terminal Operations:
	 * 
	 * Produce a result from a stream pipeline (even void).
	 */
	
	/*
	 * IMPORTANT NOTE 8
	 * The 'distinct' operation works according to the implementation
	 * of 'hashCode' and 'equals'.
	 * 
	 */
	
	/*
	 * IMPORTANT NOTE 9
	 * Find Any vs Find First.
	 * Find First is more constraining in parallel processing of a Stream;
	 * The findAny method works better for parallel processing .
	 */
	
	/*
	 * IMPORTANT NOTE 10
	 * Stateful vs Stateless
	 * 
	 * Operations such as: sorted, distinct, Reduce, Sum, Max .. are Stateful.. because the operation needs to be aware
	 * of the whole history of the stream in order to accomplish its function.
	 * 
	 * Filter, Map .. are stateless .. and although reduce / sum / max require
	 * knowing the  previous / next element ... it is a small internal state
	 */
	
	/*
	 * IMPORTANT NOTE 11
	 * 
	 * Primitive stream specializations
	 * IntStream, DoubleStream & LongStream.
	 * 
	 * These streams, are more efficient (boxing / unboxing) and have specialized methods
	 * such as: sum, min, max
	 * 
	 * -- To convert from Stream to specialized Primitive Stream:
	 * mapToInt, mapToDouble & mapToLong
	 * 
	 * -- To convert from Specialized Primitive to Stream:
	 * boxed()
	 * 
	 * These specialized Primitive STreams can return:
	 * OptionalInt, OptionalDouble & OptionalLong
	 */
	
	/*
	 * IMPORTANT NOTE 12
	 * Streams vs Collections
	 * 
	 * STreams can be unbounded; there can be infinite streams.
	 */
	
	/*
	 * IMPORTANT NOTE 13
	 * 
	 * In particular, one limitation is that you can’t define a stream 
	 * recursively because a stream can be consumed only once.
	 * */
}
