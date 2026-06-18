package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class OrderConfirmationPage {
    private WebDriverWait wait;

    // DemoWebShop locators
    private By successMessage = By.xpath("//div[@class='title']/strong");
    private By orderNumberLocator = By.xpath("//ul[@class='details']/li[1]");

    public OrderConfirmationPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ✅ Verify order confirmation
    public boolean isOrderConfirmed() throws InterruptedException {
        try {
            // Small pause before checking success message
            Thread.sleep(1000);

            WebElement msgElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(successMessage)
            );
            String text = msgElement.getText().trim();

            // Pause after fetching text
            Thread.sleep(500);

            return text.equalsIgnoreCase("Your order has been successfully processed!");
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ✅ Get success message text
    public String getConfirmationMessage() throws InterruptedException {
        try {
            Thread.sleep(1000);
            return wait.until(
                ExpectedConditions.visibilityOfElementLocated(successMessage)
            ).getText();
        } catch (TimeoutException e) {
            return "Success message not displayed.";
        }
    }

    // ✅ Get order number
    public String getOrderNumber() throws InterruptedException {
        try {
            Thread.sleep(1000);
            WebElement orderElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(orderNumberLocator)
            );
            String fullText = orderElement.getText();

            Thread.sleep(500);

            if (fullText.contains(":")) {
                return fullText.split(":")[1].trim();
            }
            return fullText;
        } catch (TimeoutException e) {
            return "Order number not found.";
        }
    }
}
