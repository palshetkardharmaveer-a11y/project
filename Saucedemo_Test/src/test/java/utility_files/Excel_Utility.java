package utility_files;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel_Utility {
	public static String getCellData(String path, String sheet, int row, int col) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		DataFormatter formatter = new DataFormatter();
		String data = formatter.formatCellValue(workbook.getSheet(sheet).getRow(row).getCell(col));
		workbook.close();
		return data;
		
	}

}
