package it.antoniomallia.spm;

import it.antoniomallia.spm.stats.Experiment;
import it.antoniomallia.spm.stats.Experiment.Type;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class J8MapReduce {

	int threads;

	public J8MapReduce(int num) {
		threads = num;
		System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", String.valueOf(num));

	}

	public Matrix compute(Matrix input) {
		return Arrays.stream(new SplitMatrix(threads).split(input)).parallel()
				.map(n -> new ExecuteFilter().execute(n))
				.reduce(new Matrix(input.getHeight(), input.getWidth()), (a, b) -> a.add(b));
	}
	
	
	public Experiment testcompute(int streamsize, int sizeRow, int sizeCol)
			throws InterruptedException, ExecutionException {

		System.out.println("#############################");
		System.out.println("J8MapReduce - Threads: " + threads + " Streamsize: "
				+ streamsize + " Matrixsize: " + sizeRow + " x " + sizeCol);

		Matrix[] initmat = new Matrix[streamsize];
		for (int i = 0; i < streamsize; i++) {
			initmat[i] = new Matrix(sizeRow, sizeCol);
		}
		Matrix[] results = new Matrix[streamsize];

		long time = System.currentTimeMillis();

		for (int i = 0; i < streamsize; i++) {
			results[i] = compute(initmat[i]);
		}

		System.out.println("Computation over in: "
				+ (System.currentTimeMillis() - time));
		return new Experiment(Type.J8_MAPREDUCE,streamsize,threads, sizeRow,(System.currentTimeMillis() - time));

	}
}
