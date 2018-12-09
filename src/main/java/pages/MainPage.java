package pages;

import base.PageBase;
import elements.SearchHotelsForm;
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

    private SearchHotelsForm searchHotelsForm = new SearchHotelsForm(driver);

    @FindBy(css = ".uc_language")
    private WebElement languageSelector;

    @FindBy(xpath = "/html")
    private WebElement pageLanguage;

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

    @FindBy(xpath = "//td[@class = 'sh-postcard-rooms']")
    private WebElement historyParameters;

    private final static String LANGUAGE_NAME_XPATH = "//div[contains(@id, 'current_language_foldout')]//span[contains(text(), 'LanguageName')]";
    private final static String CURRENCY_NAME_XPATH = "//div[contains(@class, 'uc_currency')]//span[contains(text(), 'CurrencyName')]";
    private final static String LEARN_MORE_COUNTRY_NAME_XPATH = "//li[contains(@class, 'dcbi-country')]/a[contains(text(), 'LearnMoreCountryName')]";
    private final static String MAIN_MENU_ITEMS_XPATH = "//a[@class = 'xpb__link']/span[text() = 'MainMenuItem']";

    public void changeLanguage(String language){
        languageSelector.click();
        String languageItem = LANGUAGE_NAME_XPATH.replace("LanguageName", language);
        searchElement(By.xpath(languageItem)).click();
    }

    public String getLanguageTitle(){
        return pageLanguage.getAttribute("lang");
    }

    public void changeCurrency(String currency){
        currencySelector.click();
        String currencyNameItem = CURRENCY_NAME_XPATH.replace("CurrencyName", currency);
        searchElement(By.xpath(currencyNameItem)).click();
    }

    public String getSelectedCurrencySymbol(){
        return currencyChosenSymbol.getText();
    }

    public boolean isRecommendedDirectionsCurrencyCorrect(String currencySymbol){
        for (WebElement recommendedDirectionPrice : recommendedDirectionsPrices) {
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
        String learnMoreCountryNameItem = LEARN_MORE_COUNTRY_NAME_XPATH.replace("LearnMoreCountryName", countryName);
        searchElement(By.xpath(learnMoreCountryNameItem)).click();
    }

    public void search(String place, String arrivalDate, String departureDate, String rooms, String adults, String children) {
        searchHotelsForm.initSearch(place, arrivalDate, departureDate, rooms, adults, children);
    }

    public void search(String place, String arrivalDate, String departureDate, String rooms, String adults,
                       String children, String childrenAge) {
        searchHotelsForm.initSearch(place, arrivalDate, departureDate, rooms, adults, children, childrenAge);
    }

    public String getHistoryText(){
        return historyText.getText();
    }

    public String getHistoryDates(){
        return historyDates.getText();
    }

    public String getHistoryParameters(){
        return historyParameters.getText();
    }

    public void goToFlightsPage(){
        String flightsMenuItem = MAIN_MENU_ITEMS_XPATH.replace("MainMenuItem", "Авиабилеты");
        searchElement(By.xpath(flightsMenuItem)).click();
    }

}
