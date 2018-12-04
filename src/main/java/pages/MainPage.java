package pages;

import base.PageBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends PageBase {
    public MainPage(WebDriver driver){
        super(driver);
    }

    @FindBy(id = "language_selector")
    private WebElement languageSelector;

    @FindBy(id = "currency_selector")
    private WebElement currencySelector;

    @FindBy(xpath = "//a[contains(@class, 'no_target_blank')]//span[@class = 'seldescription' and contains(text(), 'Русский')]")
    private WebElement languageName;

    public void changeLanguage(String language){
        languageSelector.click();
        languageName.sendKeys(language);
    }

    public void changeCurrency(){
        currencySelector.click();
    }

}
