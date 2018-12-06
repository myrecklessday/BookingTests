package elements;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class SearchField {
    private WebElement selfElement;

    public SearchField(WebElement selfElement){
        this.selfElement = selfElement;
    }

    private static By searchButton_locator = By.className("sb-searchbox__button");
    private static By searchField_locator = By.id("ss");
    private static By datesField_locator = By.cssSelector(".xp__dates");
    private static By guestsField_locator = By.id("xp__guests__toggle");
    private static By roomsNumber_locator = By.id("no_rooms");
    private static By adultsNumber_locator = By.id("group_adults");
    private static By childrenNumber_locator = By.id("group_children");

    private static String dates_locator = "//td[@data-date = 'Date']";


    private void chooseDates(String arrivalDate, String departureDate){
        selfElement.findElement(datesField_locator).click();
        selfElement.findElement(By.xpath(dates_locator.replace("Date", arrivalDate))).click();
        selfElement.findElement(By.xpath(dates_locator.replace("Date", departureDate))).click();

    }

    private void chooseGuests(String rooms, String adults, String children){
        selfElement.findElement(guestsField_locator).click();
//        selfElement.findElement(roomsNumber_locator).click();
//        Select roomsDropdown = new Select(selfElement.findElement(roomsNumber_locator));
//        roomsDropdown.selectByVisibleText(rooms);

//        selfElement.findElement(roomsNumber_locator).sendKeys(rooms);

//        Select adultsDropdown = new Select(selfElement.findElement(adultsNumber_locator));
//        adultsDropdown.selectByVisibleText(adults);

//        selfElement.findElement(adultsNumber_locator).sendKeys(adults);

//        Select childrenDropdown = new Select(selfElement.findElement(childrenNumber_locator));
//        childrenDropdown.selectByVisibleText(children);

//        selfElement.findElement(childrenNumber_locator).sendKeys(children);

//        JavascriptExecutor js = (JavascriptExecutor)driver;
//        js.executeScript("var rooms_input = document.getElementById('no_rooms'); rooms_input.value = arguments[0];" +
//                "var adults_input = document.getElementById('group_adults'); adults_input.value = arguments[1];" +
//                "var children_input = document.getElementById('group_children'); children_input.value = arguments[2];",
//                rooms, adults, children);

//        selfElement.findElement(adultsNumber_locator).click();

//        selfElement.findElement(childrenNumber_locator).click();

    }

    public void initSearch(String place, String arrivalDate, String departureDate, String rooms, String adults, String children){
        selfElement.findElement(searchField_locator).sendKeys(place);
        chooseDates(arrivalDate, departureDate);
        selfElement.findElement(guestsField_locator).click();
        selfElement.findElement(searchButton_locator).click();
    }

}
