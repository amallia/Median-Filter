package it.antoniomallia.spm;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import lombok.Getter;
import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.skeletons.Farm;

public class SkandiumFarm {

	@Getter
	private Stream<Matrix, Matrix> stream;

	/** Oggetto skandium del framework */
	private Skandium skandium;

	
	public SkandiumFarm(int threads) {
		skandium = new Skandium(threads);
		Farm<Matrix, Matrix> root = new Farm<Matrix, Matrix>(
				new SkandiumExecuteFilter());
		stream = skandium.newStream(root);
	}


	public Matrix compute(Matrix m) throws InterruptedException,
			ExecutionException {
		Future<Matrix> result = stream.input(m);
		return result.get();
	}

	public void shutdown() {
		skandium.shutdown();
	}

}
