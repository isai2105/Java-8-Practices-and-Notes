package java8.notes;

public class ForkJoinFrameworkNotes {

	/*
	 * IMPORTANT NOTE 1
	 * 
	 * Designed to recursively split a parallelizable task into
	 * smaller tasks and then combine the results of each subtask to produce an 
	 * overall result.
	 * */
	
	/*
	 * IMPORTANT NOTE 2
	 * 
	 * It is an implementation of the ExecutorService interface.
	 * It distributes those subtasks to worker threads in a thread pool called:
	 * ForkJoinPool.
	 * */
	
	/*
	 * IMPORTANT NOTE 3
	 * 
	 * Working with RecursiveTask
	 * 
	 * To submit tasks to the pool, you have to create a subclass of -RecursiveTask<R>-
	 * where R is the type of the result produce by the parallelized task.
	 * OR -RecursiveAction- if the task returns no result.
	 * */
	
	/*
	 * IMPORTANT NOTE 3
	 * 
	 * We need to implement the single abstract method:
	 * protected abstract R compute();
	 * Which resembles in the following code:
			if (task is small enough or no longer divisible) {
				compute task sequentially
			} else {
				split task in two subtasks
				call this method recursively possibly further splitting each subtask
				wait for the completion of all subtasks
				combine the results of each subtask
			}
	 */
	
	/*
	 * IMPORTANT NOTE 4
	 * 
	 * In a real-world application, it doesn’t make sense to use more than one
	 * ForkJoinPool. For this reason, what you typically should do is instantiate it only 
	 * once and keep this instance in a static field, making it a singleton, so it could be conveniently 
	 * reused by any part of your software.
	 * */
	
	/* 
	 * IMPORTANTE NOTE 5
	 * 
	 * - Best Practices:
	 * 
	 * 1. Invoking the join method on a task blocks the caller until the result produced by that task is ready...
	 * For this reason, call it AFTER the subtasks have been started.
	 * 
	 * 2. The "invoke" method of a ForkJoinPool should not be used within a RecursiveTask.
	 *    You should use compute or fork instead.
	 *    Only sequential code should use "invoke" to begin parallel computation.
	 *    
	 * 3. Calling the "fork" method on a subtask is the way to schedule it on the ForkJoinPool. 
	 *    But you should not invoke it on both: letfTask & rightTask. INstead, call the "compute" on one of them.
	 *    Doing this, allows the current Thread to be reused for the subtask.
	 *    Debugging can be tricky ... because of the different threads.
	 * 4. The duration of the subtask should be longer than the effort to split it.. to know if
	 *    using this parallel strategy is worth in that particular scenario.  
	 * 5. You must choose the criteria to decide if a given subtask should be split or not.   
	 * 
	 * */
	
	/*
	 * IMPORTANTE NOTE 6
	 * 
	 * Concept: Work stealing
	 * 
	 * Every thread that finished its task .. instead of becoming idle .. 
	 * pulls another task from the pool.
	 * */
	
	/* 
	 * IMPORTANT NOTE 7
	 * Concept: SplitIterator
	 * 
	 * There is an automatic mechanism splitting the stream for us 
	 * (this is why with streams we do not have to implement anything to use paralellism).
	 * 
	 * SplitIterator stand for "splitable iterator". These are designed to traverse the elements of a source,
	 * but also in parallel.
	 * 
		public interface Spliterator<T> {
			boolean tryAdvance(Consumer<? super T> action);
			Spliterator<T> trySplit(); -> devides the stream .. returns null if cannot be divided.
			long estimateSize(); -> to devide evenly
			int characteristics(); 
			// Characteristics: 
			 * ORDERED
			 * DISTINCT
			 * SORTED
			 * SIZED  -> it has been created from a source with known size
			 * NONNULL -> none element will be NULL
			 * INMUTABLE -> The source of the splitIterator cannot be modified
			 * CONCURRENT -> the source can be safely concurrently modified without syncrhonization
			 * SUBSIZED -> Both Spliterator and all further Spliterators resulting from its split are sized.
		*/
	
	/*
	 * Custom Spliterator (such as our example: "WordCounterSpliterator")
	 * 
	 * A custom Spliterator lets you gain control over the split policy.
	 * 
	 * How a stream is created from a Spliterator (the second -boolean argument- means parallel or not):
		Spliterator<Character> spliterator = new WordCounterSpliterator(SENTENCE);
		Stream<Character> stream = StreamSupport.stream(spliterator, true);
	 * */
	 
}
