package com.urbanladder.base;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.urbanladder.utils.ElementUtil;
import com.urbanladder.utils.JavaScriptUtil;

public class Filters extends BaseUI{

	
	/**********************************************************************************************/
	/* F I L T E R S -- P R I C E */
	/**********************************************************************************************/

	@FindBy(how = How.XPATH, xpath = "//li[@class = 'item' and @data-group = 'price']")
	private static WebElement price;

	@FindBy(how = How.XPATH, xpath = "//div[@class= 'range-slider noUi-target noUi-ltr noUi-horizontal noUi-background']")
	private static WebElement slider;

	@FindBy(how = How.ID, id = "filters_price_min")
	private static WebElement minimumPrice;

	@FindBy(how = How.ID, id = "filters_price_max")
	private static WebElement maximumPrice;

	@FindBy(how = How.XPATH, xpath = "//div[@class='noUi-handle noUi-handle-lower']")
	private static WebElement priceSliderLowerHandle;

	@FindBy(how = How.XPATH, xpath = "//div[@class='noUi-handle noUi-handle-upper']")
	private static WebElement priceSliderUpperHandle;

	@FindBy(how = How.XPATH, xpath = "//li[@class = 'selrange-filter']")
	private static WebElement selectedOptions_price;

	@FindBy(how = How.XPATH, xpath = "//li[@class='selrange-filter']/span[@class='close icofont icofont-cross']")
	private static WebElement clearPrice;
	
	/**********************************************************************************************/
	/* F I L T E R S -- E X C L U D E O U T OF S T O C K S */
	/**********************************************************************************************/

	@FindBy(how = How.ID, id = "filters_availability_In_Stock_Only")
	private static WebElement excludeOutOfStocks;

	@FindBy(how = How.XPATH, xpath = "//div[@class = 'selected-options']//li[@data-filter-name = 'availability']")
	private static WebElement selectedOptions_inStockOnly;

	@FindBy(how = How.XPATH, xpath = "//li[@data-filter-name = 'availability']/span[@class='close icofont icofont-cross']")
	private static WebElement clearInStockOnly;

	/**********************************************************************************************/
	/* F I L T E R S -- S O R T B Y */
	/**********************************************************************************************/

	@FindBy(how = How.XPATH, xpath = "//div[@data-group='sorting']")
	private static WebElement sortBy;

	@FindBy(how = How.XPATH, xpath = "//div[@data-group='sorting']//li")
	private static List<WebElement> sortingOptions;

	@FindBy(how = How.ID, id = "sort")
	private static WebElement selectedOptions_sorting;

	/**********************************************************************************************/
	/* F I L T E R S -- C L E A R */
	/**********************************************************************************************/

	@FindBy(how = How.XPATH, xpath = "//span[@class = 'close icofont icofont-cross']")
	private static List<WebElement> clearFilters;

	/**********************************************************************************************/
	/* F I L T E R S -- S E L E C T E D O P T I O N S */
	/**********************************************************************************************/

	@FindBy(how = How.ID, id = "topnav_wrapper")
	private static WebElement pageTopWrapper;

	@FindBy(how = How.XPATH, xpath = "//div[@class = 'optionsblock']")
	private static WebElement optionsBlock;

	@FindBy(how = How.XPATH, xpath = "//div[@class='selected-options']")
	private static WebElement selectedTab;

	/**********************************************************************************************/

	public Filters(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}
	
	/**********************************************************************************************/
	/*                                                                                            */
	/**********************************************************************************************/

	
	public void viewPrice() {
		ElementUtil.click(price);
	}

