package pages;

import base.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

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

    @FindBy(id = "criteo-tags-div")
    private WebElement loadMessage;

    private static final String FILTER_NAME_LOCATOR =
            "//span[contains(@class, 'filter_label') and contains(text(), '%s')]";

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

}
