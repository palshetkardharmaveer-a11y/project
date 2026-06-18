package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;   
import java.util.List;                 
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class LoginPage {
    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String email, String password) throws InterruptedException {
        // If already logged in, skip
        List<WebElement> logoutLinks = driver.findElements(By.linkText("Log out"));
        if (!logoutLinks.isEmpty()) {
            System.out.println("Already logged in: " + email);
            return;
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Log in"))).click();

        // Small pause to allow page transition
        Thread.sleep(1000);

        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(password);

        // Pause before clicking login
        Thread.sleep(500);

        driver.findElement(By.cssSelector("input.login-button")).click();

        // Wait for logout link to confirm login success
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Log out")));
    }
}
