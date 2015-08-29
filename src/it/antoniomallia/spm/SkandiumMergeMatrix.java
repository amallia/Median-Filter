package it.antoniomallia.spm;

import cl.niclabs.skandium.muscles.Merge;

/**
 * Merge Matrix Class implementing Skandium Merge
 * @author antoniomallia
 *
 */
public class SkandiumMergeMatrix implements Merge<Matrix, Matrix> {

	/* (non-Javadoc)
	 * @see cl.niclabs.skandium.muscles.Merge#merge(java.lang.Object[])
	 */
	@Override
	public Matrix merge(Matrix[] m)  {
		return new MergeMatrix().merge(m);
	}
}
