package java8.notes;

public class GeneralNotes {

	/*
	 * IMPORTANTE NOTE 1
	 * 
	 * Lambas, and method references, provide the ability to pass code or method as arguments to be executed in the middle of doing something else.
	 * For example: in STreams
	 * */
	
	
	/*
	 * IMPORTANTE NOTE 2
	 * 
	 * Streams, not all their data in in memory.
	 * You can process streams that are to big to fit in memory.
	 * Java8 optimizes the operation inside Streams, in a way Collections can't do.
	 * Java can parallelize streams collections for you.
	 * */
	
	
	/*
	 * IMPORTANTE NOTE 3
	 * 
	 * Multicore and processing large data sets, changes the architecture needs.
	 * These new architectures favor functional style
	 * */
	
	
	/*
	 * IMPORTANTE NOTE 3
	 * 
	 * Behavior parameterization.
	 * For example, if those methods differ only in a few lines of code .. these lines can be passed as a parameter.
	 * This could be accomplished previously with Anonymous classes, but this technique is shorter, cleaner, etc ...
	 */
	
	/*
	 * IMPORTANTE NOTE 4
	 * 
	 * in Java 8 when you write File::isHidden you create a method reference.
	 * */
	
	/*
	 * IMPORTANTE NOTE 5
	 * 
	 * Every type in Java is either a REFERENCE type or a Primitive Type
	 * Generics can be bound only to Reference types.
	 * */
}
