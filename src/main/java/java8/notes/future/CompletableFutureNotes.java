package java8.notes.future;

public class CompletableFutureNotes {

	/*
	 * IMPORTANT NOTE 1
	 * 
	 * CompletableFuture is to Future, what Stream is to Collection.
	 * */
	
	/*
	 * IMPORTANT NOTE 2
	 * 
	 * Future was introduced in Java 5. Completable Future enhances those
	 * already existing features and solved more scenarios.
	 * Future has limitations when combining results from different future computations, etc...
	 * 
	 * CompletableFuture offers lambda expressions to provide a declarative API.
	 * */
	
	/*
	 * IMPORTANT NOTE 3
	 * 
	 * CompletableFuture implements the interface: Future
	 * Future is just a handle value hat is not yet available but can be retrieved
	 * by invoking its "get" method after its computation has finally terminated.
	 * */
	
	/*
	 * IMPORTANT NOTE 4
	 * 
	 * The "get" method returns the value contained in the future or remains blocked
	 * until that value is available or times out.
	 * There is no default timeout .. if there is an error in the Shop .. the other code
	 * gets blocked indefinitely waiting for the future to complete ..
	 * This is why it is a good practice to always specify a timeout.
	 */
	
	/*
	 * IMPORTANT NOTE 5
	 * 
	 * Dealing with errors.
	 * Adding a timeout is not enough for when we want to know why
	 * the exception happened or the exception information itself.
	 * To do this, we need to propagate the exception inside the CompletableFuture
	 * through its "completeExceptionally" method.
	 * 
	 * Check the "getPriceAsyncSupplied" method of the Shop for an alternative.
	 */
	
	/*
	 * IMPORTANT NOTE 6
	 * 
	 * Even though on performance, the Parallel Stream version seems equally fast than the
	 * Complex COmpletableFuture version .. it is better to still use the complex CompletableFuture version
	 * for the following reason:
	 * 1. The CompletableFuture has an advantage because it allows to specify a
	 * custom Executor .. so we can configure the Threadpool better to better fit the
	 * requirements of the application.
	 */
	
	/*
	 * IMPORTANT NOTE 7
	 * 
	 * Parallel Streams vs CompletableFuture
	 * 
	 * 1. CompletableFuture allows us to specify our own custom Executor.
	 * 2. To work with I/O disk lectures .. or network connections .. it is better to
	 * use the CompletableFuture (stream rely on a fixed Threadpool that matchedd the number of cores
	 * in your computer.. therefore it does noit scale very well).
	 * 3. Debugging the waits it very hard on streams due to their lazy nature.
	 * */
	
	/*
	 * IMPORTANT NOTE 8
	 * 
	 * Sizing Thread Pools
	 * 
	 * Nthreads = NCPU * UCPU * (1 + W/C)
		where
		■ NCPU is the number of cores, available through Runtime.getRuntime()
			.availableProcessors()
		■ UCPU is the target CPU utilization (between 0 and 1), and
		■ W/C is the ratio of wait time to compute time ==> meaning the percentage
		of your app that will wait for those computations to finish (commonly near 90%+)
	 * */
	
	/*
	 * IMPORTANT NOTE 9
	 * 
	 * thenCompose() vs thenComposeAsync()
	 * 
	 * Any method that has the suffix "Async", means that before it computation
	 * it goes to the Threadpool to be picked up by another Thread.
	 * When composing ... if the current one depends on the previous one .. it makes
	 * no sense to use Async (switching Threads is more expensive).
	 */
	
	/*
	 * IMPORTANT NOTE 9
	 * 
	 * thenCompose() vs thenCombine()
	 * 
	 * Both have their version with the Async suffix.
	 * But, thenCompose() waits for the first to be complete, in order to proceed with the latter.
	 * Whereas thenCombine() does not wait.
	 */
	
	/*
	 * IMPORTANT NOTE 10
	 * 
	 * thenCombine() vs thenCombineAsync()
	 * 
	 * Use thenCombine() when at least one of the processes to combine does not involve
	 * waiting time or I/O (in which case, the sequential should go as second).
	 * If both need it .. better use combineAsync.
	 * 
	 * Using thenCombineAsync() is like having another Future (when used with sequential operations).
	 */
	
	/*
	 * IMPORTANT NOTE 11
	 * 
	 * You can asynchronously consume a synchronous API by simply wrapping its
	 * invocation in a CompletableFuture.
	 * 
	 * You can register a callback on a CompletableFuture to reactively execute some 
	 * code when the Future completes and its result becomes available.
	 * 
	 * You can determine when all values in a list of CompletableFutures have completed, 
	 * or alternatively you can wait for just the first to complete.
	 */
}
