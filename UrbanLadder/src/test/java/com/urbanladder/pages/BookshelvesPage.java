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
import com.urbanladder.utils.ExcelUtil;
import com.urbanladder.utils.JavaScriptUtil;

public class BookshelvesPage extends BaseUI {

	public NavigationMenu navigationMenu;
	public Filters filters;
	ProductPage bookshelvesProductPage;
	Map<String, Object[]> productData;

	/**********************************************************************************************/
	/* F I L T E R S -- S T O R A G E T Y P E */
	/**********************************************************************************************/

	@FindBy(how = How.XPATH, xpath = "//li[@data-group='storage type']")
	private static WebElement storageType;

	@FindBy(how = How.XPATH, xpath = "//li[@data-group='storage type']//li[@class = 'option ']/input")
	private static List<WebElement> storageTypeCheckBoxes;

	@FindBy(how = How.XPATH, xpath = "//div[@class = 'selected-options']//li[@data-filter-name = 'storage_type']")
	private static WebElement selectedOptions_storageType;

	@FindBy(how = How.XPATH, xpath = "//li[@data-filter-name = 'storage_type']/span[@class='close icofont icofont-cross']")
	private static WebElement clearStorageType;

	/**********************************************************************************************/
	/* F I L T E R S -- T Y P E */
	/**********************************************************************************************/

	@FindBy(how = How.XPATH, xpath = "//li[@data-group = 'type']")
	private static WebElement type;

	@FindBy(how = How.XPATH, xpath = "//li[@data-group = 'type']//li[@class = 'option ']/input")
	private static List<WebElement> typeCheckBoxes;

	@FindBy(how = How.XPATH, xpath = "//div[@class = 'selected-options']//li[@data-filter-name = 'bookshelf_format']")
	private static WebElement selectedOptions_type;

	@FindBy(how = How.XPATH, xpath = "//li[@data-filter-name = 'bookshelf_format']/span[@class='close icofont icofont-cross']")
	private static WebElement clearType;

	/**********************************************************************************************/
	/* F I L T E R S -- M A T E R I A L */
	/**********************************************************************************************/
	@FindBy(how = How.XPATH, xpath = "//li[@data-group = 'material/finish']")
	private static WebElement materialAndFinish;

	@FindBy(how = How.XPATH, xpath = "//li[@data-group = 'material/finish']//li[@class = 'option ' or @class = 'option']//input")
	private static List<WebElement> materialAndFinishCheckBoxes;

	@FindBy(how = How.XPATH, xpath = "//div[@class = 'selected-options']//li[@data-filter-name = 'frame_material']")
	private static WebElement selectedOptions_frameMaterial;

	@FindBy(how = How.XPATH, xpath = "//div[@class = 'selected-options']//li[@data-filter-name = 'primary_finishes']")
	private static WebElement selectedOptions_primaryFinish;

	@FindBy(how = How.XPATH, xpath = "//li[@data-filter-name = 'frame_material']/span[@class='close icofont icofont-cross']")
	private static WebElement clearFrameMaterial;

	@FindBy(how = How.XPATH, xpath = "//li[@data-filter-name = 'primary_finishes']/span[@class='close icofont icofont-cross']")
	private static WebElement clearPrimaryFinish;

	
	/**********************************************************************************************/
	/* F I L T E R S -- S E L E C T E D O P T I O N S */
	/**********************************************************************************************/

	@FindBy(how = How.ID, id = "topnav_wrapper")
	private static WebElement pageTopWrapper;

	@FindBy(how = How.XPATH, xpath = "//div[@class = 'optionsblock']")
	private static WebElement optionsBlock;

	/**********************************************************************************************/
	/* B O O K S H E L V E S -- P R O D U C T S */
	/**********************************************************************************************/

	@FindBy(how = How.XPATH, xpath = "//a[@class = 'product-title-block']")
	private static List<WebElement> productList;

	/**********************************************************************************************/

	public BookshelvesPage(WebDriver driver, ExtentTest logger) {
		this.driver = driver;
		this.logger = logger;
		navigationMenu = PageFactory.initElements(driver, NavigationMenu.class);
		filters = PageFactory.initElements(driver, Filters.class);
	}

	/**********************************************************************************************/
	/*                                                                                            */
	/**********************************************************************************************/

