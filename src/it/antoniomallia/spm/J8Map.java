package it.antoniomallia.spm;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

/**
 * Java 8 Map Implementation
 * 
 * @author antoniomallia
 *
 */
public class J8Map {
	private int threads;

	// Dedicated ForkJoinPool
	private ForkJoinPool fjPool;

	/**
	 * Constructor
	 * 
	 * @param num
	 *            thread number
	 */
	public J8Map(int num) {
		fjPool = new ForkJoinPool(num);
		threads = num;
	}

	/**
	 * Method which computes the matrix using a Map pattern
	 * 
	 * @param input
	 *            matrix
	 * @return computed output matrix
	 * @throws Exception
	 *             exception for computation
	 */
	public Matrix compute(Matrix input) throws Exception {
		return fjPool.submit(
				() -> new MergeMatrix().merge(Arrays
						.stream(new SplitMatrix(threads).split(input))
						.parallel().map(n -> new ExecuteFilter().execute(n))
						.toArray(size -> new Matrix[size]))).get();
	}

	/**
	 * Shutdown the ForkJoinPool
	 */
	public void shutdown() {
		fjPool.shutdown();
	}

}
