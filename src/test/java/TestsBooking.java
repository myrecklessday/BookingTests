import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;
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
//        driver = new FirefoxDriver();
        driver.manage().window().maximize();
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
        List<String> expectedPopularCities = Arrays.asList("Барселона", "Валенсия", "Мадрид");

        mainPage.goToPopularCitiesPage("Испания");
        Assert.assertTrue(countryInfoPage.getPopularCities().containsAll(expectedPopularCities), "Popular cities " +
                "of chosen country should be " + expectedPopularCities);
    }

    /**
     * 3. Я могу увидеть на главной странице результат предыдущего поиска.
     */
    @Test
    public void search(){
        mainPage.search("Барселона", "2018-12-29", "2019-01-12", "2", "4", "3");
        int i = 0;
    }

    @DataProvider
    public Object[][] changeCurrencyDataProvider(){
        return new Object[][] {
                {"Польский злотый", "zł"},
                {"Катарский риал", "QAR"}
        };
    }

//    private List<String> buildExpectedPopularCities(){
//         return Collections.unmodifiableList("Барселона", "Валенсия", "Мадрид");
//    }
//
//    @DataProvider
//    public Object[][] checkSpainPopularCitiesDataProvider(){
//        return new Object[][] {
//                {buildExpectedPopularCities()}
//        };
//    }


}
