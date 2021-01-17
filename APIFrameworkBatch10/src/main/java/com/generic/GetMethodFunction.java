package com.generic;

import java.util.Arrays;
import java.util.stream.Stream;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetMethodFunction {

	public void getTest1() {
		
		Response response = RestAssured.get("http://dummy.restapiexample.com/api/v1/employees");
		// response.prettyPrint();
		// data not null
		Assert.assertTrue(response.toString() != null);
		JsonPath jp = response.jsonPath();
		// System.out.println("Status code ="+jp.get("status").toString());
		Assert.assertEquals(jp.get("status").toString(), "success");
		// System.out.println("Data type is json or nor =" + response.contentType());
		Assert.assertTrue(response.contentType().contains("json"));
		// System.out.println("Status code found ="+response.statusLine());
		Assert.assertTrue(response.statusLine().contains("200"));
		// data =\ attribute/locator & id= object>>>>> json xpath = Attribute.object
		// System.out.println("All data ="+jp.get("data").toString());
		// System.out.println("All id ="+jp.get("data.id").toString());
		String allIDs = jp.get("data.id").toString();
		// System.out.println(allIDs.replace("[","").replace("]", ""));
		String[] s = allIDs.replace("[", "").replace("]", "").split(",");
		// System.out.println("Total id ="+s.length);
		Assert.assertEquals(s.length, 24);
		System.out.println("1st employee name =" + jp.get("data.employee_name[0]").toString());
		Assert.assertEquals(jp.get("data.employee_name[0]").toString(), "Tiger Nixon");
		System.out.println("1st employee age =" + jp.get("data.employee_age[0]").toString());
		Assert.assertEquals(jp.get("data.employee_age[0]").toString(), "61");
		// last employee = total count/lenght/size -1
		int lastemployeeindex = s.length - 1;
		System.out.println(lastemployeeindex);
		System.out.println("Last employee name =" + jp.get("data.employee_name[" + lastemployeeindex + "]").toString());
		Assert.assertEquals(jp.get("data.employee_name[" + lastemployeeindex + "]").toString(), "Doris Wilder");
		System.out.println("Last employee Age =" + jp.get("data.employee_age[" + lastemployeeindex + "]").toString());
		Assert.assertEquals(jp.get("data.employee_age[" + lastemployeeindex + "]").toString(), "23");

		// all salary + int/double/long + store in Array +max/min
		String allSalary = jp.get("data.employee_salary").toString().replace("[", "").replace("]", "");
		String[] salaryArray = allSalary.split(",");
		int[] salaryInt = Stream.of(salaryArray).map(value -> value.trim()).mapToInt(Integer::parseInt).toArray();
		int max = Arrays.stream(salaryInt).max().getAsInt();
		Assert.assertEquals(max, 725000);
		System.out.println("Max salary value =" + max);
		int min = Arrays.stream(salaryInt).min().getAsInt();
		Assert.assertEquals(min, 85600);
		System.out.println("Min salary value =" + min);
		// Age max and min
		String allage = jp.get("data.employee_age").toString().replace("[", "").replace("]", "");
		String[] ageArray = allage.split(",");
		int[] ageInt = Stream.of(ageArray).map(value -> value.trim()).mapToInt(Integer::parseInt).toArray();
		int maxAge = Arrays.stream(ageInt).max().getAsInt();
		Assert.assertEquals(maxAge, 66);
		System.out.println("Max Age value =" + maxAge);
		int minAge = Arrays.stream(ageInt).min().getAsInt();
		Assert.assertEquals(minAge, 19);
		System.out.println("Min Age value =" + minAge);
	}
	
}
