package it.antoniomallia.spm.test;

import it.antoniomallia.spm.Matrix;
import it.antoniomallia.spm.SkandiumMap;
import it.antoniomallia.spm.stats.Experiment.ExperimentType;

import java.util.concurrent.Future;

public class SkandiumMapTest extends Test {
	private SkandiumMap mapreduce;

	public SkandiumMapTest(int threads) {
		super(ExperimentType.SKANDIUM_MAP, threads);
		mapreduce = new SkandiumMap(threads);
	}

	@Override
	public Matrix[] compute(Matrix[] initmat) throws Exception{
		@SuppressWarnings("unchecked")
		Future<Matrix>[] futResults = new Future[initmat.length];
		Matrix[] results = new Matrix[initmat.length];

		for (int i = 0; i < initmat.length; i++) {
			futResults[i] = (mapreduce.getStream().input(initmat[i]));
		}

		for (int i = 0; i < initmat.length; i++) {
				results[i] = futResults[i].get();
		}
		return results;
	}

	@Override
	public void shutdown() {
		mapreduce.shutdown();
	}

}
