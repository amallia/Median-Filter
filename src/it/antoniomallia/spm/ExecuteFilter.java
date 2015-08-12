package it.antoniomallia.spm;

import cl.niclabs.skandium.muscles.Execute;

public class ExecuteFilter implements Execute<Matrix, Matrix>{

	@Override
	public Matrix execute(Matrix matrix) throws Exception {
//		long time = System.currentTimeMillis();
		matrix.medianFilter();
//		System.out.println("executed over in: "
//		+ (System.currentTimeMillis() - time));

		return matrix;
	}

}
