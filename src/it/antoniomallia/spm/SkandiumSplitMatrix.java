package it.antoniomallia.spm;

import cl.niclabs.skandium.muscles.Split;

public class SkandiumSplitMatrix implements Split<Matrix, Matrix> {

	private int num;

	public SkandiumSplitMatrix(int num) {
		this.num = num;
	}

	@Override
	public Matrix[] split(Matrix matrix) throws Exception {
		return new SplitMatrix(num).split(matrix);
	}
}
