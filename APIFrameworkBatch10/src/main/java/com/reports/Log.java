package com.reports;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.util.TakeAppScreenShot;

public class Log implements ITestListener {
	static ExtentReports extent;
	private static ExtentTest test;

	public Log() {

		//log4j2
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("log4j.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//PropertyConfigurator.configure(props);//===config file log4j
		 //PropertyConfigurator.configure("Log4j.properties");

	}

	// Initialize Log4j logs
	// private static Logger Log = Logger.getLogger(Log.class);//====> log4j
	private static Logger Log = LogManager.getLogger(Log.class);//==> log4j2

	// This is to print log for the beginning of the test case, as we usually run so
	// many test cases as a test suite

	public static void startTestCase(String sTestCaseName) {
		// PropertyConfigurator.configure("Log4j.properties");
		Log.info("****************************************************************");
		Log.info("$$$$$$$$$$$$$$$$$$$$$ " + sTestCaseName + "  $$$$$$$$$$$$$$$$$$$$$$$$$");
		Log.info("******************************************************************");
	}

	public static void endTestCase(String sTestCaseName) {
		Log.info("XXXXXXXXXXXXXXXX " + "-E---N---D-  " + sTestCaseName + "   XXXXXXXXXXXXXX");
	}

	// Need to create these methods, so that they can be called

	public static void info(String message) {
		Log.info(message);
		ExtentTestManager.getTest().log(Status.INFO, message);
	}
	public static void info(int value) {
		Log.info(value);
		ExtentTestManager.getTest().log(Status.INFO, String.valueOf(value));
	}

	public static void screenShot(WebDriver driver, String ScreenShotName) {
		Log.info("Screen Shot taken .." + ScreenShotName);
		// captureScreenShot
		String path = TakeAppScreenShot.captureScreenShot(driver, ScreenShotName);

		try {
			ExtentTestManager.getTest().info(ScreenShotName,
					MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public static void pass(String message) {
		Log.info(message);
		ExtentTestManager.getTest().pass(message);
	}

	public static void pass(String message, MediaEntityModelProvider provider) {
		Log.info(message);
		ExtentTestManager.getTest().pass(message, provider);
	}

	public static void fail(String message) {
		Log.info(message);
		ExtentTestManager.getTest().fail(message);
	}

	public static void fail(String message, MediaEntityModelProvider provider) {
		Log.info(message);
		ExtentTestManager.getTest().pass(message, provider);
	}

	public static void warn(String message) {
		Log.warn(message);
		ExtentTestManager.getTest().log(Status.WARNING, message);
	}

	public static void error(String message) {
		Log.error(message);
		ExtentTestManager.getTest().log(Status.ERROR, message);
	}

	public static void fatal(String message) {
		Log.fatal(message);
		ExtentTestManager.getTest().log(Status.FATAL, message);
	}

	public static void debug(String message) {
		Log.debug(message);
		ExtentTestManager.getTest().log(Status.DEBUG, message);
	}

	public void onStart(ITestContext context) {
		System.out.println("*** Test Suite " + context.getName() + " started ***");
	}

	public void onFinish(ITestContext context) {
		System.out.println(("*** Test Suite " + context.getName() + " ending ***"));
		ExtentTestManager.endTest();
		ExtentManager.getInstance().flush();
	}

	public void onTestStart(ITestResult result) {
		System.out.println(("** Running test method " + result.getMethod().getMethodName() + "..."));
		ExtentTestManager.startTest(result.getMethod().getMethodName());
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("** Executed " + result.getMethod().getMethodName() + " test successfully...");
		ExtentTestManager.getTest().log(Status.PASS, "Test passed");
	}

	public void onTestFailure(ITestResult result) {
		System.out.println("** Test execution " + result.getMethod().getMethodName() + " failed...");
		ExtentTestManager.getTest().log(Status.FAIL, "Test Failed");
	}

	public void onTestSkipped(ITestResult result) {
		System.out.println("** Test " + result.getMethod().getMethodName() + " skipped...");
		ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("*** Test failed but within percentage % " + result.getMethod().getMethodName());
	}

}