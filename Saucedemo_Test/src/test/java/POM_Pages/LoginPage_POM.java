package POM_Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import helper.BasePage;

public class LoginPage_POM extends BasePage {
	private Logger log = LogManager.getLogger(LoginPage_POM.class);
public LoginPage_POM(WebDriver driver) {
	super(driver);
}
	@FindBy(id = "user-name")
	private WebElement usernameInput;
	
	@FindBy(id = "password")
	private WebElement passwordInput;
	
	@FindBy(id = "login-button")
	private WebElement loginButton;
	
	public void enterUsername(String user) {
		usernameInput.clear();
		usernameInput.sendKeys(user);
		log.info("Entered username: {}",user);
	}
	public void enterPassword(String pass) {
		passwordInput.clear();
		passwordInput.sendKeys(pass);
		log.info("Entered password");
	}
	public void clickLogin() {
		loginButton.click();
		
		log.info("Clicked login button");
	}
	public void login(String user, String pass) {
		enterUsername(user);
		enterPassword(pass);
		clickLogin();
	}
}
