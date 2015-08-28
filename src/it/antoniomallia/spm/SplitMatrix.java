package it.antoniomallia.spm;

import lombok.AllArgsConstructor;

/**
 * Split Class
 * @author antoniomallia
 *
 */
@AllArgsConstructor
public class SplitMatrix {

	private int num;

	/** Method in charge of matrix split
	 * @param matrix input matrix
	 * @return array of submatrices ordered
	 */
	
	//TODO FIX the moreRow moreCol
	public Matrix[] split(Matrix matrix) {
		Matrix[] mats = new Matrix[num * num];
		int rowsize = matrix.getWidth() / num;
		int colsize = matrix.getHeight() / num;
		int moreRow = matrix.getWidth() % num;
		int moreCol = matrix.getHeight() % num;
		for (int i = 0; i < num; i++) {
			for (int j = 0; j < num; j++) {
				mats[num * i + j] = matrix.subMatrix((j * rowsize), ((j + 1)
						* rowsize - 1), i * colsize, (i + 1) * colsize - 1);
			}
		}
		return mats;
	}
}
