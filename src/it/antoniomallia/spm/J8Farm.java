package it.antoniomallia.spm;

import it.antoniomallia.spm.stats.Experiment;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class J8Farm {
	int threads;

	public J8Farm(int num) {
		threads = num;
		System.setProperty(
				"java.util.concurrent.ForkJoinPool.common.parallelism",
				String.valueOf(num));

	}

	public Matrix[] compute(Matrix[] input) {

		return Arrays.stream(input).parallel()
				.map(m -> new ExecuteFilter2().execute(m))
				.toArray(size -> new Matrix[size]);
	}

	public Experiment testcompute(int streamsize, int sizeRow, int sizeCol)
			throws InterruptedException, ExecutionException {

		System.out.println("#############################");
		System.out.println("J8 Farm Computation - Threads: " + threads
				+ " Streamsize: " + streamsize + " Matrixsize: " + sizeRow
				+ " x " + sizeCol);

		Matrix[] initmat = new Matrix[streamsize];
		for (int i = 0; i < streamsize; i++) {
			initmat[i] = new Matrix(sizeRow, sizeCol);
		}

		long time = System.currentTimeMillis();

		Matrix[] results = compute(initmat);

		System.out.println("Computation over in: "
				+ (System.currentTimeMillis() - time));
		return new Experiment(threads, sizeRow,
				(System.currentTimeMillis() - time));

	}
}
