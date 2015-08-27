package it.antoniomallia.spm;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class J8Farm {

	private ForkJoinPool fjPool;

	public J8Farm(int num) {
		fjPool = new ForkJoinPool(num);

	}

	public Matrix[] compute(Matrix[] input) {
		try {
			return (Matrix[]) fjPool.submit(
					() -> Arrays.stream(input).parallel()
							.map(m -> ExecuteFilter.execute(m))
							.toArray(size -> new Matrix[size])).get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
