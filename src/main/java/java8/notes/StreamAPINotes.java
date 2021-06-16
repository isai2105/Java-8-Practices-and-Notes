package java8.notes;

public class StreamAPINotes {

	/*
	 * IMPORTANTE NOTE 1
	 * 
	 * STreams avoids the need for us to write code that uses 'synchronized'.
	 * Java 8 can transparently run your pipeline of 
	 * Stream operations on several CPU cores on disjoint parts of the input—this is parallelism 
	 * almost for free instead of hard work using Threads.
	 * */
	
	/*
	 * IMPORTANTE NOTE 2
	 * 
	 * For introductory purposes, a stream 
	 * is a sequence of data items that are conceptually produced one at a time.
	 * 
	 * */
	
	/*
	 * IMPORTANTE NOTE 3
	 * 
	 * Streams use internal iteration (you do not need to write code for iterating).
	 * Collections use external iteration.
	 * 
	 * */
}