	public void setPrice(String priceRange, ExtentTest test) {

		double minPrice = Double.parseDouble(priceRange.substring(0,
				priceRange.indexOf('-')));
		double maxPrice = Double.parseDouble(priceRange.substring(priceRange
				.indexOf('-') + 1));

		test.log(Status.INFO, "Setting minimum price as " + minPrice);

		JavaScriptUtil.waitForPageLoad();
		ElementUtil.click(optionsBlock);
		this.viewPrice();

		Actions builder = new Actions(driver);
		if (Double.parseDouble(minimumPrice.getAttribute("value")) <= minPrice) {
			try {
				builder.moveToElement(priceSliderLowerHandle);
				while (Double.parseDouble(minimumPrice.getAttribute("value")) <= minPrice) {
					try {
						builder.clickAndHold().moveByOffset(10, 0);
						builder.release();
						builder.build().perform();
					} catch (MoveTargetOutOfBoundsException e) {
						// TODO: handle exception
						viewPrice();
						builder.moveToElement(priceSliderLowerHandle);
					}
				}
				ElementUtil.click(priceSliderLowerHandle);
				JavaScriptUtil.waitForPageLoad();

				this.viewPrice();
				if (maxPrice != Double.parseDouble(minimumPrice
						.getAttribute("value"))) {
					test.log(Status.INFO,
							"Closest available Price to " + minPrice + " is "
									+ minimumPrice.getAttribute("value"));
				}
				reportPass(
						test,
						"Minimum price set as "
								+ minimumPrice.getAttribute("value"));
			} catch (Exception e) {
				// TODO: handle exception
				reportFail(test, e.getMessage());
			}

		} else {
			test.log(
					Status.INFO,
					"Available minimum price is "
							+ minimumPrice.getAttribute("value"));
		}

		//

		test.log(Status.INFO, "Setting maximum price as " + maxPrice);
		if (Double.parseDouble(maximumPrice.getAttribute("value")) >= maxPrice) {
			try {
				builder.moveToElement(priceSliderUpperHandle);
				while (Double.parseDouble(maximumPrice.getAttribute("value")) >= maxPrice) {
					try {
						builder.clickAndHold().moveByOffset(-20, 0);
						builder.release();
						builder.build().perform();
					} catch (MoveTargetOutOfBoundsException e) {
						// TODO: handle exception
						viewPrice();
						builder.moveToElement(priceSliderUpperHandle);
					}
				}
				// ElementUtil.click(priceSliderUpperHandle);

				if (maxPrice != Double.parseDouble(maximumPrice
						.getAttribute("value"))) {
					test.log(Status.INFO,
							"Closest available Price to " + maxPrice + " is "
									+ maximumPrice.getAttribute("value"));
				}
				reportPass(
						test,
						"Maximum price set as "
								+ maximumPrice.getAttribute("value"));
			} catch (Exception e) {
				// TODO: handle exception
				// e.printStackTrace();
				reportFail(test, e.getMessage());
			}

		} else {
			test.log(
					Status.INFO,
					"Available maximum price is "
							+ maximumPrice.getAttribute("value"));
		}
		JavaScriptUtil.waitForPageLoad();
		JavaScriptUtil.scrollToPageTop();
		/*
		 * new WebDriverWait(driver, 20).until(ExpectedConditions
		 * .visibilityOf(selectedOptions_price));
		 * 
		 * System.out.println(selectedOptions_price.getText() + "\n" +
		 * maximumPrice.getAttribute("value") + "\n" +
		 * minimumPrice.getAttribute("value"));
		 */
		/*
		 * WebElement slider =
		 * driver.findElement(By.xpath("//div[@class='connect']")); Point point
		 * = slider.getLocation();
		 * 
		 * int xcoordinate1 = point.getX(); System.out.println(xcoordinate1);
		 * int ycoordinate1 = point.getY(); System.out.println(ycoordinate1);
		 * 
		 * int width = slider.getSize().getWidth(); System.out.println(width);
		 * int height = slider.getSize().getHeight();
		 * System.out.println(height);
		 * 
		 * Point pointAgain = priceSliderLowerHandle.getLocation(); int
		 * xcoordinate = pointAgain.getX(); System.out.println(xcoordinate); int
		 * ycoordinate = pointAgain.getY(); System.out.println(ycoordinate);
		 * 
		 * Point pointNew = priceSliderUpperHandle.getLocation(); int
		 * xcoordinate2 = pointNew.getX(); System.out.println(xcoordinate2); int
		 * ycoordinate2 = pointNew.getY(); System.out.println(ycoordinate2);
		 */

	}

