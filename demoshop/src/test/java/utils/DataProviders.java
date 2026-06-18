package utils;

import org.testng.annotations.DataProvider;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataProviders {

    @DataProvider(name = "checkoutData")
    public Object[][] getCheckoutData() throws IOException, InterruptedException {
        FileInputStream fis = new FileInputStream("src/test/resources/Book1.xlsx");
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet("Sheet3");

        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

        Object[][] data = new Object[rowCount - 1][colCount];

        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            Thread.sleep(300); // pause per row read

            for (int j = 0; j < colCount; j++) {
                Cell cell = row.getCell(j);

                if (cell == null) {
                    data[i - 1][j] = "";
                    continue;
                }

                switch (cell.getCellType()) {
                    case STRING:
                        data[i - 1][j] = cell.getStringCellValue();
                        break;
                    case NUMERIC:
                        // For numeric values like Phone or Zip, convert to String
                        data[i - 1][j] = String.valueOf((long) cell.getNumericCellValue());
                        break;
                    case BOOLEAN:
                        data[i - 1][j] = String.valueOf(cell.getBooleanCellValue());
                        break;
                    default:
                        data[i - 1][j] = "";
                }

                Thread.sleep(200); // pause per cell read
            }
        }

        workbook.close();
        fis.close();
        Thread.sleep(500); // pause after closing workbook
        return data;
    }
}
