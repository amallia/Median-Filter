package it.antoniomallia.spm;

import lombok.AllArgsConstructor;

/**
 * Merge Matrix class
 * 
 * @author antoniomallia
 *
 */
@AllArgsConstructor
public class MergeMatrix {

	private int num;

	/**
	 * Method in charge of merging an array of submatrices to a single one
	 * 
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
		int[][] res = new int[height][width];
		for (int i = 0; i < num; i++) {
			for (int j = 0; j < num; j++) {
				for (int k = 1; k < matrices[num * i].getHeight() - 1; k++) {
					for (int l = 1; l < matrices[j].getWidth() - 1; l++) {
						res[i * (matrices[num * i].getHeight() - 2) + k - 1][j
								* (matrices[j].getWidth() - 2) + l - 1] = matrices[num
								* i + j].getMatrix()[k][l];
					}
				}
			}
		}
		return new Matrix(res);
	}
}