	public void logFilters() {
		logger = report.createTest("BOOKSHELVES - FILTERS");
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
	/*                                                                                            */
	/**********************************************************************************************/

	public void viewStorageType() {
		ElementUtil.click(storageType);
	}

	public void setStorageType(String myStorageType) {
		if (myStorageType.matches(".*[a-zA-Z]+.*")) {
			child = logger.createNode("FILTERS - Storage Type");
			test.set(child);
			child.log(Status.INFO, "Filters - Storage Type: " + myStorageType);

			try {
				JavaScriptUtil.waitForPageLoad();
				ElementUtil.click(optionsBlock);
				this.viewStorageType();
				filters.setCheckBox(storageTypeCheckBoxes, selectedOptions_storageType,
						myStorageType, child);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				reportFail(child, e.getMessage());
			}
		}
	}

	public void clearStorageType() {
		try {
			filters.clearFilter(clearStorageType);
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			this.viewStorageType();
			if (filters.anyCheckboxSelected(storageTypeCheckBoxes)) {
				reportFail(logger, "Unable to clear Filter - Storage Type");
			}
		}

	}

	/**********************************************************************************************/
	/*                                                                                            */
	/**********************************************************************************************/

	public void viewType() {
		ElementUtil.click(type);
	}

	public void setType(String bookshelfFormat) {

		if (bookshelfFormat.matches(".*[a-zA-Z]+.*")) {
			child = logger.createNode("FILTERS - Type");
			test.set(child);
			child.log(Status.INFO, "Filters - Type: " + bookshelfFormat);

			try {
				JavaScriptUtil.waitForPageLoad();
				ElementUtil.click(optionsBlock);
				this.viewType();
				filters.setCheckBox(typeCheckBoxes, selectedOptions_type,
						bookshelfFormat, child);
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
			if (filters.anyCheckboxSelected(typeCheckBoxes)) {
				reportFail(logger, "Unable to clear storage type filter");
			}
		}
	}

	/**********************************************************************************************/
	/*                                                                                            */
	/**********************************************************************************************/

	public void viewMaterialAndFinish() {
		ElementUtil.click(materialAndFinish);
	}

	public void setMaterialAndFinish(String frameMaterial, String primaryFinish) {

		if (frameMaterial.matches(".*[a-zA-Z]+.*")
				|| primaryFinish.matches(".*[a-zA-Z]+.*")) {
			child = logger.createNode("FILTERS - Material / Finish");
			test.set(child);

			try {
				if (frameMaterial.matches(".*[a-zA-Z]+.*")) {
					JavaScriptUtil.waitForPageLoad();
					ElementUtil.click(optionsBlock);
					this.viewMaterialAndFinish();
					child.log(Status.INFO, "Filters - Frame Material: "
							+ frameMaterial);
					filters.setCheckBox(materialAndFinishCheckBoxes,
							selectedOptions_frameMaterial, frameMaterial, child);
				}
				if (primaryFinish.matches(".*[a-zA-Z]+.*")) {
					JavaScriptUtil.waitForPageLoad();
					ElementUtil.click(pageTopWrapper);
					this.viewMaterialAndFinish();
					child.log(Status.INFO, "Filters - Primary Finish: "
							+ primaryFinish);
					filters.setCheckBox(materialAndFinishCheckBoxes,
							selectedOptions_primaryFinish, primaryFinish, child);
				}
			} catch (Exception e) {
				// TODO: handle exception
				reportFail(child, e.getMessage());
			}
		}
	}

	public void clearFrameMaterial() {
		try {
			filters.clearFilter(clearFrameMaterial);
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			this.viewMaterialAndFinish();
			if (filters.anyCheckboxSelected(materialAndFinishCheckBoxes)) {
				reportFail(logger, "Unable to clear Filter - Frame Material");
			}
		}
	}

	public void clearPrimaryFinish() {
		try {
			filters.clearFilter(clearPrimaryFinish);
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			this.viewMaterialAndFinish();
			if (filters.anyCheckboxSelected(materialAndFinishCheckBoxes)) {
				reportFail(logger, "Unable to clear Filter - Frame Material");
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
		clearStorageType();
		clearType();
		clearFrameMaterial();
		clearPrimaryFinish();
		clearInStockOnly();
		BrowserFactory.refreshPage();
		// sortBy("default");
	}

	/**********************************************************************************************/

	public ProductPage viewProduct(int i) {

		String myProduct = productList.get(i).getAttribute("title");
		
		logger = report.createTest("BOOKSHELVES - "
				+ myProduct.substring(0, myProduct.indexOf('(')));
		parent.set(logger);

		ElementUtil.click(productList.get(i));
		BrowserFactory
				.switchTab(myProduct.substring(0, myProduct.indexOf('(')));
		JavaScriptUtil.waitForPageLoad();


		bookshelvesProductPage = new ProductPage(logger, driver);
		PageFactory.initElements(driver, bookshelvesProductPage);
		return bookshelvesProductPage;
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
				bookshelvesProductPage = this.viewProduct(i);
				bookshelvesProductPage.openPropertiesTab();
				productData.put(i + "",
						bookshelvesProductPage.getProductProperties());
				bookshelvesProductPage.closeProductPage();
				BrowserFactory.switchTab("Bookshelves");
				
		}

		return productData;
	}
	
	public void printProductDetails(){
		ExcelUtil.writeExcel(productData, "productSheets");
	}
}
