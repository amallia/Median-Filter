package it.antoniomallia.spm.test;

import it.antoniomallia.spm.Matrix;
import it.antoniomallia.spm.SkandiumMap;
import it.antoniomallia.spm.stats.Experiment.ExperimentType;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class SkandiumMapTest extends Test {
	private SkandiumMap mapreduce;

	public SkandiumMapTest(int threads) {
		super(ExperimentType.SKANDIUM_MAPEDUCE, threads);
		mapreduce = new SkandiumMap(threads);
	}

	@Override
	public Matrix[] compute(Matrix[] initmat) {
		@SuppressWarnings("unchecked")
		Future<Matrix>[] futResults = new Future[initmat.length];
		Matrix[] results = new Matrix[initmat.length];

		for (int i = 0; i < initmat.length; i++) {
			futResults[i] = (mapreduce.getStream().input(initmat[i]));
		}

		for (int i = 0; i < initmat.length; i++) {
			try {
				results[i] = futResults[i].get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return results;
	}

	@Override
	public void shutdown() {
		mapreduce.shutdown();
	}

}
