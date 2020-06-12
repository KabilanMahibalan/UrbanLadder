package com.urbanladder.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.urbanladder.base.BaseUI;
import com.urbanladder.pages.GiftCardsPage;
import com.urbanladder.utils.JsonUtil;

public class GiftCardsTest extends BaseUI {

	GiftCardsPage giftCardsPage;
	
	@DataProvider
	public Object[][] getGiftCardData() {
		return JsonUtil.getdata(properties.getProperty("jsonPath"),
				"GiftCard Data", 2, 10);
	}

	@Test(dataProvider = "getGiftCardData")
	public void sendGiftCard(String occasion, String amount,
			String monthAndYear, String day, String recipientName,
			String recipientEmail, String customerName, String customerEmail,
			String customerMobileNumber, String messageString) {

		giftCardsPage = homePage.navigateGiftCards();

		giftCardsPage.logGiftCards();
		giftCardsPage.chooseOccasion(occasion);
		giftCardsPage.customizeYourGift(Double.parseDouble(amount),
				monthAndYear, day);
		giftCardsPage.fillRecipientCustomerForm(recipientName, recipientEmail,
				customerName, customerEmail, customerMobileNumber,
				messageString);
		giftCardsPage.proceedToPayment();

	}
}
