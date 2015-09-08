package it.antoniomallia.spm;

/**
 * Merge Matrix class
 * 
 * @author antoniomallia
 *
 */
public class MergeMatrix {

	/**
	 * Merge the array of matrices into a single one in a ordered way
	 * @param matrices array of sub-matrices
	 * @return assembled matrix
	 */
	public Matrix merge(Matrix[] matrices) {
		int height = 0;
		for (int i = 0; i < matrices.length; i++) {
			height += matrices[i].getHeight() - 2;
		}
		int[][] res = new int[height][matrices[0].getWidth() - 2];
		for (int i = 0; i < matrices.length; i++) {
			for (int k = 1; k < matrices[i].getHeight() - 1; k++) {
				for (int l = 1; l < matrices[i].getWidth() - 1; l++) {
					res[i * (matrices[0].getHeight() - 2) + k - 1][l - 1] = matrices[i]
							.getMatrix()[k][l];
				}
			}
		}
		return new Matrix(res);
	}
}
