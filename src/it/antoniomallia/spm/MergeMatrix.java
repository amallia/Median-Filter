package it.antoniomallia.spm;

/**
 * Merge Matrix class
 * 
 * @author antoniomallia
 *
 */
public class MergeMatrix {

	private int num;

	/**
	 * Constructor
	 * 
	 * @param num
	 *            thread number
	 */
	public MergeMatrix(int num) {
		this.num = num;
	}

	/**
	 * @param matrices
	 *            array of matrices
	 * @return merged matrix
	 */
	public Matrix merge(Matrix[] matrices) {
		int height = 0, width = 0;
		for (int i = 0; i < num; i++) {
			height += matrices[num * i].getHeight() - 2;
		}
		for (int j = 0; j < num; j++) {
			width += matrices[j].getWidth() - 2;
		}
		int colsize = matrices[0].getHeight() - 2;
		int rowsize = matrices[0].getWidth() - 2;
		int[][] res = new int[height][width];
		for (int i = 0; i < num; i++) {
			for (int j = 0; j < num; j++) {
				for (int k = 1; k < matrices[num * i].getHeight() - 1; k++) {
					for (int l = 1; l < matrices[j].getWidth() - 1; l++) {
						res[i * (colsize) + k - 1][j * (rowsize) + l - 1] = matrices[num
								* i + j].getMatrix()[k][l];
					}
				}
			}
		}
		return new Matrix(res);
	}
}
