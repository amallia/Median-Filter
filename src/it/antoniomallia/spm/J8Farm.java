package it.antoniomallia.spm;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

/**
 * Java 8 Farm Implementation
 * 
 * @author antoniomallia
 *
 */
public class J8Farm {

	// Dedicate ForkJoinPool
	private ForkJoinPool fjPool;

	/**
	 * Constructor
	 * 
	 * @param num
	 *            thread number
	 */
	public J8Farm(int num) {
		fjPool = new ForkJoinPool(num);

	}

	/**
	 * Method which invokes method execute() using a farm pattern
	 * 
	 * @param input array of input matrices
	 * @return array of compute matrices
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public Matrix[] compute(Matrix[] input) throws InterruptedException,
			ExecutionException {
		return (Matrix[]) fjPool.submit(
				() -> Arrays.stream(input).parallel()
						.map(m -> new ExecuteFilter().execute(m))
						.toArray(size -> new Matrix[size])).get();
	}
	
	/**
	 * Shutdown the ForkJoinPool
	 */
	public void shutdown() {
		fjPool.shutdown();
	}

}
