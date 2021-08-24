package com.examples.NullOptional;

import java.util.Optional;
import java.util.Properties;

import com.bean.Apple;
import com.bean.nullOptional.Car;
import com.bean.nullOptional.Insurance;
import com.bean.nullOptional.OptionalUtility;
import com.bean.nullOptional.Person;

public class NullOptionalExamples {

	public static void main (String[] args) {
		// Creating an empty optional
		Optional<Apple> optApple = Optional.empty();
		// Creating an optional from a value
		// if the argument were null, a NullPOinterException would be 
		// thrown when trying to access the properties of the car
		Optional<Apple> optApple2 = Optional.of(new Apple());
		// To handle a possible null value
		Optional<Apple> optApple3 = Optional.ofNullable(new Apple());
		
		// Take note of the map method (similar to the "map" method of Streams)
		Optional<Insurance> optInsurance = Optional.ofNullable(new Insurance());
		Optional<String> name = optInsurance.map(Insurance::getName);
	}
	
	/*
	 * This is unsafe code
	public String getCarInsuranceName(Person person) {
		return person.getCar().getInsurance().getName();
	}
	Even if we add ifs to check the nullity of car & insurance .. with optionals
	the following if the  way:
	Should be replaced with something like this:
	*/
	public String getCarInsuranceName(Optional<Person> person) {
		
		// If we wanted to filter the person ny minAge, we could:
		// person.filter(p -> p.getAge() >= minAge)
		return person.flatMap(Person::getCar)
		.flatMap(Car::getInsurance) // flatMap because it mixed the
		// Optional<Optional<Insurance>> into a single Optional
		.map(Insurance::getName)
		.orElse("Unknown");
	}
	
	// This method can be rewritten to use Optional
	public int readDuration(Properties props, String name) {
		String value = props.getProperty(name);
		if (value != null) {
			try {
				int i = Integer.parseInt(value);
				if (i > 0) {
					return i;
				}
			} catch (NumberFormatException nfe) {
			}
		}
		return 0;
	}
	// Advanced use of the optional
	// can be converted to this:
	public int readDurationWithOptional(Properties props, String name) {
		return Optional.ofNullable(props.getProperty(name))
				.flatMap(OptionalUtility::stringToInt)
				.filter(i -> i > 0)
				.orElse(0);
	}
}
