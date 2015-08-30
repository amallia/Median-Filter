package it.antoniomallia.spm;

import java.util.concurrent.Future;

import lombok.Getter;
import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.skeletons.Map;

/**
 * Skandium Map Class
 * @author antoniomallia
 *
 */
public class SkandiumMap {

	@Getter
	private Stream<Matrix, Matrix> stream;

	private Skandium skandium;


	/**
	 * constructor
	 * 
	 * @param threads
	 *            Threads number
	 */
	public SkandiumMap(int threads) {
		skandium = new Skandium(threads);
		Map<Matrix, Matrix> mapReduce = new Map<Matrix, Matrix>(
				new SkandiumSplitMatrix(threads), new SkandiumExecuteFilter(),
				new SkandiumMergeMatrix());
		stream = skandium.newStream(mapReduce);

	}


	/**
	 * Method which computes the matrix using skandium Map
	 * @param matrix input matrix
	 * @return computed output matrix
	 * @throws Exception exception for computation  
	 */
	public Matrix compute(Matrix matrix) throws Exception {
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
