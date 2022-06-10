package xm.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.EconomicCalendar;
import pages.HomePage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

class DateTest {
    private Logger log = LoggerFactory.getLogger(DateTest.class);
    static final String BASE_URL = "https://www.xm.com/";
    private WebDriver driver;

    HomePage homePage;
    EconomicCalendar economicCalendar;


    @BeforeEach
    void initDriver() {
        log.info("initialize Chrome driver ");
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("start-maximized");
        driver = new ChromeDriver(chromeOptions);
    }

    @Test
    void checkDateTest() {
        driver.get(BASE_URL);
        homePage = new HomePage(driver);
        homePage.acceptAllCookies();
        economicCalendar = homePage.openEconomicCalendar();

        List<LocalDate> yesterdayDates = economicCalendar.economicCalendarTable
                .clickYesterday()
                .getDateRange();

        List<LocalDate> todayDates = economicCalendar.economicCalendarTable
                .clickToday()
                .getDateRange();

        List<LocalDate> tomorrowDates = economicCalendar.economicCalendarTable
                .clickTomorrow()
                .getDateRange();

        List<LocalDate> thisWeekDates = economicCalendar.economicCalendarTable
                .clickThisWeek()
                .getDateRange();

        economicCalendar.openRiskDisclosure();
        economicCalendar.openRiskWarning();

        //TODO assertion sensitive to incorrect GMT time zone
        Assertions.assertAll(
                () -> Assertions.assertEquals(LocalDate.now().minusDays(1), yesterdayDates.get(0)
                        , "yesterday date is incorrect"),
                () -> Assertions.assertEquals(LocalDate.now().minusDays(1), yesterdayDates.get(1)
                        , "yesterday date is incorrect"),
                () -> Assertions.assertEquals(LocalDate.now(), todayDates.get(0)
                        , "today date is incorrect"),
                () -> Assertions.assertEquals(LocalDate.now(), todayDates.get(1)
                        , "today date is incorrect"),
                () -> Assertions.assertEquals(LocalDate.now().plusDays(1), tomorrowDates.get(0)
                        , "tomorrow date is incorrect"),
                () -> Assertions.assertEquals(LocalDate.now().plusDays(1), tomorrowDates.get(1)
                        , "tomorrow date is incorrect"),
                //TODO current week depends on us/eu locale but meh
                () -> Assertions.assertEquals(LocalDate.now().with(DayOfWeek.MONDAY).minusDays(1), thisWeekDates.get(0),
                        "this week date is incorrect"),
                () -> Assertions.assertEquals(LocalDate.now().with(DayOfWeek.SATURDAY), thisWeekDates.get(1),
                        "this week date is incorrect")
        );


    }


    @AfterEach
    void closeBrowser() {
        driver.quit();
    }
}
