package pages;

import base.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class SearchHotelsResultsPage extends PageBase {
    public SearchHotelsResultsPage(WebDriver driver){
        super(driver);
    }

    @FindBy(id = "group_adults")
    private WebElement adultsNumber;

    @FindBy(id = "group_children")
    private WebElement childrenNumber;

    @FindBy(id = "no_rooms")
    private WebElement roomsNumber;

    @FindBy(xpath = "//img[@id = 'logo_no_globe_new_logo']")
    private WebElement mainPageLogo;

    @FindBy(xpath = "//div[contains(@class, 'sr_header')]/h2")
    private WebElement searchResultsHeader;

    @FindBy(xpath = "//div[@class = 'visuallyhidden' and contains(text(), 'Фильтры применены')]")
    private WebElement filterResultsMessage;

    @FindAll({@FindBy (xpath = "//div[contains(@class, 'sr_item_content')]")})
    private List<WebElement> resultsBlock;

    @FindAll({@FindBy (xpath = ".//span[contains(@class, 'sr-hotel__name')]")})
    private List<WebElement> foundHotelsTitles;

    @FindAll({@FindBy (xpath = ".//h3[contains(text(), 'не успели!')]")})
    private List<WebElement> alreadyTakenRooms;

    @FindBy(id = "filter_tracking_remaining")
    private WebElement filterEnd;

    private static final String FILTER_NAME_LOCATOR =
            "//span[contains(@class, 'filter_label') and contains(text(), '%s')]";

    private static final By FOUND_HOTELS_TITLES_LOCATOR = By.xpath(".//span[contains(@class, 'sr-hotel__name')]");
    private static final By TAKEN_ROOM_LOCATOR = By.xpath(
            "//div[contains(@class, 'sr_item_content')]//h3[contains(text(), 'не успели!')]");
    private static final By FOUND_HOTELS_BLOCK_LOCATOR = By.xpath(
            "ancestor::div[contains(@class, 'sr_item_content_slider_wrapper')]");


    public String getSearchAdultsNumber(){
        Select adultsDropdown = new Select(adultsNumber);
        return adultsDropdown.getFirstSelectedOption().getText();
    }

    public String getSearchChildrenNumber(){
        Select childrenDropdown = new Select(childrenNumber);
        return childrenDropdown.getFirstSelectedOption().getText();
    }

    public String getSearchRoomsNumber(){
        Select roomsDropdown = new Select(roomsNumber);
        return roomsDropdown.getFirstSelectedOption().getText();
    }

    public void returnToMainPage(){
        mainPageLogo.click();
    }

    public void chooseFilterOption(String filterName){
        WebElement filterOption = searchElement(By.xpath(String.format(FILTER_NAME_LOCATOR, filterName)));
        filterOption.click();
        waitForElementVisible(filterOption);
    }

    public String getSearchHeader(){
        waitForElementVisible(filterResultsMessage);
        return searchResultsHeader.getText();
    }

    public void goToHotelWithTakenRooms(){
        scrollDown(filterEnd);
        List<WebElement> hotelsWithTakenRoomsList = searchElements(TAKEN_ROOM_LOCATOR);
        WebElement firstHotelWithTakenRooms = hotelsWithTakenRoomsList.get(0);
        WebElement getTakenHotelBlock = firstHotelWithTakenRooms.findElement(FOUND_HOTELS_BLOCK_LOCATOR);
        getTakenHotelBlock.findElement(FOUND_HOTELS_TITLES_LOCATOR).click();
    }

}

