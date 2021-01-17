package com.excel.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

public class ReturnExcel {

	private static ReturnExcel instance;
	String url;
	String user;
	String pass;
	private String returnedValue;

	
	private ReturnExcel() {
		// nothing to do this time
	}

	public static ReturnExcel getInstance() {
		return instance;
	}
	static {
		instance = new ReturnExcel();
	}

	public List<String[]> returnExcel(String directory, String sheetName) {

		// Get Excel
		try {
			// Create our Workbook Object
			Workbook workbook;

			workbook = WorkbookFactory.create(new File(directory));

			// Go to Our Sheet
			assert workbook != null;
			Sheet sheet = workbook.getSheet(sheetName);

			// Create our DataFormatter
			DataFormatter df = new DataFormatter();

			List<String[]> returningValue = StreamSupport.stream(sheet.spliterator(), false)
					.filter(e -> !df.formatCellValue(e.getCell(0)).equals("URL"))
					.map(row -> new String[] { df.formatCellValue(row.getCell(0)),
							df.formatCellValue(row.getCell(1)),
							df.formatCellValue(row.getCell(2)) })
					.collect(Collectors.toList());

			workbook.close();
			System.gc();
			return returningValue;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@DataProvider(name = "Excel")
	public static Iterator<String[]> getdata() throws IOException {

		ReturnExcel rn = ReturnExcel.getInstance();

		List<String[]> finalList = new ArrayList<>();
		
		
		finalList.addAll(Objects.requireNonNull(rn.returnExcel("./TestData/CNNtestData.xls", "Most Popular Stocks")));

		return finalList.iterator();
	}

	@Factory(dataProvider = "Excel")
	private ReturnExcel(final String url, final String user, final String pass) {
		this.url = url;
		this.user = user;
		this.pass = pass;
	}

	@Test()
	public void testMethod() {
		System.out.println("Data is: " + url + ":  :" + user + ":  :" + pass);

	}
}
