package it.antoniomallia.spm.test;

import it.antoniomallia.spm.Matrix;
import it.antoniomallia.spm.stats.Experiment;
import it.antoniomallia.spm.stats.Experiment.ExperimentType;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Abstract class used to execute a test
 * @author antonio
 *
 */
@Log4j2
@AllArgsConstructor
public abstract class Test {

	private static final String testExecMsg = "Executing Test ==> Type: %s\t Thread number: %s\t Matrices number: %s\t Matrix size: %s x %s";
	private static final String timeSpentMsg = "Execution time: %s ms";
	private ExperimentType type;
	private int threads;

	
	/**
	 * Method to compute the array of matrices 
	 * @param mats Array of matrix inputs
	 * @return output array of matrices
	 * @throws Exception Execution exception
	 */
	public abstract Matrix[] compute(Matrix[] mats) throws Exception;

	/**
	 * Method called at the end of the test
	 */
	public abstract void shutdown();

	/**
	 * @param streamsize stream elements number
	 * @param size size of each element
	 * @return array of genereated random matrices
	 */
	public Matrix[] init(int streamsize, int size) {
		Matrix[] initmat = new Matrix[streamsize];
		for (int i = 0; i < streamsize; i++) {
			initmat[i] = new Matrix(size, size).randomValues();
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
	 * 
	 * @throws Exception exception for computation
	 * 
	 * @return Execution stats
	 */
	public Experiment testcompute(int streamsize, int sizeRow, int sizeCol)
			throws Exception {

		log.info(String.format(testExecMsg, type.getTitle(), threads,
				streamsize, sizeRow, sizeCol));
		Matrix[] initmat = init(streamsize, sizeRow);

		long time = System.currentTimeMillis();

		compute(initmat);

		long computationTime = System.currentTimeMillis() - time;
		log.info(String.format(timeSpentMsg, computationTime));
		return new Experiment(streamsize, threads, sizeRow, computationTime,
				type);

	}
}
