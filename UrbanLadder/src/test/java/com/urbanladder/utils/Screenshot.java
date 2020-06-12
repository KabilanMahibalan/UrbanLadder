package com.urbanladder.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Screenshot {

	public static String takeScreenshot(WebDriver driver) {
		
		String imagePath = System.getProperty("user.dir") + "\\Screenshots\\"
				+ System.currentTimeMillis() + ".png";

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(src, new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return imagePath;
	}
}
