package it.antoniomallia.spm;


public class ExecuteFilter2 {

	public Matrix execute(Matrix matrix) {
//long time = System.currentTimeMillis();
		matrix.medianFilter();
//		System.out.println("executed over in: "
//		+ (System.currentTimeMillis() - time));

		return matrix;
	}

}
