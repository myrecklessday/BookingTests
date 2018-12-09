import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.CountryInfoPage;
import pages.FlightsPage;
import pages.MainPage;
import pages.SearchHotelsResultsPage;
import util.TestUtils;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestsBooking {
    private WebDriver driver;
    private MainPage mainPage;
    private CountryInfoPage countryInfoPage;
    private SearchHotelsResultsPage searchHotelsResultsPage;
    private FlightsPage flightsPage;

    @BeforeClass
    public void start() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        mainPage = new MainPage(driver);
        countryInfoPage = new CountryInfoPage(driver);
        searchHotelsResultsPage = new SearchHotelsResultsPage(driver);
        flightsPage = new FlightsPage(driver);
    }

    @BeforeMethod
    public void getMainPage(){
        driver.get("https://www.booking.com/");
        if (!mainPage.getLanguageTitle().equals("ru")) {
            mainPage.changeLanguage("Русский");
            Assert.assertEquals(mainPage.getLanguageTitle(), "ru", "Image title should contain" +
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
    @Test(dataProvider = "checkPopularCitiesDataProvider")
    public void checkPopularCities(String country, List<String> expectedCities){
        mainPage.goToPopularCitiesPage(country);
        Assert.assertTrue(countryInfoPage.getPopularCities().containsAll(expectedCities), "Popular cities " +
                "of chosen country should be " + expectedCities);
    }

    /**
     * 3. Я могу увидеть на главной странице результат предыдущего поиска.
     */
    @Test(dataProvider = "checkSearchHistoryDataProvider")
    public void checkSearchHistory(String place, String arrivalDate, String departureDate, String rooms, String adults, String children){

        String searchArrivalDay = TestUtils.getArrivalDay(arrivalDate);
        String searchDepartureDay = TestUtils.getDepartureDay(departureDate);
        List<String> expectedHistoryParameters = Arrays.asList(rooms, adults, children);

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

        Assert.assertEquals(TestUtils.historyParameters(mainPage.getHistoryParameters()), expectedHistoryParameters,
                "History parameters should be " + expectedHistoryParameters);

    }

    /**
     * 4. Я могу узнать, что Lufthansa летает из Минска в Барселону с датой вылета 25.12 и обратной датой 29.12.
     */
    @Test(dataProvider = "checkPlaneFlightDataProvider")
    public void checkPlaneFlight(String departureCity, String arrivalCity, String departureDate,
                                 String arrivalDate, String carrier) throws ParseException {
        mainPage.goToFlightsPage();
        TestUtils.switchToNewTab(driver);
        flightsPage.searchFlights(departureCity, arrivalCity, departureDate, arrivalDate);
        Assert.assertTrue(flightsPage.getFoundCarriers().contains(carrier),
                "Search result should contain " + carrier);
    }

    /**
     * 5. Я могу найти хотя бы 2 отеля в Барселоне с оценкой "Очень хорошо",
     * с бесплатной отменой бронирования, на даты 25.12 - 29.12.
     */
    @Test(dataProvider = "findGoodHotelsDataProvider")
    public void findGoodHotels(String place, String arrivalDate, String departureDate, String rooms, String adults,
                               String children, String childrenAge){
        mainPage.search(place, arrivalDate, departureDate, rooms, adults, children, childrenAge);

        searchHotelsResultsPage.chooseFilterOption("Очень хорошо: 8+");
        searchHotelsResultsPage.chooseFilterOption("Бесплатная отмена бронирования");

        System.out.println(TestUtils.foundHotelsNumber(searchHotelsResultsPage.getSearchHeader()));
        Assert.assertNotEquals(TestUtils.foundHotelsNumber(searchHotelsResultsPage.getSearchHeader()), 2,
                "Found hotels number should be at least 2");
    }

    @DataProvider
    public Object[][] changeCurrencyDataProvider(){
        return new Object[][] {
                {"Польский злотый", "zł"},
                {"Катарский риал", "QAR"},
        };
    }

    @DataProvider
    public Object[][] checkSearchHistoryDataProvider(){
        return new Object[][] {
                {"Барселона", "2018-12-29", "2019-01-12", "3", "6", "2"},
                {"Барселона", "2018-12-30", "2019-01-09", "1", "8", "3"},
        };
    }

        @DataProvider
    public Object[][] checkPopularCitiesDataProvider(){
        return new Object[][] {
                {"Испания", Arrays.asList("Барселона", "Валенсия", "Мадрид")},
                {"Италия", Arrays.asList("Флоренция", "Рим")},
        };
    }

    @DataProvider
    public Object[][] checkPlaneFlightDataProvider(){
        return new Object[][] {
                {"Минск", "Барселона", "2018-12-25", "2018-12-29", "Lufthansa"},
        };
    }

    @DataProvider
    public Object[][] findGoodHotelsDataProvider(){
        return new Object[][] {
                {"Барселона", "2018-12-25", "2018-12-29", "1", "2", "1", "2"},
        };
    }


}
