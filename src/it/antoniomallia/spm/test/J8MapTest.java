package it.antoniomallia.spm.test;

import it.antoniomallia.spm.J8MapReduce;
import it.antoniomallia.spm.Matrix;
import it.antoniomallia.spm.stats.Experiment.Type;

public class J8MapTest extends Test {

	private J8MapReduce j8MapReduce;

	public J8MapTest(int threads) {
		super(Type.J8_MAPREDUCE, threads);
		j8MapReduce = new J8MapReduce(threads);

	}

	@Override
	public void shutdown() {

	}

	@Override
	public Matrix[] compute(Matrix[] mats) throws Exception {
		Matrix [] results = new Matrix[mats.length];
		for (int i = 0; i < mats.length; i++) {
			results[i] = j8MapReduce.compute(mats[i]);
		}
		return results;
	}

}
