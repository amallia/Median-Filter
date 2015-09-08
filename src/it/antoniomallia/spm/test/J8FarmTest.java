package it.antoniomallia.spm.test;

import it.antoniomallia.spm.J8Farm;
import it.antoniomallia.spm.Matrix;
import it.antoniomallia.spm.stats.Experiment.ExperimentType;

/**
 * Class used to extend a test of type J8 Farm
 * @author antonio
 *
 */
public class J8FarmTest extends Test {
	private J8Farm j8Farm;

	/**
	 * Constructor
	 * @param threads number of workers
	 */
	public J8FarmTest(int threads) {
		super(ExperimentType.J8_FARM, threads);
		j8Farm = new J8Farm(threads);
	}

	/* (non-Javadoc)
	 * @see it.antoniomallia.spm.test.Test#shutdown()
	 */
	@Override
	public void shutdown() {
		j8Farm.shutdown();
	}

	/* (non-Javadoc)
	 * @see it.antoniomallia.spm.test.Test#compute(it.antoniomallia.spm.Matrix[])
	 */
	@Override
	public Matrix[] compute(Matrix[] mats) throws Exception {
		return j8Farm.compute(mats);

	}

}
