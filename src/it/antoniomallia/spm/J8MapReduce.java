package it.antoniomallia.spm;

import java.util.Arrays;

public class J8MapReduce {

	private int threads;

	public J8MapReduce(int num) {
		threads = num;
		System.setProperty(
				"java.util.concurrent.ForkJoinPool.common.parallelism",
				String.valueOf(num));

	}

	public Matrix compute(Matrix input) {
		return new MergeMatrix(threads).merge(Arrays
				.stream(new SplitMatrix(threads).split(input)).parallel()
				.map(n -> ExecuteFilter.execute(n))
				.toArray(size -> new Matrix[size]));
		// .reduce(new Matrix(input.getHeight(), input.getWidth()),
		// (a, b) -> a.add(b));
	}

}
