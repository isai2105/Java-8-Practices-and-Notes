package java8.notes.optional;

public class NullOptionalNotes {

	/*
	 * IMPORTANT NOTE 1
	 * 
	 * - adding if blocks to check for nulls ... affecta readability of the code.
	 * - it breaks Java philosophy (to hide references from devs; null is a referece)
	 * - null can be assigned to any type .. and when in error .. there is no idea what caused it
	 * 
	 * */
	
	/*
	 * IMPORTANT NOTE 2
	 * 
	 * Optional is NOT Serializable
	 * This means that, it cannt be used as a field in a domain model
	 * for code that need a Serializable model to work (such as HIbernate, Spring JPA, etc)
	 * 
	 * But they recommend to add getters that return optional .. getCarAsOptional() for example.
	 * */
	
	/*
	 * IMPORTANT NOTE 3
	 * 
	 * When invoking safe methods such as: flatMap, map, filter, etc..
	 * if the optional contains no value .. it does not execute the predicates ..
	 * */
	
	/*
	 * IMPORTANT NOTE 4
	 * 
	 * The optionals can be combined.
	 * For example, the following code:
		public Optional<Insurance> nullSafeFindCheapestInsurance(
				Optional<Person> person, Optional<Car> car) {
			if (person.isPresent() && car.isPresent()) {
				return Optional.of(findCheapestInsurance(person.get(), car.get()));
			} else {
				return Optional.empty();
			}
		}
	 *
	 *	Can be rewritten as:
		public Optional<Insurance> nullSafeFindCheapestInsurance(
				Optional<Person> person, Optional<Car> car) {
			return person.flatMap(p -> car.map(c -> findCheapestInsurance(p, c)));
		}
	 * */
	
	/*
	 * IMPORTANT NOTE 5
	 * 
	 * Similarities with the Stream: filter
	 * Code like this:
		Insurance insurance = ...;
		if(insurance != null && "CambridgeInsurance".equals(insurance.getName())){
			System.out.println("ok");
		}
	 *
	 * Can be rewritten as:
		Optional<Insurance> optInsurance = ...;
		optInsurance
			.filter(insurance ->
				"CambridgeInsurance".equals(insurance.getName()))
			.ifPresent(x -> System.out.println("ok"));
	 *
	 * */
	
	/*
	 * IMPORTANT NOTE 6
	 * 
	 * Try not to check for null.
	 * For example, when pulling a value from a map (The native API returns null when not found)...
	 * You can use:
		Optional<Object> value = Optional.ofNullable(map.get("key"));
	 * */
	
	/*
	 * IMPORTANT NOTE 7
	 * 
	 * Handling existing API exceptions to Optional.
	 * For example (notice how the ugly try & catch are always encapsulated here in the utility method):
		public static Optional<Integer> stringToInt(String s) {
			try {
				return Optional.of(Integer.parseInt(s));
			} catch (NumberFormatException e) {
				return Optional.empty();
			}
		}
	 *
	 * It is recommended to put all these utility methods inside a OptionalUtility class.
	 * */
	
	/*
	 * IMPORTANT NOTE 8
	 * 
	 * Do not user the primitive version of the Optional
	 * OptionalInt, OptionalLong and OptionalDouble.
	 * These are missing the most important methods such asL flatMap & filter.
	 * Besides, those cannot be passed as a reference to another flatMap for example.
	 */
}
