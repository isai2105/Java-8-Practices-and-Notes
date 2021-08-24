package com.examples.future;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.bean.future.Discount;
import com.bean.future.Quote;
import com.bean.future.Shop;

public class CompletableFutureExamples {

	private static List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
			new Shop("LetsSaveBig"),
			new Shop("MyFavoriteShop"),
			new Shop("BuyItAll"),
			new Shop("akuraStore"),
			new Shop("Diphysa1"),
			new Shop("Diphysa2"),
			new Shop("Diphysa3"),
			new Shop("Diphysa4"),
			new Shop("Diphysa5"),
			new Shop("Diphysa6"),
			new Shop("Diphysa7"),
			new Shop("Diphysa8"));
	
	// NOTE: read the notes about how to calculate when Sizing thread pools
	private static final Executor CUSTOM_EXECUTOR =
			Executors.newFixedThreadPool(Math.min(shops.size(), 100),
					new ThreadFactory() {
						public Thread newThread(Runnable r) {
							Thread t = new Thread(r);
							t.setDaemon(true);
							return t;
						}
					});
	
	public static void main(String[] args) {
		/* 1. Single threaded example using Completable Future */
		System.out.println("******* callAsyncCalculatePriceMethod() ******");
		callAsyncSimpleCalculatePriceMethod();
		
		/* 2. Example using sequential computation */
		System.out.println("******* callSyncCalculatePriceMethod() ******");
		callSyncCalculatePriceMethod();
		
		/* 3. Example using parallel streams computation (same example above, but parallel Streams) */
		System.out.println("******* callSyncCalculatePriceMethodParalleStream() ******");
		callSyncCalculatePriceMethodParalleStream();
		
		/* 4. Example using CompletableFuture (supports as many as shops exists) */
		// notice how the parallel streams version seems to be faster
		// but this depends on the core of the computer.. if there is a bigger number
		// of threads to process than the core .. then you will see that parallel streams is slower
		System.out.println("******* callAsyncComplexCompletableFuture() ******");
		callAsyncCompletableFuture();
		System.out.println("- Number of threads in the computer: " + Runtime.getRuntime()
		.availableProcessors());
		/* 5. Example using CompletableFuture with custom Executor */
		System.out.println("******* callAsyncCompletableFutureWithCustomExecutor() ******");
		callAsyncCompletableFutureWithCustomExecutor();
		/* 6. Example with discounts - SEQUENTIAL */
		System.out.println("******* callFindPricesWithDiscountsSequential() ******");
		callFindPricesWithDiscountsSequential();
		/* 7. Discount with Stream + composing CompletableFuture */
		System.out.println("******* callFindPricesWithDiscountsAsync() ******");
		callFindPricesWithDiscountsAsync();
		/*
		 * 8. What if one shop takes 30 seconds to respond or event minutes
		 * You want to know the other prices and not wait until all are retrieved (which is what all previous examples did).
		 * To do what car dealers and flight comparators do:
		 * */
		System.out.println("******* callFindPricesWithDiscountsStream() STREAM ******");
		callFindPricesWithDiscountsStream();
	}	
	
	/* 1. Single threaded example using Completable Future */
	private static void callAsyncSimpleCalculatePriceMethod() {
		Shop shop = new Shop("madeUpShop");
		long start = System.nanoTime();
		Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
		long invocationTime = ((System.nanoTime() - start) / 1_000_000);
		System.out.println("Invocation returned after " + invocationTime
		+ " msecs");
		// Do some more tasks, like querying other shops
		doSomethingElse();
		// while the price of the product is being calculated
		try {
			double price = futurePrice.get();
			System.out.printf("Price is %.2f%n", price);
		} catch (Exception e) {
			// We can get an ExecutionException, wrapping
			// the exact same exception we just propagated in the Completable Future
			throw new RuntimeException(e);
		}
		long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
		System.out.println("Price returned after " + retrievalTime + " msecs");
	}
	
	private static void doSomethingElse() {
		System.out.println("Dong something else ...");
	}
	
	/* 2. Example using sequential computation */
	private static void callSyncCalculatePriceMethod() {
		long start = System.nanoTime();
		System.out.println(findPrices("myPhone27S"));
		long duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("Done in " + duration + " msecs");
	}
	
	// Let's assume the API of the store is Synchronous
	private static List<String> findPrices(String product) {
		return shops.stream()
				.map(shop -> {
					return String.format(
							"%s price is %.2f", 
							shop.getName(),
							shop.getPrice(product));
				})
				.collect(Collectors.toList());
	}
	
	/* 3. Example using parallel streams computation (same example above, but parallel Streams) */
	private static void callSyncCalculatePriceMethodParalleStream() {
		long start = System.nanoTime();
		System.out.println(findPricesParallelStream("myPhone27S"));
		long duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("Done in " + duration + " msecs");
	}
	
	/*
	 * We add a major improvement to the code by adding a parallel stream.
	 * But we are going to learn how to use Completable Future for thousands of
	 * processes that do not necessarily are in a stream.
	 * */
	private static List<String> findPricesParallelStream(String product) {
		return shops.parallelStream()
				.map(shop -> {
					return String.format(
							"%s price is %.2f", 
							shop.getName(),
							shop.getPrice(product));
				})
				.collect(Collectors.toList());
	}
	
	/* 4. Example using complex completable future (supports as many as shops exists) */
	private static void callAsyncCompletableFuture() {
		long start = System.nanoTime();
		System.out.println(findPricesCompletableFuture("myPhone27S"));
		long duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("Done in " + duration + " msecs");
	}
	
	private static List<String> findPricesCompletableFuture(String product) {
		/* The first stream creates the completable future (and when created, their execution begins)*/
		List<CompletableFuture<String>> priceFutures = 
				shops.stream()
				.map(
					shop -> 
						CompletableFuture.supplyAsync(() -> shop.getName() + " price is " + shop.getPrice(product))
				)
				.collect(Collectors.toList());
		/* Important note:
		 * We could add a map to the first and use the get.. but this would cause the
		 * completable futures to be executed one by one .. because this is not a parallel stream.
		 * It is better to do it this way. 
		 * */
		
		/* The second calls the "join" so waits for all to finish */
		return priceFutures.stream()
				.map(CompletableFuture::join).collect(Collectors.toList());
	}
	
	/* 5. Example using CompletableFuture with custom Executor */
	private static void callAsyncCompletableFutureWithCustomExecutor() {
		long start = System.nanoTime();
		System.out.println(findPricesCompletableFutureWithCustomExecutor("myPhone27S"));
		long duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("Done in " + duration + " msecs");
	}
	
	private static List<String> findPricesCompletableFutureWithCustomExecutor(String product) {
		/* The first stream creates the completable future (and when created, their execution begins)*/
		List<CompletableFuture<String>> priceFutures = 
				shops.stream()
				.map(
					shop -> { 
					    // notice the second parameter to specify the custom executor
						return
							CompletableFuture.supplyAsync(() -> shop.getName() + " price is " + 
															shop.getPrice(product), CUSTOM_EXECUTOR);
					}
				)
				.collect(Collectors.toList());
		/* Important note:
		 * We could add a map to the first and use the get.. but this would cause the
		 * completable futures to be executed one by one .. because this is not a parallel stream.
		 * It is better to do it this way. 
		 * */
		
		/* The second calls the "join" so waits for all to finish */
		return priceFutures.stream()
				.map(CompletableFuture::join).collect(Collectors.toList());
	}
	
	/* 6. Example with discounts - SEQUENTIAL */
	private static void callFindPricesWithDiscountsSequential() {
		long start = System.nanoTime();
		System.out.println(findPricesWithDiscountsSequential("myPhone27S"));
		long duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("Done in " + duration + " msecs");
	}
	
	private static List<String> findPricesWithDiscountsSequential(String product) {
		return shops.stream()
		.map(shop -> shop.getPriceAsString(product))
		.map(Quote::parse)
		.map(Discount::applyDiscount)
		.collect(Collectors.toList());
	}
	
	/* 7. Discount with Stream + composing CompletableFuture */
	// the advantage of this approach is that the main Thread can be busy
	// on other important tasks .. such as responding UI events
	private static void callFindPricesWithDiscountsAsync() {
		long start = System.nanoTime();
		System.out.println(findPricesWithDiscountAsync("myPhone27S"));
		long duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("Done in " + duration + " msecs");
	}
	
	private static List<String> findPricesWithDiscountAsync(String product) {
		List<CompletableFuture<String>> priceFutures = shops.stream()
				.map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceAsString(product), CUSTOM_EXECUTOR)) // Stream<CompletableFuture<String>>
				.map(future -> future.thenApply(Quote::parse)) // theApply is synchronous
				// thenApply is called here .. because the Quote parse does not interact with any I/O or delay
				// But thenApply does not block the code while waiting .. even though it is sequential 
				.map(future -> future.thenCompose( // Completable-Future<Quote>
						quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), CUSTOM_EXECUTOR)))
				// the thenCompose method allows to pipeline two asynchronous operations
				// passing the result of the first operation, to the second operation when available
				.collect(Collectors.toList());
		
		return priceFutures.stream()
				.map(CompletableFuture::join) // wait for the completion of each
				// CompletableFuture and extract their values
				.collect(Collectors.toList());
	}
	
	/*
	 * 8. What if one shop takes 30 seconds to respond or event minutes
	 * You want to know the other prices and not wait until all are retrieved (which is what all previous examples did).
	 * To do what car dealers and flight comparators do:
	 * */
	private static void callFindPricesWithDiscountsStream() {
		long start = System.nanoTime();
		/*
		 * Then we consume the stream
		 * */
		// thenAccept also has its variant thenAcceptAsync
		// not used here because we want the price as soon as it is available
		// so we avoid it to be sent to the ThreadPool with the Async variant
		long startTime = System.nanoTime();
		CompletableFuture[] futures = 
				findPricesStream("myPhone27S")
				// we print each price as soon as those are available
				.map(f -> f.thenAccept(s -> 
					System.out.println(s + " (done in " + ((System.nanoTime() - startTime) / 1_000_000) + " msecs)")))
				// toArray is a terminal operation, it starts the processing of the stream
				.toArray(size -> new CompletableFuture[size]);
		// joining all CompletableFuture and waiting for their termination
		// completes only when all CompletableFuture passed have completed
		CompletableFuture.allOf(futures).join();
		
		System.out.println("All shops have now responded in "
				+ ((System.nanoTime() - start) / 1_000_000) + " msecs");
	}
	
	/* First, we setup the streams */
	private static Stream<CompletableFuture<String>> findPricesStream(String product) {
		return shops.stream()
				.map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceAsString(product), CUSTOM_EXECUTOR))
				.map(future -> future.thenApply(Quote::parse))
				.map(future -> 
						future.thenCompose(
							quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), CUSTOM_EXECUTOR)
						)
				);
	}
	
}
