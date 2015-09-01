package it.antoniomallia.spm;

import lombok.AllArgsConstructor;

/**
 * Split Class
 * 
 * @author antoniomallia
 *
 */
@AllArgsConstructor
public class SplitMatrix {

	private int num;

	/**
	 * Method in charge of matrix split
	 * 
	 * @param matrix
	 *            input matrix
	 * @return array of submatrices ordered
	 */

	public Matrix[] split(Matrix matrix) {
		Matrix[] mats = new Matrix[num];
		int colsize = matrix.getHeight() / num;
		int i;
		for (i = 0; i < num - 1; i++) {
			mats[i] = matrix.subMatrix(0, matrix.getWidth() - 1, i * colsize,
					(i + 1) * colsize - 1);
		}
		mats[num-1] = matrix.subMatrix(0, matrix.getWidth() - 1, i * colsize,
				matrix.getHeight() - 1);
		return mats;
	}
}
