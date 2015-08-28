package it.antoniomallia.spm.test;

import it.antoniomallia.spm.J8Map;
import it.antoniomallia.spm.Matrix;
import it.antoniomallia.spm.stats.Experiment.ExperimentType;

import java.util.concurrent.ExecutionException;

public class J8MapTest extends Test {

	private J8Map j8Map;

	public J8MapTest(int threads) {
		super(ExperimentType.J8_MAPREDUCE, threads);
		j8Map = new J8Map(threads);

	}

	@Override
	public void shutdown() {
		j8Map.shutdown();
	}

	@Override
	public Matrix[] compute(Matrix[] mats) throws InterruptedException,
			ExecutionException {
		Matrix[] results = new Matrix[mats.length];
		for (int i = 0; i < mats.length; i++) {
			results[i] = j8Map.compute(mats[i]);
		}
		return results;
	}

}
