package it.antoniomallia.spm;


public class Sequential {
	
	
	public static Matrix compute(Matrix m) {
		return new ExecuteFilter().execute(m);
	}

}
