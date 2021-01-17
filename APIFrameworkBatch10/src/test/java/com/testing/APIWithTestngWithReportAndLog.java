package com.testing;

import java.util.Arrays;
import java.util.stream.Stream;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.reports.ExtentTestManager;
import com.reports.Log;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class APIWithTestngWithReportAndLog {
	static Response response;
	static JsonPath jp;
	static ExtentTest test;

	@BeforeTest
	public void setup() {
		test = ExtentTestManager.startTest("GET Method Testing");
		test.assignCategory("API Regression");
		response = RestAssured.get("http://dummy.restapiexample.com/api/v1/employees");
		Log.info("Setup done");
	}

	@Test
	public void test1() {
		// data not null
		test = ExtentTestManager.startTest("Test1");
		Assert.assertTrue(response.toString() != null);
		Log.pass("Data not null");
	}

	@Test(dependsOnMethods = { "test1" })
	public void test2() {
		test = ExtentTestManager.startTest("Test2");
		// status =success
		jp = response.jsonPath();
		Log.info("Status code =" + jp.get("status").toString());
		Assert.assertEquals(jp.get("status").toString(), "success");
		Log.pass("status =success");
	}

	@Test(dependsOnMethods = { "test2" })
	public void test3() {
		test = ExtentTestManager.startTest("Test3");
		// json format
		Log.info("Data type is json or nor =" + response.contentType());
		Assert.assertTrue(response.contentType().contains("json"));
		Log.pass("data in json format");
	}

	@Test(dependsOnMethods = { "test3" })
	public void test4() {
		test = ExtentTestManager.startTest("Test4");
		// status code =200
		Log.info("Status code found =" + response.statusLine());
		Assert.assertTrue(response.statusLine().contains("200"));
		Log.pass("status code =200");
	}

	@Test(dependsOnMethods = { "test4" })
	public void test5() {//fail
		test = ExtentTestManager.startTest("Test5");
		// Total 24 employees should be there
		String allIDs = jp.get("data.id").toString();
		Log.info(allIDs.replace("[", "").replace("]", ""));
		String[] s = allIDs.replace("[", "").replace("]", "").split(",");
		Log.info("Total id =" + s.length);
		Assert.assertEquals(s.length, 24);
		Log.pass("Total 24 employees found");
	}

	@Test(dependsOnMethods = { "test5" })
	public void test6() {
		test = ExtentTestManager.startTest("Test6");
		// 1st employee will be Tiger Nixon and age will 61
		Log.info("1st employee name =" + jp.get("data.employee_name[0]").toString());
		Assert.assertEquals(jp.get("data.employee_name[0]").toString(), "Tiger Nixon");
		Log.info("1st employee age =" + jp.get("data.employee_age[0]").toString());
		Assert.assertEquals(jp.get("data.employee_age[0]").toString(), "61");
		Log.pass("1st employee will be Tiger Nixon and age will 61");
	}

	@Test(dependsOnMethods = { "test6" })
	public void test7() {
		test = ExtentTestManager.startTest("Test7");
		// vii. Last employee name will be Doris Wilder and age 23
		String allIDs = jp.get("data.id").toString();
		String[] s = allIDs.replace("[", "").replace("]", "").split(",");
		int lastemployeeindex = s.length - 1;
		Log.info(lastemployeeindex);
		Log.info("Last employee name =" + jp.get("data.employee_name[" + lastemployeeindex + "]").toString());
		Assert.assertEquals(jp.get("data.employee_name[" + lastemployeeindex + "]").toString(), "Doris Wilder");
		Log.info("Last employee Age =" + jp.get("data.employee_age[" + lastemployeeindex + "]").toString());
		Assert.assertEquals(jp.get("data.employee_age[" + lastemployeeindex + "]").toString(), "23");
	}

	@Test(dependsOnMethods = { "test7" })
	public void test8() {
		test = ExtentTestManager.startTest("Test8");
		// viii. Lowest salary will be 85,600
		String allSalary = jp.get("data.employee_salary").toString().replace("[", "").replace("]", "");
		String[] salaryArray = allSalary.split(",");
		int[] salaryInt = Stream.of(salaryArray).map(value -> value.trim()).mapToInt(Integer::parseInt).toArray();
		int min = Arrays.stream(salaryInt).min().getAsInt();
		Assert.assertEquals(min, 85600);
		Log.info("Min salary value =" + min);
	}

	@Test(dependsOnMethods = { "test8" })
	public void test9() {
		// ix. Max salary will be 7,25,000
		test = ExtentTestManager.startTest("Test9");
		String allSalary = jp.get("data.employee_salary").toString().replace("[", "").replace("]", "");
		String[] salaryArray = allSalary.split(",");
		int[] salaryInt = Stream.of(salaryArray).map(value -> value.trim()).mapToInt(Integer::parseInt).toArray();
		int max = Arrays.stream(salaryInt).max().getAsInt();
		Assert.assertEquals(max, 725000);
		Log.info("MAx salary value =" + max);
	}

	@Test(dependsOnMethods = { "test9" })
	public void test10() {
		test = ExtentTestManager.startTest("Test10");
		// x. Lowest age will be 19

		// Age max and min
		String allage = jp.get("data.employee_age").toString().replace("[", "").replace("]", "");
		String[] ageArray = allage.split(",");
		int[] ageInt = Stream.of(ageArray).map(value -> value.trim()).mapToInt(Integer::parseInt).toArray();
		int minAge = Arrays.stream(ageInt).min().getAsInt();
		Assert.assertEquals(minAge, 19);
		Log.info("Min Age value =" + minAge);
		Log.pass("min age found"+minAge);
	}

	@Test(dependsOnMethods = { "test10" })
	public void test11() {
		test = ExtentTestManager.startTest("Test11");
		// xi. Max age will be 66
		// Age max and min
		String allage = jp.get("data.employee_age").toString().replace("[", "").replace("]", "");
		String[] ageArray = allage.split(",");
		int[] ageInt = Stream.of(ageArray).map(value -> value.trim()).mapToInt(Integer::parseInt).toArray();
		int maxAge = Arrays.stream(ageInt).max().getAsInt();
		Assert.assertEquals(maxAge, 66);
		Log.info("Max Age value =" + maxAge);
		Log.pass("Max Age value =" + maxAge);
	}

	@AfterTest
	public void teardown() {
		ExtentTestManager.endTest();
	}

}
