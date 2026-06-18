package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class ProductPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void addBookToCart(String bookName) throws InterruptedException {
        try {
            // ✅ Step 1: Go to Books category
            WebElement booksLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Books")));
            booksLink.click();
            Thread.sleep(1000); // pause after category click

            // ✅ Step 2: Click on product link
            WebElement bookLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(bookName)));
            bookLink.click();
            Thread.sleep(1000); // pause after product click

            // ✅ Step 3: Click Add to Cart
            WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[value='Add to cart']")));
            addToCartBtn.click();
            Thread.sleep(1000); // pause after add to cart

            // ✅ Step 4: Handle alert if appears
            try {
                WebDriverWait alertWait = new WebDriverWait(driver, Duration.ofSeconds(3));
                Alert alert = alertWait.until(ExpectedConditions.alertIsPresent());
                System.out.println("Alert detected: " + alert.getText());
                alert.dismiss();

                Thread.sleep(500); // pause before retry

                // Retry add to cart
                addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[value='Add to cart']")));
                addToCartBtn.click();
                System.out.println("Retried adding product to cart.");
            } catch (TimeoutException e) {
                System.out.println("No alert appeared, product added successfully.");
            }

        } catch (Exception e) {
            // ✅ Step 5: Fallback → refresh and retry
            System.out.println("❌ Initial add failed, retrying after refresh...");
            driver.navigate().refresh();
            Thread.sleep(1500); // pause after refresh

            try {
                WebElement booksLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Books")));
                booksLink.click();
                Thread.sleep(1000);

                WebElement bookLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(bookName)));
                bookLink.click();
                Thread.sleep(1000);

                WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[value='Add to cart']")));
                addToCartBtn.click();
                Thread.sleep(1000);

                System.out.println("Product added after refresh.");
            } catch (Exception ex) {
                throw new RuntimeException("Failed to add product even after refresh: " + ex.getMessage());
            }
        }
    }
}
