package it.antoniomallia.spm;

import lombok.AllArgsConstructor;
import cl.niclabs.skandium.muscles.Merge;

/**
 * Merge Matrix Class implementing Skandium Merge
 * @author antoniomallia
 *
 */
@AllArgsConstructor
public class SkandiumMergeMatrix implements Merge<Matrix, Matrix> {

	private int num;

	/* (non-Javadoc)
	 * @see cl.niclabs.skandium.muscles.Merge#merge(java.lang.Object[])
	 */
	@Override
	public Matrix merge(Matrix[] m)  {
		return new MergeMatrix(num).merge(m);
	}
}
