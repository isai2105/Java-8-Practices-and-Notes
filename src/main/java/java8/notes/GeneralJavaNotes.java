package java8.notes;

import java.util.stream.Collector;
import java.util.stream.Collectors;

public class GeneralJavaNotes {
	/*
	 * IMPORTANTE NOTE 1
	 * 
	 * The Callable interface returns a value, whereas the Runnable interface does not.
	 * If we wanted to get a value from somethign being executed in a Thread .. then the Callable is the option s.. because
	 * with the Runnable we could only print it.
	 * */
	
	/*
	 * IMPORTANTE NOTE 2
	 * 
	 * Notice the "?" wildcard.
	 * TODO Research about this.
	 */
	public static <T> Collector<T, ?, Long> counting() {
		return Collectors.reducing(0L, e -> 1L, Long::sum);
	}
}
