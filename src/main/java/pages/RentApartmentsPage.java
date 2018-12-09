package pages;

import base.PageBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RentApartmentsPage extends PageBase {
    public RentApartmentsPage(WebDriver driver){
        super(driver);
    }

    @FindBy(name = "calculator-price-per-night")
    private WebElement pricePerNightInput;

    @FindBy(css = ".js-calculator-estimated-money")
    private WebElement calculatedSum;

    private void goToCalculationForm(){
        scrollDown(calculatedSum);
    }

    public String calculateProfit(String pricePerNight){
        goToCalculationForm();
        pricePerNightInput.clear();
        pricePerNightInput.sendKeys(pricePerNight);
        return calculatedSum.getText();
    }

}
