package it.antoniomallia.spm;


/**
 * Sequential Class
 * @author antoniomallia
 *
 */
public class Sequential {
	
	
	/**
	 * Static method to execute the algoritm sequentially
	 * @param matrix input matrix
	 * @return computed matrix
	 */
	public static Matrix compute(Matrix matrix) {
		return new ExecuteFilter().execute(matrix);
	}

}
