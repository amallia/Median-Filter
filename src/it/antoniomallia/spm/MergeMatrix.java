package it.antoniomallia.spm;



public class MergeMatrix{

	private int num;

	public MergeMatrix(int num) {
		this.num = num;
	}

	public Matrix merge(Matrix[] m) {
		int colsize = m[0].getHeight() - 2;
		int rowsize = m[0].getWidth() - 2;
		int[][] res = new int[num * (colsize)][num * (rowsize)];
		for (int i = 0; i < num; i++) {
			for (int j = 0; j < num; j++) {
				for (int k = 1; k < colsize + 1; k++) {
					for (int l = 1; l < rowsize + 1; l++) {
						res[i * (colsize) + k - 1][j * (rowsize) + l - 1] = m[num
								* i + j].getMatrix()[k][l];
					}
				}
			}
		}
		return new Matrix(res);
	}
}
