package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Scroller;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;

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

    public RiskWarningPage openRiskWarning() {
        Scroller.scrollTo(riskWarning, driver)
                .click();
        return new RiskWarningPage(driver);
    }

    public void openRiskDisclosure() {
        String originalWindow = driver.getWindowHandle();

        driver.findElement(riskDisclosure).click();
        wait.until(numberOfWindowsToBe(2));
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }


    private void waitOnPage() {
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.presenceOfElementLocated(calendarWrapper));
    }


}
