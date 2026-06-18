package POM_Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import helper.BasePage;

public class HomePage_ProductPage_POM extends BasePage {
	private Logger log = LogManager.getLogger(HomePage_ProductPage_POM.class);
	public HomePage_ProductPage_POM(WebDriver wd) {
		super(wd);
	}
	@FindBy(xpath = "/html/body/div/div/div/div[1]/div[1]/div[1]/div/div[1]/div/button")WebElement menu;
	@FindBy(partialLinkText = "Logout")WebElement lgout;
	@FindBy(id = "add-to-cart-sauce-labs-backpack")WebElement addcart;
	@FindBy(xpath = "/html/body/div/div/div/div[1]/div[1]/div[3]/a")WebElement cart;
	
	public void click_on_menu()
	{
		menu.click();
		log.info("Menu option displayed");
	}

	public void click_on_logout()
	{
		lgout.click();
		log.info("Navigate to login page");
	}

	public void click_on_add_to_cart_button()
	{
		addcart.click();
		log.info("Product added to cart");
	}

	public void click_on_cart_icon()
	{
		cart.click();
		log.info("Navigate to cart page");
	}
public void logout_process() throws InterruptedException
{
Thread.sleep(2000);
click_on_menu();
Thread.sleep(2000);
click_on_logout();
Thread.sleep(2000);
}
}