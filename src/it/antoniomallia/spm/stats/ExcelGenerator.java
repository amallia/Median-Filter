package it.antoniomallia.spm.stats;

import it.antoniomallia.spm.stats.Experiment.Type;

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

	static NumberFormat numberFormat = NumberFormat.getInstance(Locale.ITALY);

	public static void generate() throws Exception {
		wb = new XSSFWorkbook();
		numberFormat.setMaximumFractionDigits(2);
		for (Type type : Stats.getInstance().tests.stream()
				.filter(e -> !e.type.equals(Type.SEQUENTIAL)).map(e -> e.type)
				.distinct().collect(Collectors.toCollection(ArrayList::new))) {

			Sheet sheet = wb.createSheet(type.getTitle());
			int rowPos = 0;
			long ssize = Stats.getInstance().tests.stream()
					.filter(t -> t.type.equals(Type.SKANDIUM_MAPEDUCE))
					.map(e -> e.streamsize).distinct().count();
			Row headerRow = sheet.createRow(rowPos);
			headerRow.createCell(0).setCellValue("Execution Time");
			headerRow.createCell((int) (1 * ssize + 1)).setCellValue("Speedup");
			headerRow.createCell((int) (2 * ssize + 1)).setCellValue(
					"Efficency");
			rowPos++;
			for (int streamsize : Stats.getInstance().tests.stream()
					.filter(t -> t.type.equals(Type.SKANDIUM_MAPEDUCE))
					.map(e -> e.streamsize).distinct()
					.collect(Collectors.toCollection(ArrayList::new))) {
				headerRow = sheet.createRow(rowPos);
				Cell cell = headerRow.createCell(0);
				cell.setCellValue("Stream size: " + streamsize);
				int cellPos = 1;
				for (int i : Stats.getInstance().tests.stream()
						.map(e -> e.sizerow).distinct()
						.collect(Collectors.toCollection(ArrayList::new))) {
					headerRow.createCell(cellPos).setCellValue(i);
					cellPos++;

				}
				for (int t : Stats.getInstance().tests.stream()
						.map(e -> e.thread).distinct()
						.collect(Collectors.toCollection(ArrayList::new))) {
					rowPos++;
					Row row = sheet.createRow(rowPos);
					if (t > 0)
						row.createCell(0).setCellValue(t);
					else
						row.createCell(0).setCellValue("seq");
					cellPos = 1;
					for (Experiment exp : Stats.getInstance().tests
							.stream()
							.filter(e -> (e.type.equals(type) || e.type
									.equals(Type.SEQUENTIAL))
									&& e.thread == t
									&& e.streamsize == streamsize)
							.collect(Collectors.toCollection(ArrayList::new))) {
						row.createCell(cellPos).setCellValue(exp.time);
						cellPos++;
					}

					cellPos++;
					long seqTime = Stats.getInstance().tests
							.stream()
							.filter(e -> e.type.equals(Type.SEQUENTIAL)
									&& e.streamsize == streamsize)
							.map(e -> e.time).findFirst().get();
					for (Experiment exp : Stats.getInstance().tests
							.stream()
							.filter(e -> e.type.equals(type) && e.thread == t
									&& e.streamsize == streamsize)
							.collect(Collectors.toCollection(ArrayList::new))) {
						Cell speedup = row.createCell(cellPos);
						speedup.setCellValue(numberFormat
								.format((double) seqTime / exp.time));

						cellPos++;
					}
					cellPos++;
					long singleThread = Stats.getInstance().tests
							.stream()
							.filter(e -> e.type.equals(type)
									&& e.streamsize == streamsize
									&& e.thread == 1).map(e -> e.time)
							.findFirst().get();
					for (Experiment exp : Stats.getInstance().tests
							.stream()
							.filter(e -> e.type.equals(type) && e.thread == t
									&& e.thread > 1
									&& e.streamsize == streamsize)
							.collect(Collectors.toCollection(ArrayList::new))) {
						Cell speedup = row.createCell(cellPos);
						speedup.setCellValue(numberFormat
								.format((double) singleThread / exp.time));

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
