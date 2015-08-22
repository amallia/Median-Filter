package it.antoniomallia.spm;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.skeletons.Map;

public class SkandiumMapReduce {

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
	public SkandiumMapReduce(int threads) {
		skandium = new Skandium(threads);
		Map<Matrix, Matrix> mapReduce = new Map<Matrix, Matrix>(
				new SkandiumSplitMatrix(threads), new SkandiumExecuteFilter(),
				new MergeMatrix(threads));
		stream = skandium.newStream(mapReduce);

	}

	public Stream<Matrix, Matrix> getStream() {
		return stream;
	}

	public void setStream(Stream<Matrix, Matrix> stream) {
		this.stream = stream;
	}

	/**
	 * Calcola l'Histogram thresholding di una matrice utilizzando il framework
	 * parallelo istanziato
	 * 
	 * @param m
	 *            Matrice da calcolare
	 * @return Matrice calcolata
	 */
	public Matrix compute(Matrix m) throws InterruptedException,
			ExecutionException {
		Future<Matrix> result = stream.input(m);
		return result.get();
	}

	/**
	 * Metodo per la disattivazione del framework parallelo
	 */
	public void shutdown() {
		skandium.shutdown();
	}

}