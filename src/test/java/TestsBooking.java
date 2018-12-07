import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.CountryInfoPage;
import pages.MainPage;
import pages.SearchHotelsResultsPage;
import util.TestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestsBooking {
    private WebDriver driver;
    private MainPage mainPage;
    private CountryInfoPage countryInfoPage;
    private SearchHotelsResultsPage searchHotelsResultsPage;

    private static TestUtils testUtils;

    @BeforeClass
    public void start() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        mainPage = new MainPage(driver);
        countryInfoPage = new CountryInfoPage(driver);
        searchHotelsResultsPage = new SearchHotelsResultsPage(driver);

        testUtils = new TestUtils();
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
    @Test(dataProvider = "searchDataProvider")
    public void search(String place, String arrivalDate, String departureDate, String rooms, String adults, String children){

        String searchArrivalDay = testUtils.getArrivalDay(arrivalDate);
        String searchDepartureDay = testUtils.getDepartureDay(departureDate);

        mainPage.search(place, arrivalDate, departureDate, rooms, adults, children);
        Assert.assertTrue(searchHotelsResultsPage.getSearchRoomsNumber().contains(rooms),
                "Quantity of searched rooms number should be " + rooms);
        Assert.assertTrue(searchHotelsResultsPage.getSearchAdultsNumber().contains(adults),
                "Quantity of searched adults number should be " + adults);
        Assert.assertTrue(searchHotelsResultsPage.getSearchChildrenNumber().contains(children),
                "Quantity of searched children number should be " + children);
        
        searchHotelsResultsPage.returnToMainPage();
        Assert.assertEquals(mainPage.getHistoryText(), "Еще хотите побывать в городе " + place + "?",
                "Place in search history should be " + place);
        Assert.assertTrue(mainPage.getHistoryDates().contains(searchArrivalDay),
                "Arrival date in search history should be " + searchArrivalDay);
        Assert.assertTrue(mainPage.getHistoryDates().contains(searchDepartureDay),
                "Departure date in search history should be " + searchDepartureDay);

    }

    @DataProvider
    public Object[][] changeCurrencyDataProvider(){
        return new Object[][] {
                {"Польский злотый", "zł"},
                {"Катарский риал", "QAR"}
        };
    }

    @DataProvider
    public Object[][] searchDataProvider(){
        return new Object[][] {
                {"Барселона", "2018-12-29", "2019-01-12", "3", "6", "2"},
                {"Лос-Анджелес", "2018-12-30", "2019-01-09", "1", "8", "3"}
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
