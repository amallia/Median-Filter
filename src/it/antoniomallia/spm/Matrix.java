package it.antoniomallia.spm;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * @author antoniomallia
 *
 */
/**
 * @author antoniomallia
 *
 */
/**
 * @author antoniomallia
 *
 */
/**
 * @author antoniomallia
 *
 */
public class Matrix {

	public int[][] matrix;
	int num;

	int rowStart, rowEnd, colStart, colEnd;

	public int getHeight() {
		return matrix[0].length;
	}

	public int getWidth() {
		return matrix.length;
	}

	public Matrix(int[][] matrix) {
		this.matrix = matrix;
	}

	public Matrix(int w) {
		num = w;
		// TODO Auto-generated constructor stub
	}

	public Matrix(int sizeRow, int sizeCol) {

		int[][] rmat = new int[sizeRow][sizeCol];
		for (int i = 0; i < sizeRow; i++) {
			for (int j = 0; j < sizeCol; j++) {
				rmat[i][j] = new Color((int) Math.random() * 255,
						(int) Math.random() * 255, (int) Math.random() * 255)
						.getRGB();
			}
		}

		this.matrix = rmat;

	}

	/**
	 * This method applies the median filter algorithm to the current matrix
	 */
	public void medianFilter() {
		int[][] denoisedMatrix = new int[getHeight()][getWidth()];
		// The window side length (square window)
		int windowSize = 3;
		// window area
		int window = windowSize * windowSize;
		// padding from the center
		int edge = windowSize / 2;

		Color[] pixel = new Color[window];
		// three arrays used for RGB colors
		int[] r = new int[window];
		int[] g = new int[window];
		int[] b = new int[window];

		// for each pixel in the matrix (border excluded)
		for (int i = 1; i < this.getWidth() - edge; i++) {
			for (int j = 1; j < this.getHeight() - edge; j++) {
				// get all pixel values in the window
				for (int k = 0; k < windowSize; k++) {
					for (int l = 0; l < windowSize; l++) {
						pixel[k * windowSize + l] = new Color(matrix[i + k
								- edge][j + l - edge]);
					}
				}
				// Decompose the colors in RGB
				for (int k = 0; k < window; k++) {
					r[k] = pixel[k].getRed();
					g[k] = pixel[k].getGreen();
					b[k] = pixel[k].getBlue();
				}
				// Sort
				Arrays.sort(r);
				Arrays.sort(g);
				Arrays.sort(b);
				// Get the median value and generate the color
				denoisedMatrix[i][j] = new Color(r[window / 2], g[window / 2],
						b[window / 2]).getRGB();
			}
		}
		matrix = denoisedMatrix;
	}

	/**
	 * This method converts the current matrix to a BufferedImage (used for
	 * test)
	 * 
	 * @return the BufferedImage of the matrix
	 */
	public BufferedImage toImage() {
		BufferedImage image = new BufferedImage(this.getWidth(),
				this.getHeight(), BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < this.getWidth(); i++)
			for (int j = 0; j < this.getHeight(); j++)
				image.setRGB(i, j, matrix[i][j]);
		return image;
	}

	/**
	 * This method returns the submatrix given the coordinates
	 * 
	 * @param rowStart
	 *            initial row index
	 * @param rowEnd
	 *            final row index
	 * @param colStart
	 *            initial column index
	 * @param colEnd
	 *            final column index
	 * @return 2d array of int representing the submatrix
	 */
	public Matrix subMatrix(int rowStart, int rowEnd, int colStart, int colEnd) {
		// Row size of the submatrix
		int rowSize = rowEnd - rowStart + 1;
		// Column size of the submatrix
		int colSize = colEnd - colStart + 1;
		// Allocate the submatrix, bigger since we need the edge just for
		// computing median filter
		Matrix subMatrix = new Matrix(colSize + 2, colSize + 2);
		subMatrix.rowStart = rowStart;
		subMatrix.rowEnd = rowEnd;
		subMatrix.colStart = colStart;
		subMatrix.colEnd = colEnd;
		// for each pixel of the extended submatrix
		if (rowStart == 0) {
			for (int i = 1; i < rowSize + 1; i++) {
				subMatrix.matrix[i][0] = matrix[i - 1][0];
			}
		}
		if (rowEnd == matrix[0].length - 1) {
			for (int i = 1; i < rowSize + 1; i++) {
				subMatrix.matrix[i][subMatrix.matrix[0].length - 1] = matrix[i - 1][matrix[0].length - 1];
			}
		}
		if (colStart == 0) {
			for (int i = 1; i < colSize + 1; i++) {
				subMatrix.matrix[0][i] = matrix[0][i - 1];
			}
		}
		if (colEnd == matrix.length - 1) {
			for (int i = 1; i < colSize + 1; i++) {
				subMatrix.matrix[subMatrix.matrix.length - 1][i] = matrix[matrix.length - 1][i - 1];
			}
		}
		for (int i = 1; i < colSize + 1; i++) {
			for (int j = 1; j < rowSize + 1; j++) {
				subMatrix.matrix[i][j] = matrix[i + colStart - 1][j + rowStart
						- 1];
			}

		}
		return subMatrix;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				sb.append(matrix[i][j]);
			}
			sb.append('\n');
		}
		return sb.toString();
	}

	public Matrix add(Matrix b) {
//long time = System.currentTimeMillis();
		for (int i = b.colStart; i < b.colEnd + 1; i++) {
			for (int j = b.rowStart; j < b.rowEnd + 1; j++) {
				this.matrix[i][j] = b.matrix[i % (b.matrix.length - 2) + 1][j
						% (b.matrix[0].length - 2) + 1];
			}
		}
//		System.out.println("Reduced over in: "
//				+ (System.currentTimeMillis() - time));
		return this;
	}
}
