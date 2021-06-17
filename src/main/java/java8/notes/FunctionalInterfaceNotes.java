package java8.notes;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.function.Function;
import java.util.function.IntConsumer;

public class FunctionalInterfaceNotes {

	/*
	 * IMPORTANTE NOTE 1
	 * 
	 * Any interface with a SAM(Single Abstract Method) is a functional interface.
	 * 
	 * */
	
	/*
	 * IMPORTANT NOTE 2
	 * 
	 *  public interface Adder{
			int add(int a, int b);
		}
		public interface SmartAdder extends Adder{
			int add(double a, double b);
		}
		public interface Nothing{
		}
		
		Only "Adder" is a functional interface.
		"SmartAdded" could be, but inherits the other "add" method from the interface it is extending.
	 * */
	
	/*
	 * IMPORTANT NOTE 3
	 * 
	 * Although it is not mandatory, the functional interface should be annotated with
	 * @FunctionalInterface 
	 * */
	
	/*
	 * IMPORTANT NOTE 4
	 * 
	 * Each OOTB FunctionalINterface have their version to support primitive values
	 * To avoid the memory cost of boxing / unboxing.
	 * For example:
	 * Consumer<T> has the versions: IntConsumer, LongConsumer, DoubleConsumer.
	 * 
	 * */
	IntConsumer intConsumer = (int i) -> System.out.println(i);
	
	/*
	 * IMPORTANT NOTE 5
	 * 
	 * When using an OOTB Functional Interface there is no possibility to 
	 * specify throw the exception in the signature of the method .. so in that
	 * scenario .. we need to catch it and then throw a RuntimeException or handle it.
	 * */
	Function<BufferedReader, String> f =
		(BufferedReader b) -> {
			try {
				return b.readLine();
			} catch (IOException e) {
				throw new RuntimeException();
			}
		};
}
