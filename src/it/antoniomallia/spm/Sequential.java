package it.antoniomallia.spm;

public class Sequential {
	
	public static Matrix compute(Matrix m) {

		m.medianFilter();
		return m;
	}
}
