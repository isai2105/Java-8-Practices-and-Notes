package com.examples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.bean.Apple;

public class BehaviorParameterization {

    public static void main(String[] args) {
        List<Apple> inventory1 = createApplesList();
        sortWithOldComparator(inventory1);
        System.out.println("Old sort: " + inventory1.stream().map(it -> it.getType()).collect(Collectors.joining(", ")));
        
        List<Apple> inventory2 = createApplesList();
        sortWithJava8(inventory2);
        System.out.println("New Java 8 sort: " + inventory2.stream().map(it -> it.getType()).collect(Collectors.joining(", ")));
    }
	
    /*
     * Old way of sorting, with a comparator.
     * This would need to be created if we wanted to sort by any other attributes of the apple.
     * */
    private static void sortWithOldComparator(List<Apple> inventory) {
    	Collections.sort(inventory, new Comparator<Apple>() {
    		public int compare(Apple a1, Apple a2){
    			return a1.getWeight().compareTo(a2.getWeight());
    		}
    	});
    }
    
    /*
     * New way of sorting.
     * The behavior of the "comparing" method changes just by sending the custom behavior via parameter.
     * */
    private static void sortWithJava8(List<Apple> inventory) {
    	inventory.sort(Comparator.comparing(Apple::getWeight));
    }
    
    private static List<Apple> createApplesList() {
    	List<Apple> applesList = new ArrayList<>();
    	Apple apple1 = new Apple(0, 10F, "type1", "red");
    	Apple apple2 = new Apple(0, 2F, "type2", "red");
    	Apple apple3 = new Apple(0, 5F, "type3", "red");
    	applesList.add(apple1);
    	applesList.add(apple2);
    	applesList.add(apple3);
    	return applesList;
    }
    
}
