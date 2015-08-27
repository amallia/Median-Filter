package it.antoniomallia.spm;


/**
 * @author antoniomallia
 *
 */
public class SplitMatrix {

	private int num;

	/**
	 * @param num
	 */
	public SplitMatrix(int num) {
		this.num = num;
	}

	/**
	 * @param matrix
	 * @return
	 */
	public Matrix[] split(Matrix matrix) {
		Matrix[] mats = new Matrix[num * num];
		int rowsize = matrix.getWidth() / num;
		int colsize = matrix.getHeight() / num;
		for (int i = 0; i < num; i++) {
			for (int j = 0; j < num; j++) {
				mats[num * i + j] = matrix.subMatrix(j * rowsize, (j + 1)
						* rowsize - 1, i * colsize, (i + 1) * colsize - 1);
			}
		}
		return mats;
	}
}
