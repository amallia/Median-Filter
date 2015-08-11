package it.antoniomallia.spm;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Test {

	public static void main(String[] args) throws IOException,
			InterruptedException, ExecutionException {

		int SIZE_ROW;
		int SIZE_COL;
		int SIZE_STREAM;
		int THREADS;

		try {
			if (args.length > 0 && args.length <= 5) {

				if (args[0].equals("mapreduce")) {
					THREADS = Integer.parseInt(args[1]);
					SIZE_STREAM = Integer.parseInt(args[2]);
					SIZE_ROW = Integer.parseInt(args[3]);
					SIZE_COL = Integer.parseInt(args[4]);
					MapReduce mapreduce = new MapReduce(THREADS);
					mapreduce.testcompute(SIZE_STREAM, SIZE_ROW, SIZE_COL);
					mapreduce.shutdown();
				}
				if (args[0].equals("farm")) {
					THREADS = Integer.parseInt(args[1]);
					SIZE_STREAM = Integer.parseInt(args[2]);
					SIZE_ROW = Integer.parseInt(args[3]);
					SIZE_COL = Integer.parseInt(args[4]);
					SkandiumFarm farm = new SkandiumFarm(THREADS);
					farm.testcompute(SIZE_STREAM, SIZE_ROW, SIZE_COL);
					farm.shutdown();
				}
				// if (args[0].equals("seq")) {
				// SIZE_STREAM = Integer.parseInt(args[1]);
				// SIZE_ROW = Integer.parseInt(args[2]);
				// SIZE_COL = Integer.parseInt(args[3]);
				// Sequential.testcompute(SIZE_STREAM, SIZE_ROW, SIZE_COL);
				// }
				// if (args[0].equals("quadpar")) {
				// THREADS = Integer.parseInt(args[1]);
				// SIZE_STREAM = Integer.parseInt(args[2]);
				// SIZE_ROW = Integer.parseInt(args[3]);
				// SIZE_COL = Integer.parseInt(args[4]);
				// ParallelQuad parmap = new ParallelQuad(THREADS);
				// parmap.testcompute(SIZE_STREAM, SIZE_ROW, SIZE_COL);
				// parmap.shutdown();
				// }
				// if (args[0].equals("quadseq")) {
				// SIZE_STREAM = Integer.parseInt(args[1]);
				// SIZE_ROW = Integer.parseInt(args[2]);
				// SIZE_COL = Integer.parseInt(args[3]);
				// SequentialQuad.testcompute(SIZE_STREAM, SIZE_ROW, SIZE_COL);
				// }
			} else {
				System.err.println("Error: Parameter missing");
				System.exit(3);
			}
		} catch (OutOfMemoryError e) {
			System.err.println("RUNNING OUT OF MEMORY!");
			System.exit(2);
		}

		System.exit(0);

	}

}
