package it.antoniomallia.spm.stats;

import it.antoniomallia.spm.stats.Experiment.ExperimentType;

import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelGenerator {

	private static Workbook wb;

	private static NumberFormat numberFormat = NumberFormat
			.getInstance(Locale.ITALY);

	public static void generate() throws Exception {
		wb = new XSSFWorkbook();
		numberFormat.setMaximumFractionDigits(2);
		for (ExperimentType type : Stats.getInstance().getTests().stream()
				.filter(e -> !e.getType().equals(ExperimentType.SEQUENTIAL))
				.map(e -> e.getType()).distinct()
				.collect(Collectors.toCollection(ArrayList::new))) {

			Sheet sheet = wb.createSheet(type.getTitle());
			int rowPos = 0;
			int ssize = (int) Stats.getInstance().getTests()
					.stream()
					.filter(t -> t.getType().equals(
							ExperimentType.SKANDIUM_MAPEDUCE))
					.map(e -> e.getStreamsize()).distinct().count();
			Row headerRow = sheet.createRow(rowPos);
			headerRow.createCell(0).setCellValue("Execution Time");
			headerRow.createCell((1 * (ssize + 1))).setCellValue("Speedup");
			headerRow.createCell((2 * (ssize + 1))).setCellValue("Scalability");
			headerRow.createCell((3 * (ssize + 1))).setCellValue("Efficiency");
			rowPos++;
			for (int streamsize : Stats.getInstance().getTests()
					.stream()
					.filter(t -> t.getType().equals(
							ExperimentType.SKANDIUM_MAPEDUCE))
					.map(e -> e.getStreamsize()).distinct()
					.collect(Collectors.toCollection(ArrayList::new))) {
				headerRow = sheet.createRow(rowPos);
				Cell cell = headerRow.createCell(0);
				cell.setCellValue("Stream size: " + streamsize);
				int cellPos = 1;
				for (int i : Stats.getInstance().getTests().stream()
						.map(e -> e.getSizerow()).distinct()
						.collect(Collectors.toCollection(ArrayList::new))) {
					headerRow.createCell(cellPos).setCellValue(i);
					headerRow.createCell((cellPos + 1 * (ssize + 1)))
							.setCellValue(i);
					headerRow.createCell((cellPos + 2 * (ssize + 1)))
							.setCellValue(i);
					headerRow.createCell((cellPos + 3 * (ssize + 1)))
							.setCellValue(i);

					cellPos++;

				}
				for (int t : Stats.getInstance().getTests().stream()
						.map(e -> e.getThread()).distinct()
						.collect(Collectors.toCollection(ArrayList::new))) {
					rowPos++;
					Row row = sheet.createRow(rowPos);
					if (t > 0)
						row.createCell(0).setCellValue(t);
					else
						row.createCell(0).setCellValue("seq");
					cellPos = 1;
					for (Experiment exp : Stats.getInstance().getTests()
							.stream()
							.filter(e -> (e.getType().equals(type) || e
									.getType()
									.equals(ExperimentType.SEQUENTIAL))
									&& e.getThread() == t
									&& e.getStreamsize() == streamsize)
							.collect(Collectors.toCollection(ArrayList::new))) {
						row.createCell(cellPos).setCellValue(exp.getTime());
						cellPos++;
					}

					cellPos++;
					long seqTime = Stats.getInstance().getTests()
							.stream()
							.filter(e -> e.getType().equals(
									ExperimentType.SEQUENTIAL)
									&& e.getStreamsize() == streamsize)
							.map(e -> e.getTime()).findFirst().get();
					for (Experiment exp : Stats.getInstance().getTests()
							.stream()
							.filter(e -> e.getType().equals(type)
									&& e.getThread() == t
									&& e.getStreamsize() == streamsize)
							.collect(Collectors.toCollection(ArrayList::new))) {
						Cell speedup = row.createCell(cellPos);
						speedup.setCellValue(numberFormat
								.format((double) seqTime / exp.getTime()));

						cellPos++;
					}
					cellPos++;
					long singleThread = Stats.getInstance().getTests()
							.stream()
							.filter(e -> e.getType().equals(type)
									&& e.getStreamsize() == streamsize
									&& e.getThread() == 1)
							.map(e -> e.getTime()).findFirst().get();
					for (Experiment exp : Stats.getInstance().getTests()
							.stream()
							.filter(e -> e.getType().equals(type)
									&& e.getThread() == t && e.getThread() > 1
									&& e.getStreamsize() == streamsize)
							.collect(Collectors.toCollection(ArrayList::new))) {
						Cell speedup = row.createCell(cellPos);
						speedup.setCellValue(numberFormat
								.format((double) singleThread / exp.getTime()));

						cellPos++;
					}

				}
				rowPos += 2;
			}

			sheet.autoSizeColumn(0);
		}
		String file = "performance.xls";
		if (wb instanceof XSSFWorkbook)
			file += "x";
		FileOutputStream out = new FileOutputStream(file);
		wb.write(out);
		out.close();
	}
}
