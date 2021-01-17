package com.excel.manager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFTableStyleInfo;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumn;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumns;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableStyleInfo;

public class ExcelWriter {

	static CreationHelper createHelper;
	static int a ;
	
	public static void wtireExcelArray(String path, String[] data) {

		// FileImageOutputStream file= new FileImageOutputStream(path);
		Workbook workbook = new XSSFWorkbook();

		/*
		 * CreationHelper helps us create instances of various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Result");

		AtomicInteger rownumber = new AtomicInteger(sheet.getLastRowNum() + 1);

		Row headerRow = sheet.createRow(0);
		AtomicInteger colmHeader = new AtomicInteger(0);

		String[] header = { "Test_id", "Description", "Expected", "Actual", "Status" };

		Arrays.stream(header).forEach(headTitle -> {
			Cell cell = headerRow.createCell(colmHeader.getAndIncrement());

			cell.setCellValue(headTitle);

		});
		Row row = sheet.createRow(rownumber.getAndIncrement());
		AtomicInteger colmNumber = new AtomicInteger(a);
		Arrays.stream(data).forEach(allValue -> {
			
			Cell cell = row.createCell(colmNumber.getAndIncrement());
			CellStyle style = workbook.createCellStyle(); // Create new style
			style.setWrapText(true); // Set wordwrap
			cell.setCellStyle(style); // Apply style to cell
			cell.setCellValue(allValue);
			a++;
		});

		// Resize all columns to fit the content size

		for (int i = 0; i < 3; i++) {
			sheet.autoSizeColumn(i);
		}

		try {
			// Write the output to a file
			FileOutputStream fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
			// Closing the workbook
			workbook.close();
		} catch (IOException e) {

		}

	}

	public static void wtireExcelList(String path, List<String> data) {

		// FileImageOutputStream file= new FileImageOutputStream(path);
		Workbook workbook = new XSSFWorkbook();

		/*
		 * CreationHelper helps us create instances of various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Result");

		AtomicInteger rownumber = new AtomicInteger(sheet.getLastRowNum() + 1);

		Row headerRow = sheet.createRow(0);
		AtomicInteger colmHeader = new AtomicInteger(0);

		String[] header = { "Test_id", "Description", "Expected", "Actual", "Status" };

		Arrays.stream(header).forEach(headTitle -> {
			Cell cell = headerRow.createCell(colmHeader.getAndIncrement());

			cell.setCellValue(headTitle);

		});

		data.forEach((value) -> {

			Row row = sheet.createRow(rownumber.getAndIncrement());
			AtomicInteger colmNumber = new AtomicInteger(0);

			String[] content = { value.split("_")[0], value.split("_")[1], value.split("_")[2], value.split("_")[3],
					value.split("_")[4]

			};

			Arrays.stream(content).forEach(allValue -> {
				Cell cell = row.createCell(colmNumber.getAndIncrement());
				 CellStyle style = workbook.createCellStyle(); // Create new style
				 style.setWrapText(true); // Set wordwrap
				 cell.setCellStyle(style); // Apply style to cell
				cell.setCellValue(allValue);

			});

		});

		// Resize all columns to fit the content size

		for (int i = 0; i < 3; i++) {
			sheet.autoSizeColumn(i);

		}

		try {
			// Write the output to a file
			FileOutputStream fileOut = new FileOutputStream(path);

			workbook.write(fileOut);
			fileOut.close();
			// Closing the workbook
			workbook.close();
		} catch (IOException e) {

		}

	}

	public static void wtireExcelListCNN(String path, List<String> data) {

	 //FileImageOutputStream file= new FileImageOutputStream(path);
		XSSFWorkbook workbook = new XSSFWorkbook();

		/*
		 * CreationHelper helps us create instances of various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		createHelper = workbook.getCreationHelper();
	 
	     
		// Create a Sheet
		XSSFSheet sheet = workbook.createSheet("Data");
		  // Create Table
	      XSSFTable table = sheet.createTable();
		    //XSSFTable table = sheet.createTable(null); //since apache poi 4.0.0
		    CTTable cttable = table.getCTTable();

		    cttable.setDisplayName("Table1");
		    cttable.setId(1);
		    cttable.setName("Test");
		    System.out.println("Excel table created with row = "+"A1:C"+(data.size()+1)+"");
		    cttable.setRef("A1:C"+(data.size()+1)+"");
		    cttable.setTotalsRowShown(false);

		    CTTableStyleInfo styleInfo = cttable.addNewTableStyleInfo();
		    styleInfo.setName("TableStyleMedium9");
		    styleInfo.setShowColumnStripes(false);
		    styleInfo.setShowRowStripes(true);

		    CTTableColumns columns = cttable.addNewTableColumns();
		    columns.setCount(3);
		    for (int i = 1; i <= 3; i++) {
		      CTTableColumn column = columns.addNewTableColumn();
		      column.setId(i);
		      column.setName("Column" + i);
		    }
   //add table data now

		AtomicInteger rownumber = new AtomicInteger(sheet.getLastRowNum() + 1);

		Row headerRow = sheet.createRow(0);
		AtomicInteger colmHeader = new AtomicInteger(0);

		String[] header = { "Name", "Value" ,"Percentage"};

		Arrays.stream(header).forEach(headTitle -> {
			Cell cell = headerRow.createCell(colmHeader.getAndIncrement());

			cell.setCellValue(headTitle);

		});

		data.forEach((value) -> {

			Row row = sheet.createRow(rownumber.getAndIncrement());
			AtomicInteger colmNumber = new AtomicInteger(0);
			 String[] content = new String[3];
			if(value.split("_").length==3) {

				content[0]=value.split("_")[0];
				content[1]=value.split("_")[1];
				content[2]=value.split("_")[2];
				
			}else if(value.split("_").length==2) {
				content[0]=value.split("_")[0];
				content[1]=value.split("_")[1];
				
			}else {
				
				System.out.println("Change the column number please..................");
			}
			DataFormat format = workbook.createDataFormat();
			Arrays.stream(content).forEach(allValue -> {
				Cell cell = row.createCell(colmNumber.getAndIncrement());
				 CellStyle style1 = workbook.createCellStyle(); // Create new style
				 //.getFormat("#,##0.00")
				style1.setDataFormat(format.getFormat("#,##0.00"));
				 style1.setWrapText(true); // Set wordwrap
				 cell.setCellStyle(style1); // Apply style to cell
				cell.setCellValue(allValue);

			});

		});

		// Resize all columns to fit the content size

		for (int i = 0; i < 3; i++) {
			sheet.autoSizeColumn(i);
		}

       
      
		try {
			// Write the output to a file
			FileOutputStream fileOut = new FileOutputStream(path);

			workbook.write(fileOut);
			System.out.println("Excel data written successfull");
			fileOut.close();
			// Closing the workbook
			workbook.close();
		} catch (IOException e) {

		}

	}

	
	public static void main(String[] args) {
		List<String> excelValue = new ArrayList<>();
		excelValue.add("Apple_22.22_10.25%");
		excelValue.add("Apple_22.22_10.25%");
		ExcelWriter.wtireExcelListCNN("./TestData/CNNExcelWrite.xlsx", excelValue);
	}
}
