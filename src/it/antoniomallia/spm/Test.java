package it.antoniomallia.spm;

import it.antoniomallia.spm.stats.Stats;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

public class Test {

	public static void main(String[] args) throws IOException,
			InterruptedException, ExecutionException {

		if (args.length == 3) {
			int[] threads = IntStream.rangeClosed(1, Integer.parseInt(args[0]))
					.toArray();
			Integer[] sizestreams = Arrays.stream(args[1].split(","))
					.map(a -> Integer.parseInt(a))
					.toArray(size -> new Integer[size]);
			Integer[] sizeRows = Arrays.stream(args[2].split(","))
					.map(a -> Integer.parseInt(a))
					.toArray(size -> new Integer[size]);

			for (Integer sizestream : sizestreams) {
				for (Integer thread : threads) {
					for (Integer sizerow : sizeRows) {

						SkandiumMapReduce mapreduce = new SkandiumMapReduce(
								thread);
						mapreduce.testcompute(sizestream, sizerow, sizerow);
						Stats.getInstance().SkandiumMapReduceTests.put(thread,
								mapreduce.testcompute(sizestream, sizerow,
										sizerow));
						mapreduce.shutdown();

						SkandiumFarm farm = new SkandiumFarm(thread);
						farm.testcompute(sizestream, sizerow, sizerow);
						Stats.getInstance().SkandiumFarmTests.put(thread,
								farm.testcompute(sizestream, sizerow, sizerow));
						farm.shutdown();

						J8MapReduce j8mapreduce = new J8MapReduce(thread);
						j8mapreduce.testcompute(sizestream, sizerow, sizerow);
						Stats.getInstance().J8MapReduceTests.put(thread,
								j8mapreduce.testcompute(sizestream, sizerow,
										sizerow));

						J8Farm j8farm = new J8Farm(thread);
						j8farm.testcompute(sizestream, sizerow, sizerow);
						Stats.getInstance().J8FarmTests.put(thread, j8farm
								.testcompute(sizestream, sizerow, sizerow));
					}
				}
			}

		} else {
			System.err.println("Error: Parameter missing");
			System.exit(3);
		}

		// try {
		// if (args.length > 0 && args.length <= 5) {
		//
		// if (args[0].equals("sequential")) {
		// SIZE_STREAM = Integer.parseInt(args[1]);
		// SIZE_ROW = Integer.parseInt(args[2]);
		// SIZE_COL = Integer.parseInt(args[3]);
		// Sequential.testcompute(SIZE_STREAM, SIZE_ROW, SIZE_COL);
		// }
		// } else {
		// System.err.println("Error: Parameter missing");
		// System.exit(3);
		// }
		// } catch (OutOfMemoryError e) {
		// System.err.println("RUNNING OUT OF MEMORY!");
		// System.exit(2);
		// }
		System.exit(0);

	}
}
