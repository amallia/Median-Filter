package it.antoniomallia.spm.test;

import it.antoniomallia.spm.stats.ExcelGenerator;
import it.antoniomallia.spm.stats.Experiment.ExperimentType;
import it.antoniomallia.spm.stats.Stats;

import java.util.Arrays;
import java.util.stream.IntStream;

import lombok.extern.log4j.Log4j2;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Main class used to execute a complete test
 * 
 * @author antonio
 *
 */
@Log4j2
public class TestExec {
	private static final String headerMsg = " __  __          _ _             _____ _ _ _\r\n|  \\/  | ___  __| (_) __ _ _ __ |  ___(_) | |_ ___ _ __\r\n| |\\/| |/ _ \\/ _` | |/ _` | '_ \\| |_  | | | __/ _ \\ '__|\r\n| |  | |  __/ (_| | | (_| | | | |  _| | | | ||  __/ |\r\n|_|  |_|\\___|\\__,_|_|\\__,_|_| |_|_|   |_|_|\\__\\___|_| ";
	private static final String testStartedMsg = "Performance Test started";
	private static HelpFormatter formatter = new HelpFormatter();
	private static CommandLineParser parser = new DefaultParser();
	private static Options options = new Options();

	public static void commandLineInit() {
		options.addOption("h", "help", false, "print help message");
		options.addOption(Option.builder("t").argName("4").longOpt("threads")
				.desc("threads number").hasArg().required().build());
		options.addOption(Option.builder("s").argName("1,10,50...")
				.longOpt("stream").desc("number of matrices to test").hasArgs()
				.valueSeparator(',').required().build());
		options.addOption(Option.builder("d").argName("200,400,1000...")
				.longOpt("dim").desc("matrix dimension in pixel").hasArgs()
				.valueSeparator(',').required().build());
	}

	/**
	 * Main method called to execute a compleate predefined test
	 * 
	 * @param args
	 *            args passed to main method
	 * @throws Exception
	 *             Execution exception
	 */
	public static void main(String[] args) throws Exception {
		// Print the ASCII message
		System.out.println(headerMsg);
		// CommandLine init
		commandLineInit();
		try {
			// parse the command line arguments
			CommandLine line = parser.parse(options, args);
			if (line.hasOption("t") && line.hasOption("s")
					&& line.hasOption("d")) {
				int[] threads = IntStream.rangeClosed(1,
						Integer.parseInt(line.getOptionValue("t"))).toArray();
				Integer[] sizestreams = Arrays
						.stream(line.getOptionValues("s"))
						.map(a -> Integer.parseInt(a))
						.toArray(size -> new Integer[size]);
				Integer[] sizeRows = Arrays.stream(line.getOptionValues("d"))
						.map(a -> Integer.parseInt(a))
						.toArray(size -> new Integer[size]);
				// Start the test
				log.info(testStartedMsg);
				for (Integer sizestream : sizestreams) {
					for (Integer sizerow : sizeRows) {
						// SEQUENTIAL TEST
						try {
							SequentialTest seqTest = new SequentialTest();
							seqTest.testcompute(sizestream, sizerow, sizerow);
							Stats.getInstance()
									.getTests()
									.add(seqTest.testcompute(sizestream,
											sizerow, sizerow));
						} catch (OutOfMemoryError e) {
							log.info(String
									.format("OutOfMemoryError => Type: %s	 Thread number: %s	 Matrices number: %s	 Matrix size: %s x %s",
											ExperimentType.SEQUENTIAL
													.getTitle(), "0",
											sizestream, sizerow, sizerow));
						}
						for (Integer thread : threads) {
							// SKANDIUM MAP TEST
							try {
								SkandiumMapTest skandiumMapTest = new SkandiumMapTest(
										thread);
								skandiumMapTest.testcompute(sizestream,
										sizerow, sizerow);
								Stats.getInstance()
										.getTests()
										.add(skandiumMapTest.testcompute(
												sizestream, sizerow, sizerow));
							} catch (OutOfMemoryError e) {
								log.info(String
										.format("OutOfMemoryError => Type: %s	 Thread number: %s	 Matrices number: %s	 Matrix size: %s x %s",
												ExperimentType.SKANDIUM_MAP
														.getTitle(), thread,
												sizestream, sizerow, sizerow));
							}
							// SKANDIUM FARM TEST
							try {

								SkandiumFarmTest farmTest = new SkandiumFarmTest(
										thread);
								farmTest.testcompute(sizestream, sizerow,
										sizerow);
								Stats.getInstance()
										.getTests()
										.add(farmTest.testcompute(sizestream,
												sizerow, sizerow));
							} catch (OutOfMemoryError e) {
								log.info(String
										.format("OutOfMemoryError => Type: %s	 Thread number: %s	 Matrices number: %s	 Matrix size: %s x %s",
												ExperimentType.SKANDIUM_FARM
														.getTitle(), thread,
												sizestream, sizerow, sizerow));
							}
							// J8 MAP TEST
							try {

								J8MapTest j8MapTest = new J8MapTest(thread);
								j8MapTest.testcompute(sizestream, sizerow,
										sizerow);
								Stats.getInstance()
										.getTests()
										.add(j8MapTest.testcompute(sizestream,
												sizerow, sizerow));
							} catch (OutOfMemoryError e) {
								log.info(String
										.format("OutOfMemoryError => Type: %s	 Thread number: %s	 Matrices number: %s	 Matrix size: %s x %s",
												ExperimentType.J8_MAP
														.getTitle(), thread,
												sizestream, sizerow, sizerow));
							}
							// J8 FARM TEST
							try {

								J8FarmTest j8farmTest = new J8FarmTest(thread);
								j8farmTest.testcompute(sizestream, sizerow,
										sizerow);
								Stats.getInstance()
										.getTests()
										.add(j8farmTest.testcompute(sizestream,
												sizerow, sizerow));
							} catch (OutOfMemoryError e) {
								log.info(String
										.format("OutOfMemoryError => Type: %s	 Thread number: %s	 Matrices number: %s	 Matrix size: %s x %s",
												ExperimentType.J8_FARM
														.getTitle(), thread,
												sizestream, sizerow, sizerow));
							}
						}
					}
				}
				ExcelGenerator.generate();
			}
			// Print the help
			if (line.hasOption("h")) {
				formatter.printHelp("MedianFilter", options, true);

			}
			// Parse exception ,print the help
		} catch (ParseException exp) {
			formatter.printHelp("MedianFilter", options, true);
		}
		System.exit(0);
	}
}
