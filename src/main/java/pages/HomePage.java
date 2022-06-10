package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BaseMenuPage {
    By welcomeBanner = By.cssSelector("#hero-content");

    public HomePage(WebDriver driver) {
        super(driver);
        waitOnPage();
    }

    private void waitOnPage() {
        WebDriverWait wait = new WebDriverWait(driver, 15, 3000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeBanner));
    }

}
