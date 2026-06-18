package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {

    public static Object[][] getTableArray(String filePath, String sheetName) throws InterruptedException {
        Object[][] data = null;
        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet name '" + sheetName + "' not found in Excel file.");
            }

            int totalRows = sheet.getLastRowNum(); 
            int totalCols = sheet.getRow(0).getLastCellNum(); 

            data = new Object[totalRows][totalCols];
 for (int i = 1; i <= totalRows; i++) {
                Row row = sheet.getRow(i);
                Thread.sleep(300); // pause per row read

                for (int j = 0; j < totalCols; j++) {
                    if (row == null) {
                        data[i - 1][j] = "";
                        continue;
                    }
                    Cell cell = row.getCell(j);
                    data[i - 1][j] = getCellValueAsString(cell);
                    Thread.sleep(200); // pause per cell read
                }
            }

            Thread.sleep(500); // pause after reading sheet
 } catch (IOException e) {
            System.out.println("Excel file reading failed: " + e.getMessage());
        }
        return data;
    }
 private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        CellType cellType = cell.getCellType();
        if (cellType == CellType.STRING) {
            return cell.getStringCellValue().trim();
        } else if (cellType == CellType.NUMERIC) {
            DataFormatter formatter = new DataFormatter();
            return formatter.formatCellValue(cell).trim();
        } else if (cellType == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else {
            return "";
        }
    }
}
