package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class EconomicCalendarTable {

    By calendarContainer = By.cssSelector(".economic-calendar>iframe");
    By dateRange = By.cssSelector("#widgetFieldDateRange");
    By yesterday = By.cssSelector("a#timeFrame_yesterday");
    By today = By.cssSelector("a#timeFrame_today");
    By tomorrow = By.cssSelector("a#timeFrame_tomorrow");
    By thisWeek = By.cssSelector("a#timeFrame_thisWeek");
    By loader = By.cssSelector("div#economicCalendarLoading");

    WebDriverWait wait;
    private final WebDriver driver;

    EconomicCalendarTable(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10, 2000);
        waitOnPage();
    }

    public EconomicCalendarTable clickYesterday() {
        driver.switchTo().frame(0);
        driver.findElement(yesterday).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
        driver.switchTo().defaultContent();
        return this;
    }

    public EconomicCalendarTable clickToday() {
        driver.switchTo().frame(0);
        driver.findElement(today).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
        driver.switchTo().defaultContent();
        return this;
    }

    public EconomicCalendarTable clickTomorrow() {
        driver.switchTo().frame(0);
        driver.findElement(tomorrow).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
        driver.switchTo().defaultContent();
        return this;
    }

    public EconomicCalendarTable clickThisWeek() {
        driver.switchTo().frame(0);
        driver.findElement(thisWeek).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
        driver.switchTo().defaultContent();
        return this;
    }

    public List<LocalDate> getDateRange() {
        driver.switchTo().frame(0);
        List<LocalDate> dateList = parseDate(driver.findElement(dateRange).getText());
        driver.switchTo().defaultContent();
        return dateList;
    }

    private void waitOnPage() {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(calendarContainer));
        driver.switchTo().defaultContent();
    }

    private List<LocalDate> parseDate(String dateRange) {
        String[] dates = dateRange.split("-");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return Arrays.asList(
                LocalDate.parse(dates[0].trim(), formatter),
                LocalDate.parse(dates[1].trim(), formatter));
    }

}
