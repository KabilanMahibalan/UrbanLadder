package com.urbanladder.pages;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.urbanladder.base.BaseUI;
import com.urbanladder.base.NavigationMenu;
import com.urbanladder.utils.BrowserFactory;
import com.urbanladder.utils.ElementUtil;

public class ProductPage extends BaseUI {

	public NavigationMenu navigationMenu;

	/**********************************************************************************************/
	/* P R O D U C T S -- I M A G E */
	/**********************************************************************************************/

	@FindBy(how = How.XPATH, xpath = "//div[@class='orbit-container current']//li[1]/img")
	private static WebElement productImage;

	/**********************************************************************************************/
	/* P R O D U C T S -- P R O P E R T I E S T A B */
	/**********************************************************************************************/

	@FindBy(how = How.XPATH, xpath = "//a[contains(text(),'Properties')]")
	private static WebElement propertiesTab;

	@FindBy(how = How.XPATH, xpath = "//ul[@id='product-properties']/li")
	private static List<WebElement> properties;

	@FindBy(how = How.XPATH, xpath = "//span[@itemprop = 'sku']")
	private static WebElement property_skuCode;

	@FindBy(how = How.XPATH, xpath = "//span[@class='variant_name']")
	private static WebElement property_name;

	@FindBy(how = How.XPATH, xpath = "//span[@itemprop = 'price']")
	private static WebElement property_price;

	@FindBy(how = How.XPATH, xpath = "//span[@itemprop = 'dimensions']")
	private static WebElement property_dimensions;

	/**********************************************************************************************/

	public ProductPage(ExtentTest logger, WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		this.logger = logger;
		navigationMenu = PageFactory.initElements(driver, NavigationMenu.class);

	}

	public void closeProductPage() {
		BrowserFactory.tearDown();
	}

	public void openPropertiesTab() {
		ElementUtil.click(propertiesTab);
	}

	public String getPropertySKUCode() {
		return property_skuCode.getText();
	}

	public String getPropertyName() {
		return property_name.getText();
	}

	public Double getPropertyPrice() {
		return Double.parseDouble(property_price.getText());
	}

	public String getPropertyDimensions() {	
		String dimensions = null;
		try {
			dimensions = property_dimensions.getText();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
		}
		return dimensions;
	}

	public String getProductImage() {
		String imagePath = "./src/test/resources/productImages/"
				+ getPropertySKUCode() + System.currentTimeMillis() + ".png";
		ElementUtil.storeImage(productImage, imagePath);
		return imagePath;
	}

	public Object[] getProductProperties() {

		child = logger.createNode("Product Details");
		test.set(child);
		/*
		 * for (int i = 0; i < data.length; i++) { System.out.println(data[i]);
		 * }
		 */
		try {
			child.log(Status.INFO, "SKU Code: " + getPropertySKUCode());
			child.log(Status.INFO, "Name: " + getPropertyName());
			child.log(Status.INFO, "MRP: Rs." + getPropertyPrice());
			child.log(Status.INFO, "Dimensions: " + getPropertyDimensions());
			reportPass(child, "Product details are logged.");
		} catch (Exception e) {
			// TODO: handle exception
			reportFail(child, e.getMessage());
		}
				/*
		 * getProductImage(); try { child.addScreenCaptureFromPath(imagePath); }
		 * catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */   
		Object[] data = { getPropertySKUCode(), getPropertyName(),
				getPropertyPrice(), getPropertyDimensions() };

		return data;
	}
}
