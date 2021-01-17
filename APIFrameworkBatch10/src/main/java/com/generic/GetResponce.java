
package com.generic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.reports.ExtentTestManager;
import com.reports.Log;
import com.util.BaseConfig;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetResponce {

	static ExtentTest test;
	public static void getData() throws IOException {
		
		test = ExtentTestManager.startTest("GET Method Testing");
		test.assignCategory("API Testing");
		Log.info(BaseConfig.getConfigValue("URL"));

		Response response = RestAssured.get(BaseConfig.getConfigValue("URL"));

		
		JsonPath jsonPath = response.jsonPath();
		Assert.assertTrue(jsonPath.get("data").toString() != null, "Data should not be null");
		Assert.assertEquals(jsonPath.get("status").toString(), "success");
		Assert.assertTrue(response.contentType().contains("json"));
		Assert.assertEquals(response.statusCode(), 200, "Status code validation for 200");

		JsonPath jsonResponse = new JsonPath(response.asString());
		String employeeName = jsonResponse.get("data.employee_name").toString();
		String employeeAge = jsonResponse.get("data.employee_age").toString();
		String employeeSalary = jsonResponse.get("data.employee_salary").toString();
		String[] name = employeeName.replace("[", "").replace("]", "").split(",");
		Assert.assertEquals(name.length, 24);
		String[] age = employeeAge.replace("[", "").replace("]", "").split(",");
		String[] salary = employeeSalary.replace("[", "").replace("]", "").split(",");
		Assert.assertEquals(name[0].trim(), "Tiger Nixon");
		Assert.assertEquals(name[name.length - 1].trim(), "Doris Wilder");
		int[] arr = Stream.of(age).map(value -> value.trim()).mapToInt(Integer::parseInt).toArray();
		Log.info(Arrays.toString(arr));
		int maxage =Arrays.stream(arr).max().getAsInt();
		Log.info(maxage);
		int minage =Arrays.stream(arr).min().getAsInt();
		Log.info(minage);
		int[] sal = Stream.of(salary).map(value -> value.trim()).mapToInt(Integer::parseInt).toArray();
		Log.info(Arrays.toString(sal));
		int maxsal=Arrays.stream(sal).max().getAsInt();
		Log.info(maxsal);
		int minsal =Arrays.stream(sal).min().getAsInt();
		Log.info(minsal);
		ExtentTestManager.endTest();
	}

	public static void main(String[] args) throws IOException {
		getData();
	}
}
