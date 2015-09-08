package it.antoniomallia.spm.test;

import it.antoniomallia.spm.Matrix;
import it.antoniomallia.spm.Sequential;
import it.antoniomallia.spm.stats.Experiment.ExperimentType;

/**
 *  Class used to extend a test of type Sequential
 * @author antonio
 *
 */
public class SequentialTest extends Test {

	/**
	 * Constructor
	 */
	public SequentialTest() {
		super(ExperimentType.SEQUENTIAL, 0);
	}

	/* (non-Javadoc)
	 * @see it.antoniomallia.spm.test.Test#compute(it.antoniomallia.spm.Matrix[])
	 */
	@Override
	public Matrix[] compute(Matrix[] mats) {
		Matrix[] results = new Matrix[mats.length];
		for (int i = 0; i < mats.length; i++) {
			results[i] = Sequential.compute(mats[i]);
		}
		return results;
	}

	/* (non-Javadoc)
	 * @see it.antoniomallia.spm.test.Test#shutdown()
	 */
	@Override
	public void shutdown() {
	}

}
