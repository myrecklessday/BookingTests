package elements;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SearchHotelsForm {
    private WebDriver driver;

    public SearchHotelsForm(WebDriver driver){
        this.driver = driver;
    }

    private static final By SEARCH_BUTTON_LOCATOR = By.className("sb-searchbox__button");
    private static final By SEARCH_FIELD_LOCATOR = By.id("ss");
    private static final By DATES_FIELD_LOCATOR = By.cssSelector(".xp__dates");
    private static final By GUESTS_FIELD_LOCATOR = By.id("xp__guests__toggle");
    private static final By ROOMS_NUMBER_LOCATOR = By.id("no_rooms");
    private static final By ADULTS_NUMBER_LOCATOR = By.id("group_adults");
    private static final By CHILDREN_NUMBER_LOCATOR = By.id("group_children");
    private static final By ADD_CHILDREN_BUTTON =
            By.xpath("//div[contains(@class, 'sb-group-children')]//button[contains(@class, 'stepper__add-button')]");
    private static final By CHILDREN_AGE_LOCATOR = By.name("age");

    private static final String DATES_LOCATOR = "//td[@data-date = 'Date']";


    private void chooseDates(String arrivalDate, String departureDate){
        driver.findElement(DATES_FIELD_LOCATOR).click();
        driver.findElement(By.xpath(DATES_LOCATOR.replace("Date", arrivalDate))).click();
        driver.findElement(By.xpath(DATES_LOCATOR.replace("Date", departureDate))).click();
    }

    private void chooseGuests(String rooms, String adults, String children){
        driver.findElement(GUESTS_FIELD_LOCATOR).click();

        //elements for choosing quantity can have type select or type input range after tests launch
        if (driver.findElement(ROOMS_NUMBER_LOCATOR).getTagName().contentEquals("select")) {
            Select roomsDropdown = new Select(driver.findElement(ROOMS_NUMBER_LOCATOR));
            roomsDropdown.selectByVisibleText(rooms);
            Select adultsDropdown = new Select(driver.findElement(ADULTS_NUMBER_LOCATOR));
            adultsDropdown.selectByVisibleText(adults);
            Select childrenDropdown = new Select(driver.findElement(CHILDREN_NUMBER_LOCATOR));
            childrenDropdown.selectByVisibleText(children);
        } else {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("var rooms_input = document.getElementById('no_rooms'); rooms_input.value = arguments[0];" +
                            "var adults_input = document.getElementById('group_adults'); adults_input.value = arguments[1];" +
                            "var children_input = document.getElementById('group_children'); children_input.value = arguments[2];",
                    rooms, adults, children);
        }
    }

    private void chooseChildrenAge(String age) {
        //elements for choosing quantity can have type select or type input range after tests launch
        if (!driver.findElement(ROOMS_NUMBER_LOCATOR).getTagName().contentEquals("select")) {
            driver.findElement(ADD_CHILDREN_BUTTON).click();
        }
        Select childAgeDropdown = new Select(driver.findElement(CHILDREN_AGE_LOCATOR));
        childAgeDropdown.selectByValue(age);
    }

    public void initSearch(String place, String arrivalDate, String departureDate, String rooms, String adults, String children){
        WebElement searchField = driver.findElement(SEARCH_FIELD_LOCATOR);
        if (!searchField.getAttribute("value").equals("")){
            searchField.clear();
        }
        searchField.sendKeys(place);
        chooseDates(arrivalDate, departureDate);
        chooseGuests(rooms, adults, children);
        driver.findElement(SEARCH_BUTTON_LOCATOR).click();
    }

    public void initSearch(String place, String arrivalDate, String departureDate, String rooms, String adults,
                           String children, String childrenAge){
        WebElement searchField = driver.findElement(SEARCH_FIELD_LOCATOR);
        if (!searchField.getAttribute("value").equals("")){
            searchField.clear();
        }
        searchField.sendKeys(place);
        chooseDates(arrivalDate, departureDate);
        chooseGuests(rooms, adults, children);
        chooseChildrenAge(childrenAge);
        driver.findElement(SEARCH_BUTTON_LOCATOR).click();
    }

}
