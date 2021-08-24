package com.bean.future;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Shop {
	
	private String name = null;
	Random random = new Random();
	
	public Shop(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public static void delay() {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public double getPrice(String product) {
		return calculatePrice(product);
	}
	
	public String getPriceAsString(String product) {
		double price = calculatePrice(product);
		Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
		return String.format("%s:%.2f:%s", name, price, code);
	}
	
	private double calculatePrice(String product) {
		delay();
		return random.nextDouble() * product.charAt(0) + product.charAt(1);
	}
	
	public Future<Double> getPriceAsync(String product) {
		CompletableFuture<Double> futurePrice = new CompletableFuture<>();
		new Thread( () -> {
			try {
				double price = calculatePrice(product);
				futurePrice.complete(price);
			} catch(Exception ex) {
				// NOTE: Notice how to propagate an exception inside a CompletableFuture
				futurePrice.completeExceptionally(ex);
			}
		}).start();
		return futurePrice;
	}
	
	/* IMPORTANT!!
	 * This version (using the factory supplyAsync, does the same as the method above.)
	 * Even the exception handling is happening behind the scenes.
	 * 
	 * This method received a supplier function.
	 * But the overloaded version allows to specify a custom Executor service when needed.
	 * */	
	public Future<Double> getPriceAsyncSupplied(String product) {
		return CompletableFuture.supplyAsync(() -> calculatePrice(product));
	}
}
