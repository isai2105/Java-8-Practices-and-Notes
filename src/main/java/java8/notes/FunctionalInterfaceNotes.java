package java8.notes;

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
}
