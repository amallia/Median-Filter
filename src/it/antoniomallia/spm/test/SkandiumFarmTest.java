package it.antoniomallia.spm.test;

import it.antoniomallia.spm.Matrix;
import it.antoniomallia.spm.SkandiumFarm;
import it.antoniomallia.spm.stats.Experiment.Type;

import java.util.concurrent.Future;

public class SkandiumFarmTest extends Test {

	private SkandiumFarm farm;

	public SkandiumFarmTest(int threads) {
		super(Type.SKANDIUM_FARM, threads);
		farm = new SkandiumFarm(threads);
	}

	@Override
	public void shutdown() {
		farm.shutdown();
	}

	@Override
	public Matrix[] compute(Matrix[] mats) throws Exception {
        @SuppressWarnings("unchecked")
		Future<Matrix>[] futResults = new Future[mats.length];
		Matrix[] results = new Matrix[mats.length];
		for (int i = 0; i < mats.length; i++) {
			futResults[i] = farm.getStream().input(mats[i]);
		}

		for (int i = 0; i < mats.length; i++) {
			results[i] = futResults[i].get();
		}
		return results;
	}

}
