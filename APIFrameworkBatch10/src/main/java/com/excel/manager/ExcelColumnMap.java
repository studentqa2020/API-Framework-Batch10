package com.excel.manager;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelColumnMap {

	public static Map<String, String> columnValueMap(String path) throws Throwable {

		FileInputStream file = new FileInputStream(path);
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Map<String, String> mymap = new LinkedHashMap<>();
		int RowCount = sheet.getPhysicalNumberOfRows();

		// System.out.println("Total Rows Number::"+ RowCount);
		for (int rowindex = 1; rowindex < RowCount; rowindex++) {
			
			Row headerRow = sheet.getRow(0);
			Row row = sheet.getRow(rowindex);
			// System.out.println("Total Column Number::"+ row.getPhysicalNumberOfCells());
			if (row != null) {
				for (int colmindex = 0; colmindex < row.getLastCellNum(); colmindex++) {

					Cell cell = row.getCell(colmindex);
					if (cell != null) {
						DataFormatter df = new DataFormatter();

						
						mymap.put(df.formatCellValue(row.getCell(0)), 
								df.formatCellValue(row.getCell(1))
										+"_"+df.formatCellValue(row.getCell(2)));
					}
				}
			}
		}
		return mymap;

	}

	public static void main(String[] args) throws Throwable {

		Map<String, String> mymap = ExcelColumnMap.columnValueMap("./Test Data/Test Data Financial.xlsx");

		System.out.println(mymap.entrySet());//whole map ==Key :: value
		System.out.println(mymap.keySet());//key
		System.out.println(mymap.values());//value

		// Map<String,String>
		// testData=ExcelColumnMap.columnValueMap("./TestData/ConfigData.xlsx");

//		System.out.println(mymap.get("Apple"));
//		System.out.println(mymap.get("Google"));
//		System.out.println(mymap.get("Microsoft"));

	}

}
