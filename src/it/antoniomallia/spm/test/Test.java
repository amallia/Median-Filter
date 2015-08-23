package it.antoniomallia.spm.test;

import it.antoniomallia.spm.Matrix;
import it.antoniomallia.spm.stats.Experiment;
import it.antoniomallia.spm.stats.Experiment.Type;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class Test {

	private static final String testExecMsg = "Executing Test ==> Type: %s\t Thread number: %s\t Matrices number: %s\t Matrix size: %s x %s";
	private static final String timeSpentMsg = "Execution time: %s ms";
	private Type type;
	private int threads;

	public Test(Type type, int threads) {
		this.type = type;
		this.threads = threads;
	}

	public abstract Matrix[] compute(Matrix[] mats) throws Exception;

	public abstract void shutdown();

	public Matrix[] init(int streamsize, int size) {
		Matrix[] initmat = new Matrix[streamsize];
		for (int i = 0; i < streamsize; i++) {
			initmat[i] = new Matrix(size, size);
		}
		return initmat;
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
	public Experiment testcompute(int streamsize, int sizeRow, int sizeCol)
			throws Exception {

		log.info(String.format(testExecMsg, type.getTitle(), threads,
				streamsize, sizeRow, sizeCol));
		Matrix[] initmat = init(streamsize, sizeRow);

		long time = System.currentTimeMillis();

		Matrix[] results = compute(initmat);

		long computationTime = System.currentTimeMillis() - time;
		log.info(String.format(timeSpentMsg, computationTime));
		return new Experiment(type, streamsize, threads, sizeRow,
				computationTime);

	}
}
