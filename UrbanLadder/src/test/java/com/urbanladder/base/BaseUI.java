package com.urbanladder.base;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.urbanladder.pages.HomePage;
import com.urbanladder.utils.BrowserFactory;
import com.urbanladder.utils.ExtentReportManager;
import com.urbanladder.utils.PropertiesUtil;
import com.urbanladder.utils.Screenshot;

public class BaseUI {

	public WebDriver driver;
	public Properties properties;

	public ExtentReports report = ExtentReportManager.getInstance();
	public ThreadLocal<ExtentTest> parent = new ThreadLocal<ExtentTest>();
	public ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
	public ExtentTest logger;
	public ExtentTest child;

	public HomePage homePage;

	@BeforeSuite
	public WebDriver invokeBrowser() {

		properties = PropertiesUtil
				.getProperties(".\\src\\test\\resources\\objectRepository\\projectConfig.properties");
		
		driver = BrowserFactory.createWebDriver(properties
				.getProperty("browser_name"));
		return driver;
	}

	@BeforeTest
	public HomePage openApplication() {

		logger = report.createTest(properties.getProperty("appName"));
		parent.set(logger);
		child = logger.createNode("Open Application");
		test.set(child);

		try {
			child.log(Status.INFO,
					"Navigating to " + properties.getProperty("baseUrl")
							+ "...");
			BrowserFactory.navigateUrl(properties.getProperty("baseUrl"));
			reportPass(child, "Application opened sucessfully.");

		} catch (Exception e) {
			// TODO: handle exception
			reportFail(child, e.getMessage());
		}

		homePage = new HomePage(driver, logger);
		PageFactory.initElements(driver, homePage);
		homePage.closeAuthenticationPopup();
		return homePage;
	}

	@AfterTest
	public void flushReport(){
		report.flush();
	}
	
	@AfterSuite
	public void quitBrowser() {
		BrowserFactory.quitBrowser();
	}

	/****************************************************************************************/
	public void reportFail(ExtentTest test, String reportString) {
		test.log(Status.FAIL, reportString);
		try {
			test.addScreenCaptureFromPath(Screenshot.takeScreenshot(driver));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			test.log(Status.FAIL, e.getMessage());
		}
	}

	public void reportPass(ExtentTest test, String reportString) {
		test.log(Status.PASS, reportString);
	}

	/****************************************************************************************/
}
