package it.antoniomallia.spm;

import cl.niclabs.skandium.muscles.Execute;

public class SkandiumExecuteFilter implements Execute<Matrix, Matrix> {

	@Override
	public Matrix execute(Matrix matrix) throws Exception {
		return ExecuteFilter.execute(matrix);
	}

}
