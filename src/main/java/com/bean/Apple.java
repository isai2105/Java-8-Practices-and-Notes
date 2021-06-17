package com.bean;

public class Apple {

	int age;
	Float weight = null;
	String type;
	String color;
	
	public Apple() {
		
	}
	
	public Apple(String type) {
		
	}
	
	public Apple (String type, String color) {
		
	}
	
	public Apple(int age, Float weight, String type, String color) {
		super();
		this.age = age;
		this.weight = weight;
		this.type = type;
		this.color = color;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public Float getWeight() {
		return weight;
	}
	
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public static boolean isGreenApple(Apple apple) {
		return apple.getColor() != null && "green".equalsIgnoreCase(apple.getColor());
	}
	
	public static boolean isHeavyApple(Apple apple) {
		return apple.getWeight() != null && apple.getWeight() > 150;
	}
}
