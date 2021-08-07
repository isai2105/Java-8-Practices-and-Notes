package java8.notes;

import java.util.stream.LongStream;

public class ParallelStreamsNotes {

	/*
	 * IMPORTANT NOTE 1
	 * 
	 * You can transform a parallel stream into a sequential one just by
	 * calling the method "sequential()" on the stream.
	 * 
	 * For example, you could:
	 * 
		 stream.parallel()
		.filter(...)
		.sequential()
		.map(...)
		.parallel()
		.reduce();
	 * 
	 * A stream is only either sequential or parallel .. that is why in 
	 * the previous example, the stream will run in parallel (last call wins)
	 * */
	
	/*
	 * IMPORTANT NOTE 2
	 * 
	 * A shared mutable state does not play well with parallel streams.
	 * 
	 * */
	
	// Note how this gives wrong results because of the race conditions
	// to access the "total" state among Threads. 
	private static long sideEffectParallelSum(long n) {
		Accumulator accumulator = new Accumulator();
		LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
		return accumulator.total;
	}
	
	private static class Accumulator {
		public long total = 0;
		public void add(long value) { total += value; }
	}
	
	/*
	 * IMPORTANT NOTE 3
	 * 
	 * Using parallel streams effectively
	 * 
	 * 1. If in doubt, measure.
	 * 2. Watch out for boxing.
	 * 3. Some operations perform worst on parallel processing, such as:
	 * 	  limit & findFirst 
	 *    findAny will perform better.
	 * 4. You can turn an ordered stream (for example, the one created from an ordered collection)
	 *    into an unordered stream, but using the stream method: unordered.
	 * 5. For small amounts of data, parallel stream is not the winner.
	 * 6. Take into account how well the data structure underlying the stream
	 *    decomposes. For example, an ArrayList can be split much more 
	 *    efficiently than a LinkedList.
	 * 7. Consider whether a terminal operation has a cheap or expensive 
	 * merge step (for example, the combiner method in a Collector).
	 * 8. Stream sources and decomposability:
	 *    - ArrayList (Excellent)
	 *    - LinkedList (Poor)
	 *    - IntStream.range (Excellent)
	 *    - Stream.iterate (Poor)
	 *    - HashSet (Good)
	 *    - TreeSet (Good)
	 * 9. The cost of the combiner method. If it is expensive .. then ..
	 * when combining the parallel streams .. it will  be expensive.
	 * */
}
