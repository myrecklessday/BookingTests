package pages;

import base.PageBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class CountryInfoPage extends PageBase {
    public CountryInfoPage(WebDriver driver){
        super(driver);
    }

    @FindAll({@FindBy(xpath = "//a[@class = 'dcsp-popular-item__title-txt']")})
    private List<WebElement> popularCities;

    public List<String> getPopularCities(){
        List<String> popularCitiesList = new ArrayList<String>();
        for (WebElement popularCity: popularCities) {
            popularCitiesList.add(popularCity.getText());
        }
        return popularCitiesList;
    }

}
