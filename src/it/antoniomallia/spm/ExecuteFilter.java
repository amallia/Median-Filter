package it.antoniomallia.spm;


/**
 * Executor Class
 * @author antoniomallia
 *
 */
public class ExecuteFilter {
	
	
	/**
	 * Execute the median filter 
	 * @param matrix input
	 * @return matrix after the execution of the median filter
	 */
	public Matrix execute(Matrix matrix) {
		matrix.medianFilter();
		return matrix;
	}
}
