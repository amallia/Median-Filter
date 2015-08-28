package it.antoniomallia.spm;

import java.util.concurrent.ExecutionException;
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
	/** Stream di input del framework */
	private Stream<Matrix, Matrix> stream;

	/** Oggetto skandium del framework */
	private Skandium skandium;

	/** Numero di threads con cui e' stato instanziato il framework */

	/**
	 * Costruttore principale
	 * 
	 * @param threads
	 *            Numero di threads con cui far eseguire il calcolo parallelo
	 */
	public SkandiumMap(int threads) {
		skandium = new Skandium(threads);
		Map<Matrix, Matrix> mapReduce = new Map<Matrix, Matrix>(
				new SkandiumSplitMatrix(threads), new SkandiumExecuteFilter(),
				new SkandiumMergeMatrix(threads));
		stream = skandium.newStream(mapReduce);

	}


	/**
	 * 
	 * @param m
	 * @return 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public Matrix compute(Matrix m) throws InterruptedException, ExecutionException {
		Future<Matrix> result = stream.input(m);
			return result.get();
	}

	/**
	 * Shutdown skandium
	 */
	public void shutdown() {
		skandium.shutdown();
	}

}
