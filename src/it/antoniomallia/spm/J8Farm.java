package it.antoniomallia.spm;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

/**
 * @author antoniomallia
 *
 */
public class J8Farm {

	private ForkJoinPool fjPool;

	/**
	 * Constructor
	 * @param num thread number
	 * @param executor implementation of the IExecutor
	 */
	public J8Farm(int num) {
		fjPool = new ForkJoinPool(num);

	}

	/**
	 * Method which invokes the executor method execute() in a farm pattern
	 * @param input
	 * @return
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public Matrix[] compute(Matrix[] input) throws InterruptedException, ExecutionException {
			return (Matrix[]) fjPool.submit(
					() -> Arrays.stream(input).parallel()
							.map(m -> new ExecuteFilter().execute(m))
							.toArray(size -> new Matrix[size])).get();
	}

}
