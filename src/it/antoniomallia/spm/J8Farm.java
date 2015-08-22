package it.antoniomallia.spm;

import java.util.Arrays;

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
				.map(m -> ExecuteFilter.execute(m))
				.toArray(size -> new Matrix[size]);
	}

}
