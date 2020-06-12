package com.urbanladder.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	public static String TESTDATA_SHEET_PATH = ".\\src\\test\\java\\com\\urbanladder\\testData\\testData.xlsx";
	public static Workbook book;
	public static Sheet sheet;
	
	public static Object[][] getTestData(String sheetName) {

		FileInputStream inStream = null;
		try {
			inStream = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(inStream);
		} catch (EncryptedDocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);

		Object data[][] = new Object[sheet.getLastRowNum()][sheet.getRow(0)
				.getLastCellNum()];

		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {

				Cell cell = sheet.getRow(i + 1).getCell(k);
				if (cell == null) {
					data[i][k] = "";
				} else {
					switch (cell.getCellType()) {
					case STRING:
						data[i][k] = cell.getStringCellValue();
						break;
					case NUMERIC:
						data[i][k] = cell.getNumericCellValue();
						break;
					case BOOLEAN:
						data[i][k] = cell.getBooleanCellValue();
						break;
					default:
						data[i][k] = "";
						break;
					}
				}
			}
		}
		return data;
	}

	public static void writeExcel(Map<String, Object[]> data, String fileName) {

		try {

			FileInputStream inStream = new FileInputStream(new File(
					"./src/test/java/com/urbanladder/testData/"+ fileName +".xlsx"));
			book = new XSSFWorkbook(inStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			book = new XSSFWorkbook();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sheet = book.createSheet("sheet_" + System.currentTimeMillis());
		Row row;

		Set<String> keySet = data.keySet();
		int rowid = 0;

		for (String key : keySet) {
			row = sheet.createRow(rowid++);
			Object[] objectArray = data.get(key);
			int cellid = 0;
			
			for (Object object : objectArray) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue(object + "");
			}
		}
		
	
		FileOutputStream outStream;
		try {
			outStream = new FileOutputStream(
					new File(
							"./src/test/java/com/urbanladder/testData/"+ fileName +".xlsx"));
			book.write(outStream);
			outStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
