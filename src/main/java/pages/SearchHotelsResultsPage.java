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



}
