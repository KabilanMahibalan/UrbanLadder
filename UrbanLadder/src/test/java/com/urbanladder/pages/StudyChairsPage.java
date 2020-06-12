package com.urbanladder.pages;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.urbanladder.base.BaseUI;
import com.urbanladder.base.Filters;
import com.urbanladder.base.NavigationMenu;
import com.urbanladder.utils.BrowserFactory;
import com.urbanladder.utils.ElementUtil;
import com.urbanladder.utils.JavaScriptUtil;

public class StudyChairsPage extends BaseUI{
	
	public NavigationMenu navigationMenu;
	public Filters filters;
	ProductPage studyChairsProductPage;
	Map<String, Object[]> productData;
	
	/**********************************************************************************************/
	/* F I L T E R S -- W H E E L */
	/**********************************************************************************************/

	@FindBy(how = How.XPATH, xpath = "//li[@data-group='wheel']")
	private static WebElement wheel;

	@FindBy(how = How.XPATH, xpath = "//li[@data-group='wheel']//li/input")
	private static List<WebElement> wheelOptions;

	@FindBy(how = How.XPATH, xpath = "//div[@class='selected-options']//li[@data-filter-name='chair_wheels']")
	private static WebElement selectedOptions_wheelType;
	
	@FindBy(how = How.XPATH, xpath = "//li[@data-filter-name='chair_wheels']/span[@class='close icofont icofont-cross']")
	private static WebElement clearWheelType;
	

	/**********************************************************************************************/
	/* F I L T E R S -- T Y P E																	  */
	/**********************************************************************************************/

	@FindBy(how = How.XPATH, xpath = "//li[@data-group='type']")
	private static WebElement type;

	@FindBy(how = How.XPATH, xpath = "//div[@data-filter-name='study_chair_type']//input[contains(@id,'filters_study_chair_type')]")
	private static List<WebElement> typeOptions;

	@FindBy(how = How.XPATH, xpath = "//div[@class='selected-options']//li[@data-filter-name='study_chair_type']")
	private static WebElement selectedOptions_type;
	
	@FindBy(how = How.XPATH, xpath = "//li[@data-filter-name='study_chair_type']//span[@class='close icofont icofont-cross']")
	private static WebElement clearType;


	/**********************************************************************************************/
	/* F I L T E R S -- C O L O U R */
	/**********************************************************************************************/

	@FindBy(how = How.XPATH, xpath = "//li[@data-group='colour']")
	private static WebElement colour;

	@FindBy(how = How.XPATH, xpath = "//li[@data-group='colour']//li/input")
	private static List<WebElement> colourOptions;
	
	@FindBy(how = How.XPATH, xpath = "//div[@class='selected-options']//li[@data-filter-name='primary_colors']")
	private static WebElement selectedOptions_colourType;
	
	@FindBy(how = How.XPATH, xpath = "//li[@data-filter-name='primary_colors']/span[@class='close icofont icofont-cross']']")
	private static WebElement clearColourType;
	
	
	/**********************************************************************************************/
	/* F I L T E R S -- S E L E C T E D  O P T I O N S  */
	/**********************************************************************************************/
	
	@FindBy(how = How.XPATH, xpath = "//div[@class = 'optionsblock']")
	private static WebElement optionsBlock;
	
	/**********************************************************************************************/
	/* B O O K S H E L V E S -- P R O D U C T S */
	/**********************************************************************************************/

	@FindBy(how = How.XPATH, xpath = "//a[@class = 'product-title-block']")
	private static List<WebElement> productList;

	/**********************************************************************************************/
	

	public StudyChairsPage(WebDriver driver, ExtentTest logger) {
		this.driver = driver;
		this.logger = logger;
		navigationMenu = PageFactory.initElements(driver, NavigationMenu.class);
		filters = PageFactory.initElements(driver, Filters.class);
	}

	/**********************************************************************************************/
	/*                                                                                            */
	/**********************************************************************************************/

	public void logFilters(){
		logger = report.createTest("STUDY CHAIRS - FILTERS");
		parent.set(logger);
	}

	/**********************************************************************************************/
	/*                                                                                            */
	/**********************************************************************************************/

	public void viewPrice() {
		filters.viewPrice();
	}

	public void setPrice(String priceRange) {
		
		child = logger.createNode("FILTERS - Price");
		test.set(child);	
		filters.setPrice(priceRange, child);

	}

	public void clearPrice() {
		filters.clearPrice(logger);
	}

	/**********************************************************************************************/

	/**********************************************************************************************/
	/*  */
	/**********************************************************************************************/

	public void viewWheel() {
		ElementUtil.click(wheel);
	}

	public void setWheel(String myWheel) {
		if (myWheel.matches(".*[a-zA-Z]+.*")) {
			child = logger.createNode("FILTERS - Wheel");
			test.set(child);
			child.log(Status.INFO, "Filters - Wheel: " + myWheel);

			try {
				JavaScriptUtil.waitForPageLoad();
				ElementUtil.click(optionsBlock);
				this.viewWheel();
				filters.setCheckBox(wheelOptions, selectedOptions_wheelType, myWheel, child);
			} catch (Exception e) {
				e.printStackTrace();
				reportFail(child, e.getMessage());
			}
		}
	}

