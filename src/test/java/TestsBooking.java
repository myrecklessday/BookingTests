import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.MainPage;

public class TestsBooking {
    private WebDriver driver;
    private MainPage mainPage;

    @BeforeClass
    public void start() {
        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
    }

    @BeforeMethod
    public void getMainPage(){
        driver.get("https://www.booking.com/");
    }

    @AfterClass
    public void finish(){
        driver.quit();
    }

    /**
     * 1. Я могу убедиться, что при смене валюты цены на странице отображаются в выбранной валюте.
     */
    @Test
    public void currencyTest(){
        mainPage.changeLanguage("Русский");
    }

}
