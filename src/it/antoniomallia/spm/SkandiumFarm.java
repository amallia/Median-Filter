package it.antoniomallia.spm;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.skeletons.Farm;

public class SkandiumFarm {

	private Stream<Matrix, Matrix> stream;

	/** Oggetto skandium del framework */
	private Skandium skandium;

	/** Numero di threads con cui e' stato instanziato il framework */
	private int threads;

	public SkandiumFarm(int threads) {

		this.threads = threads;
		skandium = new Skandium(threads);

		// Genero il modello, formato da un farm dove viene calcolato
		// l'Histogram thresholding di ogni matrice

		Farm<Matrix, Matrix> root = new Farm<Matrix, Matrix>(
				new ExecuteFilter());
		stream = skandium.newStream(root);
	}

	public Matrix compute(Matrix m) throws InterruptedException,
			ExecutionException {
		Future<Matrix> result = stream.input(m);
		return result.get();
	}

	public void shutdown() {
		skandium.shutdown();
	}

	public void testcompute(int streamsize, int sizeRow, int sizeCol)
			throws InterruptedException, ExecutionException {


		System.out.println("#############################");
		System.out.println("Parallel Farm Computation - Threads: " + threads
				+ " Streamsize: " + streamsize + " Matrixsize: " + sizeRow
				+ " x " + sizeCol);

		ArrayList<Matrix> initmat = new ArrayList<Matrix>(streamsize);
		for (int i = 0; i < streamsize; i++) {
			initmat.add(i, new Matrix(sizeRow, sizeCol));
		}
		
		long time = System.currentTimeMillis();

		// @SuppressWarnings("unchecked")
		ArrayList<Future<Matrix>> results = new ArrayList<Future<Matrix>>(
				streamsize);
		for (int i = 0; i < streamsize; i++) {
			results.add(i, (stream.input(initmat.get(i))));
		}

		for (Future<Matrix> fut : results) {
			fut.get();
		}

		System.out.println("Computation over in: "
				+ (System.currentTimeMillis() - time));
	}

}
