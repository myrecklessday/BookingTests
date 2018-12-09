package elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SearchFlightsForm {
    private WebDriver driver;
    private WebDriverWait wait;

    public SearchFlightsForm(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }

    private static final By DEPARTURE_CITY_LOCATOR = By.xpath("//div[contains(@class, 'airportBlock')]//input[@name = 'origin']");
    private static final By ARRIVAL_CITY_LOCATOR = By.xpath("//div[contains(@class, 'airportBlock')]//input[@name = 'destination']");
    private static final String FLIGHT_DAY_LOCATOR =
            "//div[contains(@class, 'col-month') and (contains(@id, '%s'))]//div[@class = 'day' and text() = '%s']";
    private static final By CALENDAR_LOCATOR = By.xpath("//div[contains(@class, 'single-date-picker')]");
    private static final By SEARCH_BUTTON_LOCATOR =
            By.xpath("//div[contains(@class, 'fieldBlock')]/button[contains(@class, 'searchButton')]");
    private static final By DEPARTURE_AIRPORTS_LIST_LOCATOR =
            By.xpath("//div[contains(@id, 'origin-smartbox-dropdown')]//li");
    private static final By ARRIVAL_AIRPORTS_LIST_LOCATOR =
            By.xpath("//div[contains(@id, 'destination-smartbox-dropdown')]//li");

    public WebElement waitForElementVisible(WebElement element){
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    private void selectDates(String departureDateStr, String arrivalDateStr) throws ParseException {
        SimpleDateFormat defaultDateFormat =  new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat yearMonthDateFormat = new SimpleDateFormat("yyyyMM");
        SimpleDateFormat dayDateFormat = new SimpleDateFormat("d");

        Date departureDate = defaultDateFormat.parse(departureDateStr);
        Date arrivalDate = defaultDateFormat.parse(arrivalDateStr);
        WebElement calendar = driver.findElement(CALENDAR_LOCATOR);
        calendar.click();

        driver.findElement(By.xpath(
                String.format(FLIGHT_DAY_LOCATOR, yearMonthDateFormat.format(departureDate), dayDateFormat.format(departureDate))
        )).click();

        calendar.click();

        WebElement arrivalDay = driver.findElement(By.xpath(
                String.format(FLIGHT_DAY_LOCATOR, yearMonthDateFormat.format(arrivalDate), dayDateFormat.format(arrivalDate))
        ));
        waitForElementVisible(arrivalDay);
        arrivalDay.click();

    }

    private void selectCities(String departureCity, String arrivalCity){
        WebElement departure = driver.findElement(DEPARTURE_CITY_LOCATOR);
        departure.clear();
        waitForElementVisible(departure);
        departure.sendKeys(departureCity);
        List<WebElement> departureAirportsList = driver.findElements(DEPARTURE_CITY_LOCATOR);
        departureAirportsList.get(0).click();
        WebElement arrival = driver.findElement(ARRIVAL_CITY_LOCATOR);
        arrival.clear();
        waitForElementVisible(arrival);
        arrival.sendKeys(arrivalCity);
        List<WebElement> arrivalAirportsList = driver.findElements(ARRIVAL_AIRPORTS_LIST_LOCATOR);
        arrivalAirportsList.get(0).click();
    }

    //TODO: delete sleep (needed to do captcha)
    public void initSearch(String departureCity, String arrivalCity, String departureDateStr, String arrivalDateStr) throws ParseException {
        selectCities(departureCity, arrivalCity);
        selectDates(departureDateStr, arrivalDateStr);
        driver.findElement(SEARCH_BUTTON_LOCATOR).click();
        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
