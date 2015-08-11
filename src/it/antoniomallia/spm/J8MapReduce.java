package it.antoniomallia.spm;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class J8MapReduce {

	int threads;

	public J8MapReduce(int num) {
		threads = num;
		System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", String.valueOf(num));

	}

	public Matrix compute(Matrix input) {
		return Arrays.stream(new SplitMatrix2(threads).split(input)).parallel()
				.map(n -> new ExecuteFilter2().execute(n)).sequential()
				.reduce(new Matrix(threads), (a, b) -> a.add(b));
	}
	
	
	public void testcompute(int streamsize, int sizeRow, int sizeCol)
			throws InterruptedException, ExecutionException {

		System.out.println("#############################");
		System.out.println("J8MapReduce - Threads: " + threads + " Streamsize: "
				+ streamsize + " Matrixsize: " + sizeRow + " x " + sizeCol);

		Matrix[] initmat = new Matrix[streamsize];
		for (int i = 0; i < streamsize; i++) {
			initmat[i] = new Matrix(sizeRow, sizeCol);
		}

		long time = System.currentTimeMillis();

		Matrix[] results = new Matrix[streamsize];
		for (int i = 0; i < streamsize; i++) {
			results[i] = compute(initmat[i]);
		}

		System.out.println("Computation over in: "
				+ (System.currentTimeMillis() - time));
	}
}
