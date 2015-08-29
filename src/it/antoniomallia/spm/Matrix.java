package it.antoniomallia.spm;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

import lombok.Getter;

/**
 * Matrix Class used to wrap the image and containing useful methods
 * 
 * @author antoniomallia
 *
 */
public class Matrix {

	@Getter
	private int[][] matrix;

	/**
	 * @return matrix height
	 */
	public int getHeight() {
		return matrix.length;
	}

	/**
	 * @return matrix width
	 */
	public int getWidth() {
		return matrix[0].length;
	}

	/**
	 * Constructor from an array of RGB integers
	 * 
	 * @param matrix
	 *            array of int
	 */
	public Matrix(int[][] matrix) {
		this.matrix = matrix;
	}

	/**
	 * Constructor of an blank matrix
	 * 
	 * @param sizeRow
	 *            width
	 * @param sizeCol
	 *            height
	 */
	public Matrix(int sizeRow, int sizeCol) {
		matrix = new int[sizeCol][sizeRow];
	}

	/**
	 * Fill the matrix with random values
	 * 
	 * @return the matrix generated
	 */
	public Matrix randomValues() {
		Random rand = new Random();
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				matrix[i][j] = new Color(rand.nextFloat(), rand.nextFloat(),
						rand.nextFloat()).getRGB();
			}
		}
		return this;
	}
	
	public void augment(){
		this.matrix=subMatrix(0, getWidth()-1, 0, getHeight()-1).matrix;
	}
	public void diminish(){
		int[][] diminished = new int[getHeight()-2][getWidth()-2];
		for (int i = 1; i < getHeight()-1; i++) {
			for (int j = 1; j < getWidth()-1; j++) {
				diminished[i-1][j-1]=matrix[i][j];
			}
		}
		this.matrix=diminished;
	}
	
	/**
	 * This method applies the median filter algorithm to the current matrix
	 */
	public void medianFilter() {
		//TODO check again
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
		for (int i = edge; i < this.getHeight()  - edge; i++) {
			for (int j = edge; j < this.getWidth()- edge; j++) {
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
		for (int i = 0; i < this.getHeight(); i++)
			for (int j = 0; j < this.getWidth(); j++)
				image.setRGB(j, i, matrix[i][j]);
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
	 * @return submatrix
	 */
	public Matrix subMatrix(int rowStart, int rowEnd, int colStart, int colEnd) {
		// Row size of the submatrix
		int rowSize = rowEnd - rowStart + 1;
		// Column size of the submatrix
		int colSize = colEnd - colStart + 1;
		// Allocate the submatrix, bigger since we need the edge just for
		// computing median filter
		Matrix subMatrix = new Matrix(rowSize + 2, colSize + 2);
		for (int i = 0; i < colSize + 2; i++) {
			for (int j = 0; j < rowSize + 2; j++) {
				if ((i + colStart - 1) < 0
						|| (i + colStart - 1) > (getHeight() - 1)
						|| (j + rowStart - 1) < 0
						|| (j + rowStart - 1) > (getWidth() - 1)) {
					if ((i + colStart - 1) > (getHeight() - 1)) {
					}
					int colV = (i + colStart - 1) < 0 ? (i + colStart)
							: (i + colStart - 1) > (getHeight() - 1) ? (i
									+ colStart - 2) : i + colStart - 1;
					int rowV = (j + rowStart - 1) < 0 ? (j + rowStart)
							: (j + rowStart - 1) > (getWidth() - 1) ? (j
									+ rowStart - 2) : j + rowStart - 1;
					subMatrix.matrix[i][j] = matrix[colV][rowV];
				} else {
					subMatrix.matrix[i][j] = matrix[i + colStart - 1][j
							+ rowStart - 1];
				}
			}
		}
		return subMatrix;
	}
}
