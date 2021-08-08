package java8.notes.refactoring;

public class RefactoringNotes {

	/*
	 * IMPORTANT NOTE 1
	 * 
	 * Java 8 features help improve readability by:
	 * - reducing verbosity of the code
	 * - Improve the intent by using method references & Streams API
	 * */
	
	/*
	 * IMPORTANT NOTE 2
	 * 
	 * Three simple refactorings:
	 * - Anonymous classes to lambda expressions
	 * - Lambda Expressions to Method References
	 * - Refactoring Imperative-style data processing to streams 
	 * */
	
	/*
	 * IMPORTANT NOTE 3
	 * 
	 * From anonymous classes to lambda expressions
	 * 
	 * For example:
	  
	 	* Anonymous class:
			Runnable r1 = new Runnable(){
				public void run(){
					System.out.println("Hello");
				}
			};
		
		* Lambda Expressions:
			Runnable r2 = () -> System.out.println("Hello");
		
	 *
	 * Differences between anonymous classes and Lambda expressions:	
	 * 
	 * - "this" & "super" mean different things on both:
	 * 		Inside anonymous class, this refers to the class itself .. 
	 * 		whereas in a lambda expression it refers to the enclosing class.
	 * - Anonymous classes are allowed to "shadow" variables from the enclosing class.
	 *   Lambda expressions can't and it will cause a compile error.
	 	
	 	* Compilation error:
			int a = 10;
			Runnable r1 = () -> {
				int a = 2;
				System.out.println(a);
			};
		* No Compilation error (anonymous class):
			Runnable r2 = new Runnable() {
				public void run(){
					int a = 2;
					System.out.println(a);
				}
			};
			
	  * - Sometimes when converting from anonymous class to lambda, 
	  * causes code ambiguity (in the context of overloading).
	  * For example, for the following code:
		interface Task{
			public void execute();
		}
		public static void doSomething(Runnable r){ r.run(); }
		public static void doSomething(Task a){ r.execute(); }
		
		You can use anonymous classes without a problem:
			doSomething(new Task() {
				public void execute() {
					System.out.println("Danger danger!!");
				}
			});
		But when using a lambda expression, there is an ambiguity error:
			doSomething(() -> System.out.println("Danger danger!!"));
		The ambiguity needs to be solved by adding a cast:
			doSomething((Task)() -> System.out.println("Danger danger!!"));
	  * 
	 * */
	 
	/*
	 * IMPORTANTE NOTE 4
	 * 
	 * From lambda expressions to method references
	 * 
	 * A method name states more clearly the intent of your code.
	 * For example:
	 * 
	 	Instead of:
			inventory.sort(
				(Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));
		Use: 
			inventory.sort(comparing(Apple::getWeight));
		
		** NOTE:
			Try using alternative built-in collectors, which state more clearly what the problem
			statement is. Here we use the collector summingInt (names go a long way in documenting
			your code)
		Instead of:
			int totalCalories =
				menu.stream().map(Dish::getCalories)
					.reduce(0, (c1, c2) -> c1 + c2);
		Use: 
			int totalCalories = menu.stream().collect(summingInt(Dish::getCalories));
	 * */
	
	/*
	 * IMPORTANTE NOTE 5
	 * 
	 * From imperative data processing to Streams.
	 	Ideally, you should try to convert all code that processes a collection with typical data
		processing patterns with an iterator to use the Streams API instead.
		
		For example, instead of having the following code, with the filtering and the collecting
		in the same place:
			List<String> dishNames = new ArrayList<>();
			for(Dish dish: menu){
				if(dish.getCalories() > 300){
					dishNames.add(dish.getName());
				}
			}
		Instead, see (notice how streams can be parallelized to be faster):
			menu.parallelStream()
			.filter(d -> d.getCalories() > 300)
			.map(Dish::getName)
			.collect(toList());
	 * */
}
