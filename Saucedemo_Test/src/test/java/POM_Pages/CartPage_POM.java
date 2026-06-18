package POM_Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import helper.BasePage;

public class CartPage_POM extends BasePage {
	private Logger log = LogManager.getLogger(CartPage_POM.class);
	public CartPage_POM(WebDriver wd) {
		super(wd);
	}
	@FindBy(id = "remove-sauce-labs-bike-light")WebElement remove_btn;
@FindBy(id = "checkout")WebElement checkout_btn;
@FindBy(id = "continue-shopping")WebElement continue_btn;

public void click_on_remove_button() {
	remove_btn.click();
	log.info("product remove from cart");
}

public void click_on_checkout_button() {
	checkout_btn.click();
	log.info("naviagate to checkout 2nd page");
}
public void click_on_continue_shopping_button() {
	continue_btn.click();
	log.info("navigate to home page or product page");
}
}