	public void clearPrice(ExtentTest test) {
		try {
			clearFilter(clearPrice);
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			this.viewPrice();

			if (!(slider.getAttribute("data-max").equals(
					maximumPrice.getAttribute("value")) && slider.getAttribute(
					"data-min").equals(minimumPrice.getAttribute("value")))) {
				reportFail(test, "Unable to clear Filter - Price");
			}
		}

	}
	
	/**********************************************************************************************/
	/*                                                                                            */
	/**********************************************************************************************/

	public void setCheckBox(List<WebElement> listCheckboxes,
			WebElement selectedOption, String mySelect, ExtentTest test) {

		try {
			ElementUtil.selectByAttribute(listCheckboxes, "value", mySelect);
			// JavaScriptUtil.scrollToPageTop();
			new WebDriverWait(driver, 60).until(ExpectedConditions
					.visibilityOf(selectedOption));
			/*
			 * System.out.println(selectedOption.getAttribute("data-option-name")
			 * ); System.out.println(mySelect);
			 */
			if (((selectedOption.getAttribute("data-option-name")
					.equals(mySelect)))) {
				reportPass(test, mySelect + " is successfully set");
			} else {
				reportFail(test, mySelect + " is not set");
			}

		} catch (TimeoutException e) {
			// TODO: handle exception
			reportFail(test, mySelect + " is not available");
		}
		// JavaScriptUtil.waitForPageLoad();
	}

	public boolean anyCheckboxSelected(List<WebElement> listCheckbox) {

		boolean isCheckboxSelected = false;
		for (WebElement checkbox : listCheckbox) {
			if (checkbox.isSelected()) {
				isCheckboxSelected = true;
			}
		}
		return isCheckboxSelected;
	}

	/**********************************************************************************************/
	/*                                                                                            */
	/**********************************************************************************************/

	public void excludeOutOfStocks(Boolean inStockOnly, ExtentTest test) {


		JavaScriptUtil.waitForPageLoad();

		if (inStockOnly) {
			test.log(Status.INFO, "Excluding out of stock items");
			try {

				ElementUtil.click(excludeOutOfStocks);

				new WebDriverWait(driver, 30).until(ExpectedConditions
						.visibilityOf(selectedOptions_inStockOnly));

				if ((excludeOutOfStocks.isSelected() && selectedOptions_inStockOnly
						.getAttribute("data-option-key")
						.equals("In Stock Only"))) {
					reportPass(test, "In Stock Only");
				}

			} catch (Exception e) {
				// TODO: handle exception
				reportFail(test, e.getMessage());
			}
		} else {
			reportPass(test, "Search results contain out of stock items also");
		}
	}

	public void clearInStockOnly() {
		try {
			clearFilter(clearInStockOnly);
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			if (excludeOutOfStocks.isSelected()) {
				reportFail(logger, "Unable to clear Exclude Out of Stocks");
			}
		}
	}

	/**********************************************************************************************/
	/*                                                                                            */
	/**********************************************************************************************/

	public void sortBy(String mySort, ExtentTest test) {

		test.log(Status.INFO, "Sort By - " + mySort);

		JavaScriptUtil.waitForPageLoad();

		try {
			ElementUtil.click(sortBy);
			ElementUtil.selectByAttribute(sortingOptions, "data-key", mySort);
			if ((selectedOptions_sorting.getAttribute("value")).equals(mySort)) {
				reportPass(test, "Search results sorted");
			}
		} catch (Exception e) {
			// TODO: handle exception
			reportFail(test, e.getMessage());
		}

	}

	/**********************************************************************************************/
	/*                                                                                            */
	/**********************************************************************************************/

	
	public void clearFilter(WebElement clearMe) {
		ElementUtil.click(selectedTab);
		ElementUtil.click(clearMe);
		JavaScriptUtil.waitForPageLoad();
		JavaScriptUtil.scrollToPageTop();

	}
	
	/**********************************************************************************************/

}
