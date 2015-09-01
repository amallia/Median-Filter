package it.antoniomallia.spm.test;

import it.antoniomallia.spm.Matrix;
import it.antoniomallia.spm.SkandiumFarm;
import it.antoniomallia.spm.stats.Experiment.ExperimentType;

import java.util.concurrent.ExecutionException;

public class SkandiumFarmTest extends Test {

	private SkandiumFarm farm;

	public SkandiumFarmTest(int threads) {
		super(ExperimentType.SKANDIUM_FARM, threads);
		farm = new SkandiumFarm(threads);
	}

	@Override
	public void shutdown() {
		farm.shutdown();
	}

	@Override
	public Matrix[] compute(Matrix[] mats) throws Exception,
			ExecutionException {
		return farm.compute(mats);
	}

}
