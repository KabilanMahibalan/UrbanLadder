package com.urbanladder.pages;

import org.openqa.selenium.NoSuchElementException;
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
import com.urbanladder.utils.BrowserFactory;
import com.urbanladder.utils.ElementUtil;

public class GiftCardsPage extends BaseUI {

	public NavigationMenu navigationMenu;

	public GiftCardsPage(WebDriver driver, ExtentTest logger) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		this.logger = logger;
		navigationMenu = PageFactory.initElements(driver, NavigationMenu.class);

	}

	@FindBy(how = How.XPATH, xpath = "//h3[contains(text(),'Weddings')]")
	private static WebElement occasion_weddings;

	@FindBy(how = How.XPATH, xpath = "//h3[contains(text(),'House Warming')]")
	private static WebElement occasion_houseWarming;

	@FindBy(how = How.XPATH, xpath = "//h3[contains(text(),'Birthday')]")
	private static WebElement occasion_birthdayOrAnniversary;

	@FindBy(how = How.XPATH, xpath = "//h3[contains(text(),'Special Occasions')]")
	private static WebElement occasion_specialOccasions;

	@FindBy(how = How.XPATH, xpath = "//h3[contains(text(),'Corporate')]")
	private static WebElement occasion_corporateOrBulkGifting;

	@FindBy(how = How.XPATH, xpath = "//h3[contains(text(),'Other')]")
	private static WebElement occasion_other;

	@FindBy(how = How.XPATH, xpath = "//input[@placeholder='Amount']")
	private static WebElement customizeYourGift_amountTextBox;

	@FindBy(how = How.XPATH, xpath = "//body//select[1]")
	private static WebElement customizeYourGift_selectMonthAndYear;

	@FindBy(how = How.XPATH, xpath = "//body//select[2]")
	private static WebElement customizeYourGift_selectDay;

	@FindBy(how = How.XPATH, xpath = "//button[contains(text(),'Next')]")
	private static WebElement customizeYourGift_nextButton;

	@FindBy(how = How.NAME, name = "recipient_name")
	private static WebElement to_recipientName;

	@FindBy(how = How.NAME, name = "recipient_email")
	private static WebElement to_recipientEmail;

	@FindBy(how = How.NAME, name = "customer_name")
	private static WebElement from_customerName;

	@FindBy(how = How.NAME, name = "customer_email")
	private static WebElement from_customerEmail;

	@FindBy(how = How.NAME, name = "customer_mobile_number")
	private static WebElement from_customerMobileNumber;

	@FindBy(how = How.NAME, name = "message")
	private static WebElement message;

	@FindBy(how = How.XPATH, xpath = "//button[contains(text(),'Confirm')]")
	private static WebElement submit;

	@FindBy(how = How.XPATH, xpath = "//span[contains(text(),'Proceed to payment')]")
	private static WebElement proceedToPayment;

	@FindBy(how = How.XPATH, xpath = "//h2[contains(text(),'Confirm the details')]/parent::div/ul")
	private static WebElement errorMessage;

	@FindBy(how = How.XPATH, xpath = "//iframe[@class = 'razorpay-checkout-frame']")
	private static WebElement merchantCard;

	public void logGiftCards() {
		logger = report.createTest("URBANLADDER - GIFT CARDS");
		parent.set(logger);
	}

	public void chooseOccasion(String occasion) {

		child = logger.createNode("Occasion: " + occasion);
		test.set(child);
		try {
			switch (occasion) {
			case "Weddings":
				ElementUtil.click(occasion_weddings);
				break;
			case "House Warming":
				ElementUtil.click(occasion_houseWarming);
				break;
			case "Birthday/Anniversary":
				ElementUtil.click(occasion_birthdayOrAnniversary);
				break;
			case "Special Occasions":
				ElementUtil.click(occasion_specialOccasions);
				break;
			case "Corporate/Bulk gifting":
				ElementUtil.click(occasion_corporateOrBulkGifting);
				break;
			case "Other":
				ElementUtil.click(occasion_other);
				break;
			default:
				break;
			}

			reportPass(child, occasion + " is chosen");
		} catch (Exception e) {
			// TODO: handle exception
			reportFail(child, e.getMessage());
		}

	}

	public void enterAmount(double amount) {

		double min = Double.parseDouble(customizeYourGift_amountTextBox
				.getAttribute("min"));
		double max = Double.parseDouble(customizeYourGift_amountTextBox
				.getAttribute("max"));
		if (amount >= min && amount <= max) {
			ElementUtil.enterText(customizeYourGift_amountTextBox, amount + "");
		} else if (amount <= min) {
			ElementUtil.enterText(customizeYourGift_amountTextBox, min + "");
		} else if (amount >= max) {
			ElementUtil.enterText(customizeYourGift_amountTextBox, max + "");
		}

	}

	public void selectMonthAndYear(String value) {
		try {
			ElementUtil.selectByValue(customizeYourGift_selectMonthAndYear,
					value);
		} catch (NoSuchElementException e) {
			// TODO: handle exception
		}

	}

	public void selectDay(String value) {
		try {
			ElementUtil.selectByValue(customizeYourGift_selectDay, value);
		} catch (NoSuchElementException e) {
			// TODO: handle exception
		}
	}

	public void customizeYourGift(double amount, String monthAndYear, String day) {
		child = logger.createNode("Customize Your Gift");
		test.set(child);
		try {
			enterAmount(amount);
			selectMonthAndYear(monthAndYear);
			selectDay(day);
			ElementUtil.click(customizeYourGift_nextButton);
			reportPass(child, "Gift card customized by setting date and amount");
		} catch (Exception e) {
			// TODO: handle exception
			reportFail(child, e.getMessage());
		}

	}

	public void fillRecipientCustomerForm(String recipientName,
			String recipientEmail, String customerName, String customerEmail,
			String customerMobileNumber, String messageString) {

		child = logger.createNode("Fill Confirmation form");
		test.set(child);

		ElementUtil.enterText(to_recipientName, recipientName);
		ElementUtil.enterText(to_recipientEmail, recipientEmail);

		ElementUtil.enterText(from_customerName, customerName);
		ElementUtil.enterText(from_customerEmail, customerEmail);
		ElementUtil.enterText(from_customerMobileNumber, customerMobileNumber);

		ElementUtil.enterText(message, messageString);

		ElementUtil.submit(submit);

	}

	public void proceedToPayment() {
		ElementUtil.click(proceedToPayment);
		try {
			new WebDriverWait(driver, 20).until(ExpectedConditions
					.visibilityOf(merchantCard));

			reportPass(
					child,
					"Test passed when provided valid values and system navigated to the Payment page.");
		} catch (TimeoutException e) {
			// TODO: handle exception
			child.log(Status.INFO, "Error Message: " + errorMessage.getText());
			reportPass(child,
					"Upon providing invalid values, system produced an error message.");
			BrowserFactory.refreshPage();
			// JavaScriptUtil.waitForPageLoad();
			// JavaScriptUtil.scrollToPageTop();
		} /*
		 * catch (Exception e) { // TODO: handle exception e.printStackTrace();
		 * try { reportFail(child, e.getMessage()); } catch
		 * (UnhandledAlertException f) { try { ElementUtil.acceptAlert(); }
		 * catch (NoAlertPresentException eNew) { e.printStackTrace(); } } }
		 */

	}

}
