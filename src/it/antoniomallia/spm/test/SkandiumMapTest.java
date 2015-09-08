package it.antoniomallia.spm.test;

import it.antoniomallia.spm.Matrix;
import it.antoniomallia.spm.SkandiumMap;
import it.antoniomallia.spm.stats.Experiment.ExperimentType;

import java.util.concurrent.Future;

/**
 * Class used to extend a test of type Skandium Map
 * @author antonio
 *
 */
public class SkandiumMapTest extends Test {
	private SkandiumMap mapreduce;

	/**
	 * Constructor
	 * @param threads number of workers of the map
	 */
	public SkandiumMapTest(int threads) {
		super(ExperimentType.SKANDIUM_MAP, threads);
		mapreduce = new SkandiumMap(threads);
	}

	/* (non-Javadoc)
	 * @see it.antoniomallia.spm.test.Test#compute(it.antoniomallia.spm.Matrix[])
	 */
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

	/* (non-Javadoc)
	 * @see it.antoniomallia.spm.test.Test#shutdown()
	 */
	@Override
	public void shutdown() {
		mapreduce.shutdown();
	}

}
