package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage {
    WebDriver driver;

    // Locators
    By genderMale = By.id("gender-male");
    By firstNameField = By.id("FirstName");
    By lastNameField = By.id("LastName");
    By emailField = By.id("Email");
    By passwordField = By.id("Password");
    By confirmPasswordField = By.id("ConfirmPassword");
    By registerButton = By.id("register-button");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void registerUser(String firstName, String lastName, String email, String password) throws InterruptedException {
        driver.findElement(genderMale).click();
        Thread.sleep(500); // pause after gender select

        driver.findElement(firstNameField).sendKeys(firstName);
        Thread.sleep(500); // pause after first name

        driver.findElement(lastNameField).sendKeys(lastName);
        Thread.sleep(500); // pause after last name

        driver.findElement(emailField).sendKeys(email);
        Thread.sleep(500); // pause after email

        driver.findElement(passwordField).sendKeys(password);
        Thread.sleep(500); // pause after password

        driver.findElement(confirmPasswordField).sendKeys(password);
        Thread.sleep(500); // pause after confirm password

        driver.findElement(registerButton).click();
        Thread.sleep(1000); // pause after clicking register
    }
}
