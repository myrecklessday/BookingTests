package pages;

import base.PageBase;
import elements.SearchFlightsForm;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class FlightsPage extends PageBase {

    private SearchFlightsForm searchFlightsForm = new SearchFlightsForm(driver);

    public FlightsPage(WebDriver driver){
        super(driver);
    }

    @FindAll({@FindBy(xpath = "//div[@data-name = 'airlines-section']//label[contains(@id, 'airlines-airlines')]")})
    private List<WebElement> foundCarriers;

    public void searchFlights(String departureCity, String arrivalCity, String departureDateStr, String arrivalDateStr) throws ParseException {
        searchFlightsForm.initSearch(departureCity, arrivalCity, departureDateStr, arrivalDateStr);
    }

    public List<String> getFoundCarriers(){
        List<String> foundCarriersList = new ArrayList<String>();
        for (WebElement foundCarrier: foundCarriers) {
            foundCarriersList.add(foundCarrier.getText());
        }
        return foundCarriersList;

    }

}
