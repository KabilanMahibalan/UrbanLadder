package com.urbanladder.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementUtil extends BrowserFactory {

	/**********************************************************************************************/

	public static void click(WebElement myElement) {

		try {
			myElement.click();
		} catch (ElementClickInterceptedException e) {
			// TODO: handle exception
			new Actions(driver).moveToElement(myElement).click().build()
					.perform();
		}
	}

	public static boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static boolean isElementPresent(WebElement myElement) {
		try {
			new WebDriverWait(driver, 20).until(ExpectedConditions
					.visibilityOf(myElement));
			return true;
		} catch (TimeoutException e) {
			// TODO: handle exception
			return false;
		}
	}

	public static void enterText(WebElement textBox, String text) {
		if(isElementPresent(textBox)){
			textBox.sendKeys(text);
		}
	}

	public static void selectByValue(WebElement mySelect, String value) {
		Select select = new Select(mySelect);
		select.selectByValue(value);
	}

	public static void submit(WebElement submit) {
		submit.submit();
	}

	public static void acceptAlert() {
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		System.out.println("Alert data: " + alertText);
		alert.accept();
	}

	/**********************************************************************************************/

	public static void selectByAttribute(List<WebElement> list,
			String attribute, String value) {
		for (WebElement element : list) {
			if (element.getAttribute(attribute).equals(value)) {
				click(element);
				break;
			}
		}
	}

	/**********************************************************************************************/
	public static void storeImage(WebElement image, String imagePath) {

		String logoSRC = image.getAttribute("src");
		URL imageURL;
		try {
			imageURL = new URL(logoSRC);
			BufferedImage saveImage = ImageIO.read(imageURL);
			ImageIO.write(saveImage, "png", new File(imagePath));

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * ClassLoader classLoader = getClass().getClassLoader(); File inputFile
		 * = new File(classLoader .getResource(imagePath) .getFile());
		 * 
		 * byte[] fileContent = FileUtils.readFileToByteArray(inputFile); String
		 * encodedString = Base64 .getEncoder() .encodeToString(fileContent);
		 * 
		 * // create output file File outputFile = new File(inputFile
		 * .getParentFile() .getAbsolutePath() + File.pathSeparator +
		 * imagePath);
		 * 
		 * // decode the string and write to file byte[] decodedBytes = Base64
		 * .getDecoder() .decode(encodedString);
		 * FileUtils.writeByteArrayToFile(outputFile, decodedBytes);
		 */

	}
}
