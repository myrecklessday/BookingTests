package util;

import org.openqa.selenium.WebDriver;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestUtils {

    private static Pattern numberRegex = Pattern.compile("\\d+");
    //TODO: refactor using SimpleDateFormat

    public static String getArrivalDay(String arrivalDate){
        String[] splitArrivalDate = arrivalDate.split("-");
        String arrivalDay = splitArrivalDate[2];
        if (arrivalDay.substring(0, 1).contentEquals("0")){
            return arrivalDay.substring(1);
        }
        return arrivalDay;
    }

    public static String getDepartureDay(String departureDate){
        String[] splitDepartureDate = departureDate.split("-");
        String departureDay = splitDepartureDate[2];
        if (departureDay.substring(0, 1).contentEquals("0")){
            return departureDay.substring(1);
        }
        return departureDay;
    }


    public static List<String> historyParameters(String roomGuestsStr){
        Matcher matcher = numberRegex.matcher(roomGuestsStr);
        List<String> results = new ArrayList<String>();
        while (matcher.find()) {
            results.add(matcher.group(0));
        }

        return results;
    }

    public static String foundHotelsNumber(String searchHeader){
        Matcher matcher = numberRegex.matcher(searchHeader);
        String result = "";
        while (matcher.find()) {
            result = matcher.group(0);
        }
        return result;
    }

    public static String getCalculatedSum(String finalPrice){
        Pattern numberRegex = Pattern.compile("\\$(.*)");
        Matcher matcher = numberRegex.matcher(finalPrice);
        String result = "";
        while (matcher.find()) {
            result = matcher.group(1);
        }
        return result.replaceAll("\\s+","");
    }

    public static void switchToNewTab(WebDriver driver){
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.close();
        driver.switchTo().window(tabs.get(1));
    }

    public static String rentCalculation(String pricePerNight){
        double pricePerNightValue = Math.floor(Double.parseDouble(pricePerNight));
        DecimalFormat f = new DecimalFormat("##.00");
        String calculatedPrice = f.format((pricePerNightValue - pricePerNightValue * 0.15 )*18);
        return calculatedPrice;
    }

}
