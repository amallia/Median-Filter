package it.antoniomallia.spm.test;

import it.antoniomallia.spm.J8Farm;
import it.antoniomallia.spm.Matrix;
import it.antoniomallia.spm.stats.Experiment.ExperimentType;

import java.util.concurrent.ExecutionException;

public class J8FarmTest extends Test {
	private J8Farm j8Farm;

	public J8FarmTest(int threads) {
		super(ExperimentType.J8_FARM, threads);
		j8Farm = new J8Farm(threads);
	}

	@Override
	public void shutdown() {
		j8Farm.shutdown();
	}

	@Override
	public Matrix[] compute(Matrix[] mats) throws Exception {
		return j8Farm.compute(mats);

	}

}
