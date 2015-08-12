package it.antoniomallia.spm;

import cl.niclabs.skandium.muscles.Split;

public class SplitMatrix implements Split<Matrix, Matrix> {

	private int num;

	public SplitMatrix(int num) {
		this.num = num;
	}

	@Override
	public Matrix[] split(Matrix matrix) throws Exception {
//		long time = System.currentTimeMillis();

		Matrix[] mats = new Matrix[num * num];
		int rowsize = matrix.getWidth() / num;
		int colsize = matrix.getHeight() / num;
		int moreRowSize = matrix.getWidth() % num;
		int moreColSize = matrix.getHeight() % num;
		// System.out.println(colsize);
		for (int i = 0; i < num; i++) {
			for (int j = 0; j < num; j++) {
				int rowStart = j * rowsize;
				int rowStop = (j + 1) * rowsize - 1;
				int colStart = i * colsize;
				int colStop = (i + 1) * colsize - 1;

				mats[num * i + j] = matrix.subMatrix(rowStart, rowStop,
						colStart, colStop);
			}
		}
//		System.out.println("Split over in: "
//				+ (System.currentTimeMillis() - time));
		return mats;
	}
}
