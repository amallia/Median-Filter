package it.antoniomallia.spm;

import cl.niclabs.skandium.muscles.Execute;

public class ExecuteFilter implements Execute<Matrix, Matrix>{

	@Override
	public Matrix execute(Matrix matrix) throws Exception {
		//System.out.println(Thread.currentThread().getId());
		matrix.medianFilter();
		return matrix;
	}

}
