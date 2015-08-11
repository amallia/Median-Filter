package it.antoniomallia.spm;

import cl.niclabs.skandium.muscles.Execute;

public class ExecuteFilter implements Execute<Matrix, Matrix>{

	@Override
	public Matrix execute(Matrix matrix) throws Exception {
		matrix.medianFilter();
		return matrix;
	}

}
