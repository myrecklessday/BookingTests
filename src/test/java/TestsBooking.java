import com.google.common.collect.ImmutableList;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.collections.Lists;
import pages.CountryInfoPage;
import pages.MainPage;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestsBooking {
    private WebDriver driver;
    private MainPage mainPage;
    private CountryInfoPage countryInfoPage;

    @BeforeClass
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        mainPage = new MainPage(driver);
        countryInfoPage = new CountryInfoPage(driver);
    }

    @BeforeMethod
    public void getMainPage(){
        driver.get("https://www.booking.com/");
        if (!mainPage.getLanguageImageTitle().contains("русском")) {
            mainPage.changeLanguage("Русский");
            Assert.assertTrue(mainPage.getLanguageImageTitle().contains("русском"), "Image title should contain" +
                    "chosen language name");
        }
    }

    @AfterClass
    public void finish(){
        driver.quit();
    }

    /**
     * 1. Я могу убедиться, что при смене валюты цены на странице отображаются в выбранной валюте.
     */
    @Test(dataProvider = "changeCurrencyDataProvider")
    public void changeCurrencyTest(String currencyName, String currencySymbol){
        mainPage.changeCurrency(currencyName);
        Assert.assertTrue(mainPage.getSelectedCurrencySymbol().contentEquals(currencySymbol), "Chosen currency symbol" +
                "should be " + currencySymbol);
        Assert.assertTrue(mainPage.isRecommendedDirectionsCurrencyCorrect(currencySymbol), "Recommended directions" +
                "price currency should be in currency " + currencySymbol);
    }

    /**
     * 2. Я могу узнать, что в Испании среди популярных городов есть Барселона, Мадрид и Валенсия.
     */
    @Test
    public void checkSpainPopularCities(){
        List<String> expectedPopularCities = ImmutableList.of("Барселона", "Валенсия", "Мадрид");
        mainPage.goToPopularCitiesPage("Испания");
        Assert.assertTrue(countryInfoPage.getPopularCities().containsAll(expectedPopularCities), "Popular cities " +
                "of chosen country should be " + expectedPopularCities);
    }

    @DataProvider
    public Object[][] changeCurrencyDataProvider(){
        return new Object[][] {
                {"Польский злотый", "zł"},
                {"Катарский риал", "QAR"}
        };
    }


}
