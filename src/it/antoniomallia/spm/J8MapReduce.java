package it.antoniomallia.spm;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class J8MapReduce {
	private int threads;
	private ForkJoinPool fjPool;

	public J8MapReduce(int num) {

		fjPool = new ForkJoinPool(num);
		threads = num;

	}

	public Matrix compute(Matrix input) throws InterruptedException, ExecutionException {
			return fjPool.submit(
					() -> new MergeMatrix(threads).merge(Arrays
							.stream(new SplitMatrix(threads).split(input))
							.parallel().map(n -> new ExecuteFilter().execute(n))
							.toArray(size -> new Matrix[size]))).get();
	}

}
