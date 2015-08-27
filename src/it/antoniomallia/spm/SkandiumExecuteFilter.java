package it.antoniomallia.spm;

import cl.niclabs.skandium.muscles.Execute;

/**
 * Class which implements the Skandium Execute interface
 * @author antoniomallia
 *
 */
public class SkandiumExecuteFilter implements Execute<Matrix, Matrix> {

	/* (non-Javadoc)
	 * @see cl.niclabs.skandium.muscles.Execute#execute(java.lang.Object)
	 */
	@Override
	public Matrix execute(Matrix matrix) throws Exception {
		return new ExecuteFilter().execute(matrix);
	}

}
