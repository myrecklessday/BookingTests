package pages;

import base.PageBase;
import elements.SearchField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MainPage extends PageBase {
    public MainPage(WebDriver driver){
        super(driver);
    }

    @FindBy(css = ".uc_language")
    private WebElement languageSelector;

    @FindBy(xpath = "//li[contains(@class, 'uc_language')]//img")
    private WebElement languageImageTitle;

    @FindBy(css = ".uc_currency")
    private WebElement currencySelector;

    @FindBy(xpath = "//li[contains(@class, 'uc_currency')]//a")
    private WebElement currencyChosenSymbol;

    @FindAll({@FindBy(xpath = "//span[@class = 'lp-postcard-avg-price-value']")})
    private List <WebElement> recommendedDirectionsPrices;

    @FindAll({@FindBy(xpath = "//div[contains(@class, 'bui-card__content')]/p")})
    private List <WebElement> housesRecommendationsPrices;

    @FindBy(id = "booking-footer")
    private WebElement footer;

    @FindBy(className = "sh-postcard-content-title")
    private WebElement historyText;

    @FindBy(xpath = "//td[@class = 'sh-postcard-dates']/a")
    private WebElement historyDates;

    private String languageName = "//div[contains(@id, 'current_language_foldout')]//span[contains(text(), 'LanguageName')]";
    private String currencyName = "//div[contains(@class, 'uc_currency')]//span[contains(text(), 'CurrencyName')]";
    private String learnMoreCountryName = "//li[contains(@class, 'dcbi-country')]/a[contains(text(), 'LearnMoreCountryName')]";

    public void changeLanguage(String language){
        languageSelector.click();
        String languageItem = languageName.replace("LanguageName", language);
        searchElement(By.xpath(languageItem)).click();
    }

    public String getLanguageImageTitle(){
        return languageImageTitle.getAttribute("alt");
    }

    public void changeCurrency(String currency){
        currencySelector.click();
        String currencyNameItem = currencyName.replace("CurrencyName", currency);
        searchElement(By.xpath(currencyNameItem)).click();
    }

    public String getSelectedCurrencySymbol(){
        return currencyChosenSymbol.getText();
    }

    public boolean isRecommendedDirectionsCurrencyCorrect(String currencySymbol){
        for (WebElement recommendedDirectionPrice:recommendedDirectionsPrices) {
            if (!recommendedDirectionPrice.getText().contains(currencySymbol)){
                return false;
            }
        }
        return true;
    }

    private void scrollDown(){
        new Actions(driver).moveToElement(footer).build().perform();
    }

    public void goToPopularCitiesPage(String countryName){
        scrollDown();
        String learnMoreCountryNameItem = learnMoreCountryName.replace("LearnMoreCountryName", countryName);
        searchElement(By.xpath(learnMoreCountryNameItem)).click();
    }

    public void search(String place, String arrivalDate, String departureDate, String rooms, String adults, String children) {
        SearchField searchField = new SearchField(driver);
        searchField.initSearch(place, arrivalDate, departureDate, rooms, adults, children);
    }

    public String getHistoryText(){
        return historyText.getText();
    }

    public String getHistoryDates(){
        return historyDates.getText();
    }

}
