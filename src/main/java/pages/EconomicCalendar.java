package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EconomicCalendar extends BaseMenuPage {

    By calendarWrapper = By.cssSelector("div.economic-calendar");
    By riskWarning = By.cssSelector(".p-200 a[href*='/risk_warning']");
    By riskDisclosure = By.cssSelector("#risk-block a[href*='/docs/XMGlobal-Risk-Disclosures-for-Financial-Instruments.pdf']");


    public EconomicCalendarTable economicCalendarTable;
    WebDriverWait wait;

    public EconomicCalendar(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 10, 2000);
        waitOnPage();
        economicCalendarTable = new EconomicCalendarTable(driver);
    }

    public RiskWarning openRiskWarning() {
        scrollTo(riskWarning)
                .click();
        return new RiskWarning(driver);
    }

    public void openRiskDisclosure() {
        driver.findElement(riskDisclosure).click();
    }


    private void waitOnPage() {
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.presenceOfElementLocated(calendarWrapper));
    }

    private WebElement scrollTo(By locator) {
        WebElement element = driver.findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        js.executeScript("scrollBy(0, -250);");
        return element;
    }


}
