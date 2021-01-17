package com.testing;

import java.util.Arrays;
import java.util.stream.Stream;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class APIWithTestng {
	static Response response;
	static JsonPath jp;

	@BeforeTest
	public void setup() {
		response = RestAssured.get("http://dummy.restapiexample.com/api/v1/employees");

	}

	@Test
	public void test1() {
		// data not null
		Assert.assertTrue(response.toString() != null);

	}

	@Test(dependsOnMethods = { "test1" })
	public void test2() {
		// status =success
		jp = response.jsonPath();
		System.out.println("Status code =" + jp.get("status").toString());
		Assert.assertEquals(jp.get("status").toString(), "success");

	}

	@Test(dependsOnMethods = { "test2" })
	public void test3() {
		// json format
		System.out.println("Data type is json or nor =" + response.contentType());
		Assert.assertTrue(response.contentType().contains("json"));
	}

	@Test(dependsOnMethods = { "test3" })
	public void test4() {
		// status code =200
		System.out.println("Status code found =" + response.statusLine());
		Assert.assertTrue(response.statusLine().contains("200"));
	}

	@Test(dependsOnMethods = { "test4" })
	public void test5() {
		// Total 24 employees should be there
		String allIDs = jp.get("data.id").toString();
		System.out.println(allIDs.replace("[", "").replace("]", ""));
		String[] s = allIDs.replace("[", "").replace("]", "").split(",");
		System.out.println("Total id =" + s.length);
		Assert.assertEquals(s.length, 24);
	}

	@Test(dependsOnMethods = { "test5" })
	public void test6() {
		// 1st employee will be Tiger Nixon and age will 61
		System.out.println("1st employee name =" + jp.get("data.employee_name[0]").toString());
		Assert.assertEquals(jp.get("data.employee_name[0]").toString(), "Tiger Nixon");
		System.out.println("1st employee age =" + jp.get("data.employee_age[0]").toString());
		Assert.assertEquals(jp.get("data.employee_age[0]").toString(), "61");
	}

	@Test(dependsOnMethods = { "test6" })
	public void test7() {
		// vii. Last employee name will be Doris Wilder and age 23
		String allIDs = jp.get("data.id").toString();
		String[] s = allIDs.replace("[", "").replace("]", "").split(",");
		int lastemployeeindex = s.length - 1;
		System.out.println(lastemployeeindex);
		System.out.println("Last employee name =" + jp.get("data.employee_name[" + lastemployeeindex + "]").toString());
		Assert.assertEquals(jp.get("data.employee_name[" + lastemployeeindex + "]").toString(), "Doris Wilder");
		System.out.println("Last employee Age =" + jp.get("data.employee_age[" + lastemployeeindex + "]").toString());
		Assert.assertEquals(jp.get("data.employee_age[" + lastemployeeindex + "]").toString(), "23");
	}

	@Test(dependsOnMethods = { "test7" })
	public void test8() {
		// viii. Lowest salary will be 85,600
		String allSalary = jp.get("data.employee_salary").toString().replace("[", "").replace("]", "");
		String[] salaryArray = allSalary.split(",");
		int[] salaryInt = Stream.of(salaryArray).map(value -> value.trim()).mapToInt(Integer::parseInt).toArray();
		int min = Arrays.stream(salaryInt).min().getAsInt();
		Assert.assertEquals(min, 85600);
		System.out.println("Min salary value =" + min);
	}

	@Test(dependsOnMethods = { "test8" })
	public void test9() {
		// ix. Max salary will be 7,25,000

		String allSalary = jp.get("data.employee_salary").toString().replace("[", "").replace("]", "");
		String[] salaryArray = allSalary.split(",");
		int[] salaryInt = Stream.of(salaryArray).map(value -> value.trim()).mapToInt(Integer::parseInt).toArray();
		int max = Arrays.stream(salaryInt).max().getAsInt();
		Assert.assertEquals(max, 725000);
		System.out.println("MAx salary value =" + max);
	}

	@Test(dependsOnMethods = { "test9" })
	public void test10() {
		// x. Lowest age will be 19

		// Age max and min
		String allage = jp.get("data.employee_age").toString().replace("[", "").replace("]", "");
		String[] ageArray = allage.split(",");
		int[] ageInt = Stream.of(ageArray).map(value -> value.trim()).mapToInt(Integer::parseInt).toArray();
		int minAge = Arrays.stream(ageInt).min().getAsInt();
		Assert.assertEquals(minAge, 19);
		System.out.println("Min Age value =" + minAge);
	}

	@Test(dependsOnMethods = { "test10" })
	public void test11() {
		// xi. Max age will be 66
		// Age max and min
		String allage = jp.get("data.employee_age").toString().replace("[", "").replace("]", "");
		String[] ageArray = allage.split(",");
		int[] ageInt = Stream.of(ageArray).map(value -> value.trim()).mapToInt(Integer::parseInt).toArray();
		int maxAge = Arrays.stream(ageInt).max().getAsInt();
		Assert.assertEquals(maxAge, 66);
		System.out.println("Max Age value =" + maxAge);

	}

	@AfterTest
	public void teardown() {

	}

}
