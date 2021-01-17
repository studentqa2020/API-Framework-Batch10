package com.excel.manager;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelColumn {

	public static List<String> columnValue(String path,int columnNumber) throws Throwable
    {
		
		FileInputStream file= new FileInputStream(path);
		XSSFWorkbook workbook=new XSSFWorkbook(file);
		XSSFSheet sheet= workbook.getSheetAt(0);
		List<String> Value= new ArrayList<String>();
		int RowCount= sheet.getPhysicalNumberOfRows();
		
		//System.out.println("Total Rows Number::"+ RowCount); 
		for(int rowindex=0;rowindex<RowCount;rowindex++) {
			Row row=sheet.getRow(rowindex);
			//System.out.println("Total Column Number::"+ row.getPhysicalNumberOfCells());
			if (row!=null) {		
		for(int colmindex=0;colmindex<row.getLastCellNum();colmindex++) {	
			if(colmindex==columnNumber) {
				Cell cell=row.getCell(colmindex);
				if(cell!=null) {				
					DataFormatter df= new DataFormatter();
					Value.add(df.formatCellValue(cell));
				}}}}}
		return Value;	
		
	}
	
	public static void main(String[] args) throws Throwable {
		String path="./Test Data/Test Data Financial.xlsx";
		System.out.println(ExcelColumn.columnValue(path, 0));//1st
		System.out.println(ExcelColumn.columnValue(path, 1));//2nd
		System.out.println(ExcelColumn.columnValue(path, 2));//3rd
		
	}

}
