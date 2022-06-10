package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public abstract  class BaseMenuPage {

    protected WebDriver driver;

    By cookieModal = By.cssSelector("#cookieModal");
    By acceptAll = By.cssSelector("button.js-acceptDefaultCookie.gtm-acceptDefaultCookieFirstVisit");
    By researchAndEduacation = By.cssSelector("li.main_nav_research>a");
    By economicCalendar = By.cssSelector(".dropdown a[href*='/economicCalendar']");

    protected BaseMenuPage(WebDriver driver) {
        this.driver = driver;
    }

    public void acceptAllCookies() {
        driver.findElement(cookieModal).findElement(acceptAll).click();
    }

    private BaseMenuPage toggleResearchAndEducation() {
        driver.findElement(researchAndEduacation).click();
        return this;
    }

    public EconomicCalendar openEconomicCalendar() {
        toggleResearchAndEducation(); //isToggled() check is not needed here
        driver.findElement(economicCalendar).click();
        return new EconomicCalendar(driver);
    }
}
