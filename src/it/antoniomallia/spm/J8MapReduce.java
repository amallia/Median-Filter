package it.antoniomallia.spm;

import java.util.Arrays;

public class J8MapReduce {

	int workers;

	public J8MapReduce(int num) {
		workers = num;
		System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", String.valueOf(num));

	}

	public Matrix compute(Matrix input) {
		return Arrays.stream(new SplitMatrix2(workers).split(input)).parallel()
				.map(n -> new ExecuteFilter2().execute(n)).sequential()
				.reduce(new Matrix(workers), (a, b) -> a.add(b));
	}
}
