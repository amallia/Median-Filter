package it.antoniomallia.spm;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.skeletons.Map;

public class MapReduce {

	/** Stream di input del framework */
	private Stream<Matrix, Matrix> stream;

	/** Oggetto skandium del framework */
	private Skandium skandium;
	/** Numero di threads con cui e' stato instanziato il framework */
	private int threads;

	/**
	 * Costruttore principale
	 * 
	 * @param threads
	 *            Numero di threads con cui far eseguire il calcolo parallelo
	 */
	public MapReduce(int threads) {

		this.threads = threads;
		skandium = new Skandium(threads);

		Map<Matrix, Matrix> mapReduce = new Map<Matrix, Matrix>(
				new SplitMatrix(threads), new ExecuteFilter(), new MergeMatrix(
						threads));

		// Genero il modello, formato da un pipeline a due stati, con due map
		// Map<Matrix, Matrix> mapHisto = new Map<Matrix, Matrix>(new
		// SplitMatrix(
		// worker), new ComputeHistogram(), new MergeHistoMatrix());
		//
		// Map<Matrix, Matrix> mapThre = new Map<Matrix, Matrix>(new
		// SplitMatrix(
		// worker), new ComputeThreshold(), new MergeMatrix());

		// Pipe<Matrix, Matrix> root = new Pipe<Matrix, Matrix>(mapHisto,
		// mapThre);
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

	/**
	 * Esegue una computazione di test su uno stream di matrici che vengono
	 * generate casualmente a runtime
	 * 
	 * @param streamsize
	 *            Dimensione dello stream di matrici
	 * @param sizeRow
	 *            Numero di righe delle matrici
	 * @param sizeCol
	 *            Numero di colonne delle matrici
	 * @param perc
	 *            Percentuale di soglia delle matrici
	 */
	@SuppressWarnings("unchecked")
	public void testcompute(int streamsize, int sizeRow, int sizeCol)
			throws InterruptedException, ExecutionException {

		System.out.println("#############################");
		System.out.println("MapReduce - Threads: " + threads + " Streamsize: "
				+ streamsize + " Matrixsize: " + sizeRow + " x " + sizeCol);

		Matrix[] initmat = new Matrix[streamsize];
		for (int i = 0; i < streamsize; i++) {
			initmat[i] = new Matrix(sizeRow, sizeCol);
		}

		long time = System.currentTimeMillis();

		Future<Matrix>[] results = new Future[streamsize];
		for (int i = 0; i < streamsize; i++) {
			results[i] = (stream.input(initmat[i]));
		}

		for (int i = 0; i < streamsize; i++) {
			results[i].get();
		}

		System.out.println("Computation over in: "
				+ (System.currentTimeMillis() - time));
	}

}
