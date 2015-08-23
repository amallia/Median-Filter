package it.antoniomallia.spm;

public class SplitMatrix {

	private int num;

	public SplitMatrix(int num) {
		this.num = num;
	}

	public Matrix[] split(Matrix matrix) {
		
		// long time = System.currentTimeMillis();
		Matrix[] mats = new Matrix[num * num];
		int rowsize = matrix.getWidth() / num;
		int colsize = matrix.getHeight() / num;
		
		// System.out.println(colsize);
		for (int i = 0; i < num; i++) {
			for (int j = 0; j < num; j++) {
				// System.out.println(j * rowsize+" "+((j + 1) * rowsize -
				// 1)+" "+ i * colsize+" "+ ((i + 1) * colsize-1));
				mats[num * i + j] = matrix.subMatrix(j * rowsize, (j + 1)
						* rowsize - 1, i * colsize, (i + 1) * colsize - 1);
			}
		}
		// System.out.println("Split over in: "
		// + (System.currentTimeMillis() - time));
		return mats;
	}
}
