package it.antoniomallia.spm;

import lombok.AllArgsConstructor;
import cl.niclabs.skandium.muscles.Split;

/**
 * Split class implementing skandium Split
 * @author antoniomallia
 *
 */
@AllArgsConstructor
public class SkandiumSplitMatrix implements Split<Matrix, Matrix> {

	private int num;

	/* (non-Javadoc)
	 * @see cl.niclabs.skandium.muscles.Split#split(java.lang.Object)
	 */
	@Override
	public Matrix[] split(Matrix matrix) {
		return new SplitMatrix(num).split(matrix);
	}
}
