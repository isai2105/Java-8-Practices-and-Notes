package java8.notes.defaultMethods;

public class DefaultMethodsNotes {

	/*
	 * IMPORETANT NOTE 1
	 * 
	 * In Java 8, in interfaces, you cna now declare methods with implementation code.
	 * This can happen in 2 ways:
	 * 
	 * 1. Java 8 allows static methods inside interfaces
	 * 2. Java 8 introduces 'default methods'. A default implementation for methods
	 * in an interface.
	 * 
	 * This allows to evolve interfaces non-intrusively (like the stream method that are now available to all collections).
	 * */
	
	/*
	 * IMPORTANT NOTE 2
	 * 
	 * Syntax. Note the 'default' modifier before the return type:
	 * 
		default void sort(Comparator<? super E> c){
			Collections.sort(this, c);
		}
	 * */
	
	/*
	 * IMPORTANT NOTE 3
	 * 
	 * The main users of default methods are library designers
	 */
	
	/*
	 * IMPORTANT NOTE 4
	 * 
	 * Flexible Multiple inheritance:
	 * 
	 * A class can inherit multiple default methods from several interfaces.
	 */
	
	/*
	 * IMPORTANT NOTE 5
	 * 
	 * Static methods and interfaces
	 * 
	 * A common pattern in Java is to define both an interface an a utility companion 
	 * class defining many static methods for working with instances of the interface.
	 * For example, 'Collections' is a companion class to deal with 'Collection' objects.
	 * 
	 * Now that interfaces support static methods ... those utility classes can go away and their
	 * static methods can be moved inside an interface.
	 */
	
	/*
	 * IMPORTANT NOTE 6
	 * 
	 * Types of compatibilities:
	 * 
	 * - binary: after updating an interface, the already compiled classes (.class)
	 * will run without any issues .. as long as the new method is never invoked.
	 * - source: after updating an interface, the source will compile without any issues.
	 * - behavioral: after adding a change, with the same inputs .. the same result is given.
	 * 
	 */
	
	/*
	 * IMPORTANT NOTE 7
	 * 
	 * A functional interface can have default methods .. and still be a functional interface.
	 * (functional interface is the one that defines only one abstract method).
	 * Therefore, default methods are not abstract methods.
	 */
	
	/*
	 * IMPORTANT NOTE 8
	 * 
	 * Abstract classes vs Interfaces in Java 8
	 * 
	 * 1. A class can only extend from one abstract class, but a class can implement
	 * multiple interfaces.
	 * 2. An abstract class can enforce common state through instance variables
	 * (fields). An interface can't have instance variables.
	 */
	
	/*
	 * IMPORTANT NOTE 9
	 * Usage pattern for Default methods:
	 * 
	 * 1. Optional methods:
	 * When implementing an interface, you need to five a body to all methods
	 * even if you do not need one of those .. with default methods .. the default impl
	 * can go in the interface and classes implementing it do not need to provide an empty impl.
	 * 
	 * 2. Multiple Inheritance of behavior:
	 * To reuse code, inheritance is not always the solution.
	 * If you need a couple of methods from a class that has more than 100 methods, for example ..
	 * It is better to use delegation (create a method that calls the method of the class you need).
	 * To avoid this bad pattern .. classes can be final such as the String class (String class is for security reasons).
	 */
	
	/*
	 * IMPORTANTE NOTE 10
	 * 
	 * What if a class implements more than 1 interface that has the same signature as default method (diamond problem in inheritance).
	 * 
	 * Resolution rules:
	 * 
	 * 1. Classes always win. A method declaration on a class or a superclass takes
	 * priority over default methods.
	 * 2. Otherwise, sub-interfaces win: The method in the most specific default-providing interface is selected
	 * (If B extends A, then B is more specific than A)
	 * 3. If the choice is still ambiguous, the class inheriting from multiple
	 * interfaces has to explicitly select which default method implementation to use by overriding it,
	 * and providing the desired impl ... example:

		public class C implements B, A {
			void hello(){
				B.super.hello(); ===> new Java 8 Syntax, 
									  to explicitly choose to call the method
									  from Interface B 
			}
		}
	 * 
	 * 
	 * */
	
	/* 
	 * IMPORTANTE NOTE 11
	 * 
	 * If A has a default method "hello"
	 * If D implements A, D has the default method
	 * If C implements D, C has the default method as well
	 * But if D overrides its signature, C gets the overridden version and
	 * if the overridden version is an abstract method .. then C if forced to implement it
	 * */
}
