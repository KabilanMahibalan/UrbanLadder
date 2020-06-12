package com.urbanladder.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil extends BrowserFactory {

	static JavascriptExecutor jsexecutor = (JavascriptExecutor) driver;

	/************************************ PAGE LOAD WAIT ******************************************/

	public static void waitForPageLoad() {
		
		int i = 0;
		while (i != 180) {
			String pageState = (String) jsexecutor
					.executeScript("return document.readyState;");
			if (pageState.equals("complete")) {
				break;
			} else {
				waitLoad(1);
			}
		}
		waitLoad(2);

		i = 0;
		while (i != 180) {
			Boolean jsState = (Boolean) jsexecutor
					.executeScript("return window.jQuery != undefined && jQuery.active == 0;");
			if (jsState) {
				break;
			} else {
				waitLoad(1);
			}
		}
	}
	
	public static void waitLoad(int i) {
		try {
			Thread.sleep(i * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**********************************************************************************************/

	public static void clickElementByJS(WebElement element) {
		jsexecutor.executeScript("arguments[0].click();", element);
	}


	public static void scrollToPageTop() {
		(jsexecutor)
				.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
	}
}
