package it.antoniomallia.spm.test;

import it.antoniomallia.spm.J8Map;
import it.antoniomallia.spm.Matrix;
import it.antoniomallia.spm.stats.Experiment.ExperimentType;

/**
 * Class used to extend a test of type J8 Map
 * @author antonio
 *
 */
public class J8MapTest extends Test {

	private J8Map j8Map;

	/**
	 * Constructor
	 * @param threads number of workers
	 */
	public J8MapTest(int threads) {
		super(ExperimentType.J8_MAP, threads);
		j8Map = new J8Map(threads);

	}

	/* (non-Javadoc)
	 * @see it.antoniomallia.spm.test.Test#shutdown()
	 */
	@Override
	public void shutdown() {
		j8Map.shutdown();
	}

	/* (non-Javadoc)
	 * @see it.antoniomallia.spm.test.Test#compute(it.antoniomallia.spm.Matrix[])
	 */
	@Override
	public Matrix[] compute(Matrix[] mats) throws Exception {
		Matrix[] results = new Matrix[mats.length];
		for (int i = 0; i < mats.length; i++) {
			results[i] = j8Map.compute(mats[i]);
		}
		return results;
	}

}
