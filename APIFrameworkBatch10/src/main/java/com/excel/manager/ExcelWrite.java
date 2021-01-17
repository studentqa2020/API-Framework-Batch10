package com.excel.manager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWrite {

	static XSSFWorkbook workbook = new XSSFWorkbook();
	CreationHelper createHelper = workbook.getCreationHelper();
	static XSSFSheet sheet = workbook.createSheet("Data");
	static String path="./Test Data/CNNExcelWrite.xlsx";
	private static void writeHeader(String[] headers, XSSFSheet sheet) {

		// Create our Headers
		AtomicInteger columnNumber = new AtomicInteger(0);
		XSSFRow headerRow = sheet.createRow(0);
		Arrays.stream(headers).forEach(hd -> {
			Cell cell = headerRow.createCell(columnNumber.getAndIncrement());
			cell.setCellValue(hd);
		});
	}

	private static void writeContent(XSSFSheet sheet,String[] header, String [] cons) {


		ExcelWrite.writeHeader(header,sheet);
		// Get our Last Updated row
		AtomicInteger rowNumber = new AtomicInteger(sheet.getLastRowNum() + 1);

		// iterate over the map
		
			// Set Our Column Number to Zero
			AtomicInteger columnNumber = new AtomicInteger(0);

			// Get our Row
			XSSFRow row = sheet.createRow(rowNumber.getAndIncrement());

			Arrays.stream(cons).forEach(hd -> {
				Cell cell = row.createCell(columnNumber.getAndIncrement());
				cell.setCellValue(hd);
			});
	
		// Resize all columns to fit the content size

				for (int i = 0; i < 3; i++) {
					sheet.autoSizeColumn(i);

				}
		try {
			// Write the output to a file
			FileOutputStream fileOut = new FileOutputStream(path);

			workbook.write(fileOut);
			System.out.println("Done");
			fileOut.close();
			// Closing the workbook
			workbook.close();
		} catch (IOException e) {
e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

		String [] header ={"Name","Age"};
		String [] data = {"Sarower","40",};
		
		ExcelWrite.writeContent(sheet,header, data);
	}
}
