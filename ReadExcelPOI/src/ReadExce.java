
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadExce {
	public static void main(String[] args) {
		// Excel File Path
		String filePath = "employee_data.xlsx";

		try {
			// Create a FileInputStream to read the Excel file
			FileInputStream fileInputStream = new FileInputStream(new File(filePath));

			// Create a workbook instance (for .xlsx format, we use XSSFWorkbook)
			Workbook workbook = new XSSFWorkbook(fileInputStream);

			// Get the first sheet from the workbook
			Sheet sheet = workbook.getSheetAt(0);

			// Iterate through all the rows of the sheet
			for (Row row : sheet) {
				// Skip the first row (header)
				if (row.getRowNum() == 0)
					continue;

				// Iterate through each cell in the row
				for (Cell cell : row) {
					switch (cell.getCellType()) {
					case STRING:
						System.out.print(cell.getStringCellValue() + "\t");
						break;
					case NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							System.out.print(cell.getDateCellValue() + "\t");
						} else {
							System.out.print(cell.getNumericCellValue() + "\t");
						}
						break;
					case BOOLEAN:
						System.out.print(cell.getBooleanCellValue() + "\t");
						break;
					default:
						System.out.print("N/A\t");
					}
				}
				System.out.println();
			}

			// Close the workbook and file input stream
			workbook.close();
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
