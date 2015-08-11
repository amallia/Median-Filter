package it.antoniomallia.spm;


public class ExecuteFilter2 {

	public Matrix execute(Matrix matrix) {
		//System.out.println(Thread.currentThread().getId());
		matrix.medianFilter();
		return matrix;
	}

}
