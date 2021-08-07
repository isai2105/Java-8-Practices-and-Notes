package java8.notes;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector.Characteristics;

public class CollectorInterfaceNotes {

	/*
	 * IMPORTANT NOTE 1
	 * We can implement a custom collect in two ways:
	 * - Implementing the Collector interface
	 * - Using the overloaded collect() method that accepts the -supplier, accumulator and combiner- functions.
	 * As this overloaded method does not accept a finisher() method ...
	 * Then this option assumes always Function.identityFunction() as as result of the finisher() method.
	 * 
	 * It always behaves as an IDENTITY_FINISH and CONCURRENT
	 * but not UNORDERED collector.
	 * */
}

/*
 * This is how a Collector looks like:
 * 
 * T = generic type of items to be collected
 * A = Type of the accumulator
 * R = type of the object resulting (typically but not always: the collection)
 * 
 * Only the 4 methods:
 * - supplier()
 * - accumulator() 
 * - finisher()
 * - combiner() 
 * will be called from the collect() method.
 *
 */
abstract interface Collector<T, A, R> {
	// Creates an instance of an empty accumulator
	Supplier<A> supplier(); 
	// the accumulator instance "R" collects the "T" element
	BiConsumer<A, T> accumulator(); 
	// transforms the accumulator object into the final resulting object
	Function<A, R> finisher(); 
	// (A, A) -> A in case of parallel execution, this method is called to "join" the parts executed in parallel
	BinaryOperator<A> combiner(); 
	// Returns an immutable set of Characteristics:
	// - UNORDERED 
	// if the result of the reduction is not affected by the order in which
	// the items in the stream are traversed and accumulated
	// - CONCURRENT
	// the accumulator function can be called concurrently from multiple threads?
	// - IDENTITY_FINISH
	// INdicates that the function returned by the finisher method is the
	// Identity Function. This means that the accumulator object is directly used as the final result of the
	// reduction process
	Set<Characteristics> characteristics();
}
