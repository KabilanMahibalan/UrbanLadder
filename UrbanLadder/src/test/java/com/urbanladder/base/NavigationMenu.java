package com.urbanladder.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.urbanladder.utils.BrowserFactory;
import com.urbanladder.utils.ElementUtil;
import com.urbanladder.utils.JavaScriptUtil;

public class NavigationMenu extends BaseUI {

	@FindBy(how = How.XPATH, xpath = "//li[@class='topnav_item storageunit']")
	private static WebElement storage;

	@FindBy(how = How.LINK_TEXT, linkText = "Bookshelves")
	private static WebElement bookshelves;

	@FindBy(how = How.XPATH, xpath = "//li[@class='topnav_item studyunit']")
	private static WebElement study;

	@FindBy(how = How.XPATH, xpath = "//*[@id=\"topnav_wrapper\"]/ul/li[6]/div/div/ul/li[2]/ul/li[1]/a")
	private static WebElement studyChairs;

	@FindBy(how = How.XPATH, xpath = "//li[@class='topnav_item collectionsunit']")
	private static WebElement collections;
	
	@FindBy(how = How.XPATH, xpath = "//a[contains(text(),'Gift Cards')]")
	private static WebElement giftCards;

	@FindBy(how = How.ID, id = "search")
	private static WebElement search;

	public NavigationMenu(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}

	public void viewStorage() {
		ElementUtil.click(storage);
	}


	public void navigateBookshelves() {
		this.viewStorage();
		ElementUtil.click(bookshelves);
		BrowserFactory.switchTab("Bookshelves");
	}

	public void navigateGiftCards() {
		ElementUtil.click(giftCards);
		BrowserFactory.switchTab("Gift Card");

	}

	public void viewStudy() {
		ElementUtil.click(study);
	}

	public void navigateStudyChairs() {

		this.viewStudy();
		JavaScriptUtil.clickElementByJS(studyChairs);

		/*
		 * this.viewStudy(); JavaScriptUtil.clickElementByJS(studyChairs);
		 * 
		 * new
		 * Actions(driver).moveToElement(studyChairs).click().build().perform();
		 * JavaScriptUtil.clickElementByJS(studyChairs);
		 * ElementUtil.click(studyChairs);
		 */
		BrowserFactory.switchTab("Study Chair");
	}
	
	public void viewCollections() {
		ElementUtil.click(collections);
	}
	
	public void navigateBeingAtHome() {

		this.viewCollections();
		System.out.println();

	}
}
