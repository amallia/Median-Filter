package it.antoniomallia.spm;

import it.antoniomallia.spm.stats.Experiment;
import it.antoniomallia.spm.stats.Experiment.Type;

import java.util.concurrent.ExecutionException;

public class Sequential {
	
	public static Matrix compute(Matrix m) {

		m.medianFilter();
		return m;
	}
	
	public static Experiment testcompute(int streamsize, int sizeRow, int sizeCol)
			throws InterruptedException, ExecutionException {

		System.out.println("#############################");
		System.out.println("Sequential - Streamsize: "
				+ streamsize + " Matrixsize: " + sizeRow + " x " + sizeCol);

		Matrix[] initmat = new Matrix[streamsize];
		for (int i = 0; i < streamsize; i++) {
			initmat[i] = new Matrix(sizeRow, sizeCol);
		}

		long time = System.currentTimeMillis();

		Matrix[] results = new Matrix[streamsize];
		for (int i = 0; i < streamsize; i++) {
			results[i] = compute(initmat[i]);
		}

		System.out.println("Computation over in: "
				+ (System.currentTimeMillis() - time));
		return new Experiment(Type.SEQUENTIAL,streamsize,0, sizeRow,(System.currentTimeMillis() - time));

	}

}
