package pages;

import base.PageBase;
import elements.SearchHotelsForm;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    @FindBy(xpath = "//div[@class = 'bui-banner__content']/a")
    private WebElement calculationPageLink;

    private final static String LANGUAGE_NAME_XPATH = "//div[contains(@id, 'current_language_foldout')]//span[contains(text(), '%s')]";
    private final static String CURRENCY_NAME_XPATH = "//div[contains(@class, 'uc_currency')]//span[contains(text(), '%s')]";
    private final static String LEARN_MORE_COUNTRY_NAME_XPATH = "//li[contains(@class, 'dcbi-country')]/a[contains(text(), '%s')]";
    private final static String MAIN_MENU_ITEMS_XPATH = "//a[@class = 'xpb__link']/span[text() = '%s']";

    public void changeLanguage(String language){
        languageSelector.click();
        String languageItem = String.format(LANGUAGE_NAME_XPATH, language);
        searchElement(By.xpath(languageItem)).click();
    }

    public String getLanguageTitle(){
        return pageLanguage.getAttribute("lang");
    }

    public void changeCurrency(String currency){
        currencySelector.click();
        String currencyNameItem = String.format(CURRENCY_NAME_XPATH, currency);
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

    public void goToPopularCitiesPage(String countryName){
        scrollDown(footer);
        String learnMoreCountryNameItem = String.format(LEARN_MORE_COUNTRY_NAME_XPATH, countryName);
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
        String flightsMenuItem = String.format(MAIN_MENU_ITEMS_XPATH, "Авиабилеты");
        searchElement(By.xpath(flightsMenuItem)).click();
    }

    public void goToRentApartmentsPage(){
        calculationPageLink.click();
    }

}
