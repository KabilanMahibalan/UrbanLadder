package com.urbanladder.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.urbanladder.base.BaseUI;
import com.urbanladder.pages.BookshelvesPage;
import com.urbanladder.pages.StudyChairsPage;
import com.urbanladder.utils.ExcelUtil;

public class FurnituresTest extends BaseUI {

	BookshelvesPage bookshelves;
	StudyChairsPage studyChairs;

	@DataProvider
	public Object[][] getBookshelvesData() {
		Object[][] data = ExcelUtil.getTestData("bookshelves");
		return data;
	}

	@DataProvider
	public Object[][] getStudyChairsData() {
		Object[][] data = ExcelUtil.getTestData("studyChairs");
		return data;
	}

	@Test(dataProvider = "getBookshelvesData")
	public void getBookshelvesDetails(String price, String storageType,
			String bookshelfFormat, String frameMaterial, String basicFinish,
			boolean inStockOnly, String sortBy) {

		bookshelves = homePage.navigateBookshelves();

		bookshelves.clearAllFilters();
		bookshelves.logFilters();
		bookshelves.setPrice(price);
		bookshelves.setStorageType(storageType);
		bookshelves.setMaterialAndFinish(frameMaterial, basicFinish);
		bookshelves.setType(bookshelfFormat);
		bookshelves.excludeOutOfStocks(inStockOnly);
		bookshelves.sortBy(sortBy);
		bookshelves.getProductDetails(1);

	}

	@Test(dataProvider = "getStudyChairsData", dependsOnMethods = { "getBookshelvesDetails" })
	public void getStudyChairsDetails(String price, String wheel, String type,
			String colour, boolean inStockOnly, String sortBy) {

		studyChairs = homePage.navigateStudyChairs();

		studyChairs.clearAllFilters();
		studyChairs.logFilters();
		studyChairs.setPrice(price);
		studyChairs.setWheel(wheel);
		studyChairs.setColour(colour);
		studyChairs.setType(type);
		studyChairs.excludeOutOfStocks(inStockOnly);
		studyChairs.sortBy(sortBy);
		studyChairs.getProductDetails(3);
	}

}
