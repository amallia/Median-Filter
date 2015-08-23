package it.antoniomallia.spm;



public class ExecuteFilter {

	public static  Matrix execute(Matrix matrix) {
		matrix.medianFilter();
		return matrix;
	}

}
