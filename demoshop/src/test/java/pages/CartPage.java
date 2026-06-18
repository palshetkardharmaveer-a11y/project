package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class CartPage {
    private WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    // ✅ Go to Cart and proceed to checkout
    public void proceedToCheckout() {
        // Step 1: Navigate to Shopping Cart
        driver.findElement(By.linkText("Shopping cart")).click();

        // Step 2: Accept Terms of Service
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement terms = wait.until(ExpectedConditions.elementToBeClickable(By.id("termsofservice")));
        if (!terms.isSelected()) {
            terms.click();
        }

        // Step 3: Click Checkout
        driver.findElement(By.id("checkout")).click();

        // Step 4: Wait until Billing section is visible
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait1.until(ExpectedConditions.or(
            ExpectedConditions.presenceOfElementLocated(By.cssSelector("select#billing-address-select")),
            ExpectedConditions.presenceOfElementLocated(By.id("BillingNewAddress_FirstName"))
        ));
    }

    // Optional helper if you just want to go to Cart without checkout
    public void goToCart() {
        driver.findElement(By.linkText("Shopping cart")).click();
    }
}
