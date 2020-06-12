package com.urbanladder.pages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.urbanladder.base.BaseUI;
import com.urbanladder.base.NavigationMenu;
import com.urbanladder.utils.ElementUtil;

public class HomePage extends BaseUI {

	public NavigationMenu navigationMenu;

	@FindBy(how = How.XPATH, xpath = "//a[@class = 'close-reveal-modal hide-mobile']")
	private static WebElement closePopup;

	public HomePage(WebDriver driver, ExtentTest logger) {
		this.driver = driver;
		this.logger = logger;
		navigationMenu = PageFactory.initElements(driver, NavigationMenu.class);
	}

	public void closeAuthenticationPopup() {
		/*
		 * new WebDriverWait(driver, 10).until(
		 * ExpectedConditions.visibilityOfElementLocated(
		 * By.id("authentication_popup")));
		 */
		try {
			new WebDriverWait(driver, 20).until(ExpectedConditions
					.elementToBeClickable(closePopup));
			ElementUtil.click(closePopup);
		} catch (TimeoutException e) {
			// TODO: handle exception
			// e.printStackTrace();
		} 

	}

	public BookshelvesPage navigateBookshelves() {
		child = logger.createNode("BOOKSHELVES");
		test.set(child);
		try {
			navigationMenu.navigateBookshelves();
			child.log(Status.INFO, "Loading results for bookshelves");
			reportPass(child,
					"Furniture Home >Storage > Living Storage >Bookshelves");
		} catch (Exception e) {
			// TODO: handle exception
			reportFail(child, e.getMessage());
		}

		BookshelvesPage bookshelvesPage = new BookshelvesPage(driver, logger);
		PageFactory.initElements(driver, bookshelvesPage);
		return bookshelvesPage;
	}
	
	public StudyChairsPage navigateStudyChairs() {
		child = logger.createNode("STUDY CHAIR");
		test.set(child);
		try {
			navigationMenu.navigateStudyChairs();
			child.log(Status.INFO, "Loading results for study chairs");
			reportPass(child,
					"Furniture Home > Study & Home Office > Study Chairs > Study Chair");
		} catch (Exception e) {
			// TODO: handle exception
			reportFail(child, e.getMessage());
		}

		StudyChairsPage studyChairsPage = new StudyChairsPage(driver, logger);
		PageFactory.initElements(driver, studyChairsPage);
		return studyChairsPage;
	}
	

	public GiftCardsPage navigateGiftCards() {
		child = logger.createNode("GIFT CARDS");
		test.set(child);
		try {
			navigationMenu.navigateGiftCards();
			child.log(Status.INFO, "Loading Gift Cards");
			reportPass(child, "Gift Cards page successfully navigated");
		} catch (Exception e) {
			// TODO: handle exception
			reportFail(child, e.getMessage());
		}

		GiftCardsPage giftCardsPage = new GiftCardsPage(driver, logger);
		PageFactory.initElements(driver, giftCardsPage);

		return giftCardsPage;
	}
}
