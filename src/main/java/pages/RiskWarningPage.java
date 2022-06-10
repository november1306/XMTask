package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RiskWarningPage extends BaseMenuPage{

    By warningContainer = By.cssSelector(".mt-350");

    protected RiskWarningPage(WebDriver driver) {
        super(driver);
        waitOnPage();
    }

    private void waitOnPage() {
        WebDriverWait wait = new WebDriverWait(driver, 15, 3000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(warningContainer));
    }
}
