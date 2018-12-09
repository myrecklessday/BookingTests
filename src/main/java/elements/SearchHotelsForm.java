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

    //TODO: make final and uppercase
    private static final By searchButton_locator = By.className("sb-searchbox__button");
    private static By searchField_locator = By.id("ss");
    private static By datesField_locator = By.cssSelector(".xp__dates");
    private static By guestsField_locator = By.id("xp__guests__toggle");
    private static By roomsNumber_locator = By.id("no_rooms");
    private static By adultsNumber_locator = By.id("group_adults");
    private static By childrenNumber_locator = By.id("group_children");

    private static String dates_locator = "//td[@data-date = 'Date']";


    private void chooseDates(String arrivalDate, String departureDate){
        driver.findElement(datesField_locator).click();
        driver.findElement(By.xpath(dates_locator.replace("Date", arrivalDate))).click();
        driver.findElement(By.xpath(dates_locator.replace("Date", departureDate))).click();
    }

    private void chooseGuests(String rooms, String adults, String children){
        driver.findElement(guestsField_locator).click();

        //elements for choosing quantity can have type select or type input range after tests launch
        if (driver.findElement(roomsNumber_locator).getTagName().contentEquals("select")) {
            Select roomsDropdown = new Select(driver.findElement(roomsNumber_locator));
            roomsDropdown.selectByVisibleText(rooms);
            Select adultsDropdown = new Select(driver.findElement(adultsNumber_locator));
            adultsDropdown.selectByVisibleText(adults);
            Select childrenDropdown = new Select(driver.findElement(childrenNumber_locator));
            childrenDropdown.selectByVisibleText(children);
        } else {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("var rooms_input = document.getElementById('no_rooms'); rooms_input.value = arguments[0];" +
                            "var adults_input = document.getElementById('group_adults'); adults_input.value = arguments[1];" +
                            "var children_input = document.getElementById('group_children'); children_input.value = arguments[2];",
                    rooms, adults, children);
        }
    }

    public void initSearch(String place, String arrivalDate, String departureDate, String rooms, String adults, String children){
        WebElement searchField = driver.findElement(searchField_locator);
        if (!searchField.getAttribute("value").equals("")){
            searchField.clear();
        }
        searchField.sendKeys(place);
        chooseDates(arrivalDate, departureDate);
        chooseGuests(rooms, adults, children);
        driver.findElement(searchButton_locator).click();
    }

}
