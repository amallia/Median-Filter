package it.antoniomallia.spm;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Sequential {
	
	public static Matrix compute(Matrix m) {

		m.medianFilter();
		return m;
	}
	
	public static void testcompute(int streamsize, int sizeRow, int sizeCol)
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
	}

}
