package it.antoniomallia.spm;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import lombok.Getter;
import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.skeletons.Farm;

/**
 * Farm Class using Skandium 
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
	 * @param threads threads numbers
	 */
	public SkandiumFarm(int threads) {
		skandium = new Skandium(threads);
		Farm<Matrix, Matrix> root = new Farm<Matrix, Matrix>(
				new SkandiumExecuteFilter());
		stream = skandium.newStream(root);
	}


	/**
	 * @param matrix input matrix
	 * @return computed matrix using Skandium Map
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public Matrix compute(Matrix matrix) throws InterruptedException,
			ExecutionException {
		Future<Matrix> result = stream.input(matrix);
		return result.get();
	}

	
	/**
	 * Shutdown skandium
	 */
	public void shutdown() {
		skandium.shutdown();
	}

}
