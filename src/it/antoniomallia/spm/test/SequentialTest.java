package it.antoniomallia.spm.test;

import it.antoniomallia.spm.Matrix;
import it.antoniomallia.spm.Sequential;
import it.antoniomallia.spm.stats.Experiment.Type;

public class SequentialTest extends Test {

	public SequentialTest() {
		super(Type.SEQUENTIAL, 0);
	}

	@Override
	public Matrix[] compute(Matrix[] mats) throws Exception {
		Matrix[] results = new Matrix[mats.length];
		for (int i = 0; i < mats.length; i++) {
			results[i] = Sequential.compute(mats[i]);
		}
		return results;
	}

	@Override
	public void shutdown() {		
	}

}
