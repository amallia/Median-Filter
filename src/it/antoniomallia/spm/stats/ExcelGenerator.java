package it.antoniomallia.spm.stats;

import it.antoniomallia.spm.stats.Experiment.ExperimentType;

import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Class used to generate an Excel report
 * 
 * @author antonio
 *
 */
public class ExcelGenerator {

	private static Workbook wb;

	private static NumberFormat numberFormat = NumberFormat
			.getInstance(Locale.ITALY);

	private static final int ssize = (int) Stats.getInstance().getTests()
			.stream().map(e -> e.getStreamsize()).distinct().count();

	/**
	 * Init the workbook
	 */
	public static void init() {
		wb = new XSSFWorkbook();
		numberFormat.setMaximumFractionDigits(2);
	}

	/**
	 * Write the file on disk
	 * 
	 * @throws Exception write file exception
	 */
	public static void writeFile() throws Exception {
		String file = "performance.xls";
		if (wb instanceof XSSFWorkbook)
			file += "x";
		FileOutputStream out = new FileOutputStream(file);
		wb.write(out);
		out.close();
	}

	/**
	 * Method used to generate an Excel file with the execution stats
	 * 
	 * @throws Exception write file exception
	 */
	public static void generate() throws Exception {
		init();
		// For each type of experiment, Sequenatil excluded
		for (ExperimentType type : Stats.getInstance().getTests().stream()
				.filter(e -> !e.getType().equals(ExperimentType.SEQUENTIAL))
				.map(e -> e.getType()).distinct()
				.collect(Collectors.toCollection(ArrayList::new))) {
			// Create a new workbook sheet
			Sheet sheet = wb.createSheet(type.getTitle());
			int rowPos = 0;
			// Create header row
			Row headerRow = sheet.createRow(rowPos);
			headerRow.createCell(0).setCellValue("Execution Time");
			headerRow.createCell((1 * (ssize + 2))).setCellValue("Speedup");
			headerRow.createCell((2 * (ssize + 2))).setCellValue("Scalability");
			headerRow.createCell((3 * (ssize + 2))).setCellValue("Efficiency");
			rowPos++;
			// for each stream size
			for (int streamsize : Stats.getInstance().getTests().stream()
					.map(e -> e.getStreamsize()).distinct()
					.collect(Collectors.toCollection(ArrayList::new))) {
				headerRow = sheet.createRow(rowPos);
				// write the size of the stream
				for (int i = 0; i < 4; i++) {
					headerRow.createCell(i * (ssize + 2)).setCellValue(
							"Stream size: " + streamsize);
				}
				int cellPos = 1;
				// for each size of the matrix
				for (int i : Stats.getInstance().getTests().stream()
						.map(e -> e.getSizerow()).distinct()
						.collect(Collectors.toCollection(ArrayList::new))) {
					// write the size of the matrix
					for (int j = 0; j < 4; j++) {
						headerRow.createCell((cellPos + j * (ssize + 2)))
								.setCellValue(i);
					}
					cellPos++;
				}
				// for each thread number
				for (int t : Stats.getInstance().getTests().stream()
						.map(e -> e.getThread()).distinct()
						.collect(Collectors.toCollection(ArrayList::new))) {
					rowPos++;
					// write thread number
					Row row = sheet.createRow(rowPos);
					for (int i = 0; i < 4; i++) {
						if (t > 0) {
							row.createCell((i * (ssize + 2))).setCellValue(t);
						} else {
							row.createCell((i * (ssize + 2))).setCellValue(
									"seq");
						}
					}
					cellPos = 1;
					// for each size of the matrix
					for (int sizeRow : Stats.getInstance().getTests().stream()
							.map(e -> e.getSizerow()).distinct()
							.collect(Collectors.toCollection(ArrayList::new))) {
						// Get the execution time of the sequential test
						long seqTime = Stats
								.getInstance()
								.getTests()
								.stream()
								.filter(e -> e.getType().equals(
										ExperimentType.SEQUENTIAL)
										&& e.getStreamsize() == streamsize
										&& e.getSizerow() == sizeRow)
								.map(e -> e.getTime()).findFirst().get();
						// Get the execution time of the test with one thread
						long singleThread = Stats
								.getInstance()
								.getTests()
								.stream()
								.filter(e -> e.getType().equals(type)
										&& e.getStreamsize() == streamsize
										&& e.getSizerow() == sizeRow
										&& e.getThread() == 1)
								.map(e -> e.getTime()).findFirst().get();
						// Get the experiment execution time and write it
						Experiment exp = Stats
								.getInstance()
								.getTests()
								.stream()
								.filter(e -> (e.getType().equals(type) || e
										.getType().equals(
												ExperimentType.SEQUENTIAL))
										&& e.getThread() == t
										&& e.getStreamsize() == streamsize
										&& e.getSizerow() == sizeRow)
								.findFirst().get();
						row.createCell(cellPos).setCellValue(exp.getTime());
						// Exclude sequential for the speedup parameter and
						// write it
						if (!exp.getType().equals(ExperimentType.SEQUENTIAL)) {
							row.createCell(cellPos + 1 * (ssize + 2))
									.setCellValue(
											numberFormat
													.format((double) seqTime
															/ exp.getTime()));
						}
						// Exclude single thread for the scalability/efficiency
						// and write it
						if (exp.getThread() > 1) {
							row.createCell(cellPos + 2 * (ssize + 2))
									.setCellValue(
											numberFormat
													.format((double) singleThread
															/ exp.getTime()));
							row.createCell(cellPos + 3 * (ssize + 2))
									.setCellValue(
											numberFormat
													.format((double) seqTime
															/ (exp.getThread() * exp
																	.getTime())));
						}
						cellPos++;
					}
					cellPos++;
				}
				rowPos += 2;
			}
			sheet.autoSizeColumn(0);
		}
		// Write the file on disk
		writeFile();
	}
}
