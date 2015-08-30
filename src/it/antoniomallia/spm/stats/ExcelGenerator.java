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

public class ExcelGenerator {

	private static Workbook wb;

	private static NumberFormat numberFormat = NumberFormat
			.getInstance(Locale.ITALY);

	private static final int ssize = (int) Stats.getInstance().getTests()
			.stream().map(e -> e.getStreamsize()).distinct().count();

	public static void init() {
		wb = new XSSFWorkbook();
		numberFormat.setMaximumFractionDigits(2);
	}

	public static void writeFile() throws Exception {
		String file = "performance.xls";
		if (wb instanceof XSSFWorkbook)
			file += "x";
		FileOutputStream out = new FileOutputStream(file);
		wb.write(out);
		out.close();
	}

	public static void generate() throws Exception {
		init();

		for (ExperimentType type : Stats.getInstance().getTests().stream()
				.filter(e -> !e.getType().equals(ExperimentType.SEQUENTIAL))
				.map(e -> e.getType()).distinct()
				.collect(Collectors.toCollection(ArrayList::new))) {

			Sheet sheet = wb.createSheet(type.getTitle());
			int rowPos = 0;
			Row headerRow = sheet.createRow(rowPos);
			headerRow.createCell(0).setCellValue("Execution Time");
			headerRow.createCell((1 * (ssize + 2))).setCellValue("Speedup");
			headerRow.createCell((2 * (ssize + 2))).setCellValue("Scalability");
			headerRow.createCell((3 * (ssize + 2))).setCellValue("Efficiency");
			rowPos++;
			for (int streamsize : Stats.getInstance().getTests().stream()
					.map(e -> e.getStreamsize()).distinct()
					.collect(Collectors.toCollection(ArrayList::new))) {
				headerRow = sheet.createRow(rowPos);
				for (int i = 0; i < 4; i++) {
					headerRow.createCell(i * (ssize + 2)).setCellValue(
							"Stream size: " + streamsize);
				}
				int cellPos = 1;
				for (int i : Stats.getInstance().getTests().stream()
						.map(e -> e.getSizerow()).distinct()
						.collect(Collectors.toCollection(ArrayList::new))) {
					for (int j = 0; j < 4; j++) {
						headerRow.createCell((cellPos + j * (ssize + 2)))
								.setCellValue(i);
					}
					cellPos++;
				}
				for (int t : Stats.getInstance().getTests().stream()
						.map(e -> e.getThread()).distinct()
						.collect(Collectors.toCollection(ArrayList::new))) {
					rowPos++;
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
					for (int sizeRow : Stats.getInstance().getTests().stream()
							.map(e -> e.getSizerow()).distinct()
							.collect(Collectors.toCollection(ArrayList::new))) {
						long seqTime = Stats
								.getInstance()
								.getTests()
								.stream()
								.filter(e -> e.getType().equals(
										ExperimentType.SEQUENTIAL)
										&& e.getStreamsize() == streamsize
										&& e.getSizerow() == sizeRow)
								.map(e -> e.getTime()).findFirst().get();
						long singleThread = Stats
								.getInstance()
								.getTests()
								.stream()
								.filter(e -> e.getType().equals(type)
										&& e.getStreamsize() == streamsize
										&& e.getSizerow() == sizeRow
										&& e.getThread() == 1)
								.map(e -> e.getTime()).findFirst().get();
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
						if (!exp.getType().equals(ExperimentType.SEQUENTIAL)) {
							row.createCell(cellPos + 1 * (ssize + 2))
									.setCellValue(
											numberFormat
													.format((double) seqTime
															/ exp.getTime()));
						}
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
		writeFile();
	}
}
