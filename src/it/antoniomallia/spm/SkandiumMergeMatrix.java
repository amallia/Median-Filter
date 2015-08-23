package it.antoniomallia.spm;

import cl.niclabs.skandium.muscles.Merge;

public class SkandiumMergeMatrix implements Merge<Matrix, Matrix> {

	private int num;

	public SkandiumMergeMatrix(int num) {
		this.num = num;
	}

	@Override
	public Matrix merge(Matrix[] m) throws Exception {
		return new MergeMatrix(num).merge(m);
	}
}