	public void clearWheelType() {
		try {
			filters.clearFilter(clearWheelType);
		} catch (NoSuchElementException e) {
			this.viewWheel();
			if(filters.anyCheckboxSelected(wheelOptions)){
				reportFail(logger, "Unable to clear Filter - Wheel Type");
			}
		}
	}
	                                                                                           
	/**********************************************************************************************/
	/*F I L T E R S -- C O L O U R																  */	
	/**********************************************************************************************/

	public void viewColour() {
		ElementUtil.click(colour);
	}

	public void setColour(String colour) {

		if (colour.matches(".*[a-zA-Z]+.*")) {
			child = logger.createNode("FILTERS - Colour");
			test.set(child);
			child.log(Status.INFO, "Filters - Colour: " + colour);

			try {
				JavaScriptUtil.waitForPageLoad();
				ElementUtil.click(optionsBlock);
				this.viewColour();
				filters.setCheckBox(colourOptions, selectedOptions_colourType,
						colour, child);
			} catch (Exception e) {
				// TODO: handle exception
				reportFail(child, e.getMessage());
			}
		}
	}

	public void clearColour() {
		
		try {
			filters.clearFilter(clearColourType);
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			this.viewColour();
			if(filters.anyCheckboxSelected(colourOptions)){
				reportFail(logger, "Unable to clear colour filter");
			}
		}
	}

	/**********************************************************************************************/
	
	/**********************************************************************************************/
	/**/
	/**********************************************************************************************/
	public void viewType() {
		ElementUtil.click(type);
	}

	public void setType(String type) {

		if (type.matches(".*[a-zA-Z]+.*")) {
			child = logger.createNode("FILTERS - Type");
			test.set(child);
			child.log(Status.INFO, "Filters - Type: " + type);

			try {
				JavaScriptUtil.waitForPageLoad();
				ElementUtil.click(optionsBlock);
				this.viewType();
				filters.setCheckBox(typeOptions, selectedOptions_type,
						type, child);
			} catch (Exception e) {
				// TODO: handle exception
				reportFail(child, e.getMessage());
			}
		}
	}

	public void clearType() {
		
		try {
			filters.clearFilter(clearType);
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			this.viewType();
			if(filters.anyCheckboxSelected(typeOptions)){
				reportFail(logger, "Unable to clear type filter");
			}
		}
	}

	
	/**********************************************************************************************/
	/*                                                                                            */
	/**********************************************************************************************/

	public void excludeOutOfStocks(Boolean inStockOnly) {

		child = logger.createNode("FILTERS - Exclude Out of Stocks");
		test.set(child);

		filters.excludeOutOfStocks(inStockOnly, child);
	}

	public void clearInStockOnly() {
		filters.clearInStockOnly();
	}

	/**********************************************************************************************/
	/*                                                                                            */
	/**********************************************************************************************/

	public void sortBy(String mySort) {

		child = logger.createNode("FILTERS - Sort By");
		test.set(child);
		
		filters.sortBy(mySort, child);
	}

	/**********************************************************************************************/

	public void clearAllFilters() {
		
		clearPrice();
		clearWheelType();
		clearType();
		clearColour();
		clearInStockOnly();
		BrowserFactory.refreshPage();
		//sortBy("default");
	}
	
	/**********************************************************************************************/
	
	public ProductPage viewProduct(int i) {

		String myProduct = productList.get(i).getAttribute("title");
		
		logger = report.createTest("STUDY CHAIRS - "
				+ myProduct.substring(0, myProduct.indexOf('(')));
		parent.set(logger);

		ElementUtil.click(productList.get(i));
		BrowserFactory
				.switchTab(myProduct.substring(0, myProduct.indexOf('(')));
		JavaScriptUtil.waitForPageLoad();


		studyChairsProductPage = new ProductPage(logger, driver);
		PageFactory.initElements(driver, studyChairsProductPage);
		return studyChairsProductPage;
		/*
		 * bookshelvesProductPage.getProductProperties();
		 * BrowserFactory.switchTab("Bookshelves");
		 * System.out.println(driver.getTitle());
		 */
	}

	public Map<String, Object[]> getProductDetails(int count) {
		productData = new TreeMap<String, Object[]>();
		
		if (count > productList.size()){
			count = productList.size();
		}
		
		for (int i = 0; i < count; i++) {
				studyChairsProductPage = this.viewProduct(i);
				studyChairsProductPage.openPropertiesTab();
				productData.put(i + "",
						studyChairsProductPage.getProductProperties());
				studyChairsProductPage.closeProductPage();
				BrowserFactory.switchTab("Bookshelves");
				
		}

		return productData;
	}
}