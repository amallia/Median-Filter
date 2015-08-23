package it.antoniomallia.spm.test;

import it.antoniomallia.spm.Matrix;
import it.antoniomallia.spm.SkandiumMapReduce;
import it.antoniomallia.spm.stats.Experiment.Type;

import java.util.concurrent.Future;

public class SkandiumMapTest extends Test {
	private SkandiumMapReduce mapreduce;

	public SkandiumMapTest(int threads) {
		super(Type.SKANDIUM_MAPEDUCE, threads);
		mapreduce = new SkandiumMapReduce(threads);
	}

	@Override
	public Matrix[] compute(Matrix[] initmat) throws Exception {
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
