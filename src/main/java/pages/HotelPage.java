package pages;

import base.PageBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HotelPage extends PageBase {
    public HotelPage(WebDriver driver){
        super(driver);
    }

    @FindBy(id = "no_availability_msg")
    private WebElement takenRoomMessage;

    public boolean isRoomAlreadyTaken(){
        return takenRoomMessage.isDisplayed();
    }

}
