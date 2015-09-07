package it.antoniomallia.spm;

import java.util.concurrent.Future;

import lombok.Getter;
import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.skeletons.Farm;

/**
 * Farm Class using Skandium
 * 
 * @author antoniomallia
 *
 */
public class SkandiumFarm {

	@Getter
	private Stream<Matrix, Matrix> stream;

	/** Oggetto skandium del framework */
	private Skandium skandium;

	/**
	 * Constructor
	 * 
	 * @param threads
	 *            threads numbers
	 */
	public SkandiumFarm(int threads) {
		skandium = new Skandium(threads);
		Farm<Matrix, Matrix> root = new Farm<Matrix, Matrix>(
				new SkandiumExecuteFilter());
		stream = skandium.newStream(root);
	}

	/**
	 * @param matrices
	 *            input matrices array
	 * @return computed matrices array using Skandium Map
	 * @throws Exception
	 *             exception for computation
	 */
	public Matrix[] compute(Matrix[] matrices) throws Exception {
		@SuppressWarnings("unchecked")
		Future<Matrix>[] futResults = new Future[matrices.length];
		Matrix[] results = new Matrix[matrices.length];
		for (int i = 0; i < matrices.length; i++) {
			matrices[i].augment();
			futResults[i] = getStream().input(matrices[i]);
		}

		for (int i = 0; i < matrices.length; i++) {
			results[i] = futResults[i].get();
			results[i].diminish();
		}
		return results;
	}

	/**
	 * Shutdown skandium
	 */
	public void shutdown() {
		skandium.shutdown();
	}

}
