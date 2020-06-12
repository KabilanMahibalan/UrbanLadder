package com.urbanladder.utils;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BrowserFactory {
	
	public static WebDriver driver;

	public static WebDriver createWebDriver(String browser) {

		if (browser.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver",
					".\\src\\test\\resources\\drivers\\chromedriver.exe");
			
			driver = new ChromeDriver();
			
		} else if (browser.equals("Firefox")) {
			System.setProperty("webdriver.gecko.driver",
					".\\src\\test\\resources\\drivers\\geckodriver.exe");

			driver = new FirefoxDriver();
		}
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		return driver;
	}
	
	public static void navigateUrl(String websiteUrl) {
		driver.navigate().to(websiteUrl);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
	}
	
	public static void tearDown() {
		driver.close();
	}

	public static void quitBrowser() {
		driver.quit();
	}
	
	public static void refreshPage() {
		driver.navigate().refresh();
	}
	
	public static void switchTab(String title) {

		Set<String> tabs = driver.getWindowHandles();
		for (String myTab : tabs) {
			if (driver.switchTo().window(myTab).getTitle().contains(title)) {
				driver.switchTo().window(myTab);
				break;
			}
		}
	}
	
}
