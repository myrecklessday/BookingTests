package util;

import org.openqa.selenium.WebDriver;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestUtils {

    private static String getDayFromDate(String date) throws ParseException {
        SimpleDateFormat defaultDateFormat =  new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dayDateFormat = new SimpleDateFormat("d");
        Date getDay = defaultDateFormat.parse(date);
        return dayDateFormat.format(getDay);
    }

    public static String getArrivalDay(String arrivalDateStr) throws ParseException {
        return getDayFromDate(arrivalDateStr);
    }

    public static String getDepartureDay(String departureDateStr) throws ParseException {
        return getDayFromDate(departureDateStr);
    }

    public static List<String> historyParameters(String roomGuestsStr){
        Pattern numberRegex = Pattern.compile("\\d+");
        Matcher matcher = numberRegex.matcher(roomGuestsStr);
        List<String> results = new ArrayList<String>();
        while (matcher.find()) {
            results.add(matcher.group(0));
        }

        return results;
    }

    public static String foundHotelsNumber(String searchHeader){
        Pattern numberRegex = Pattern.compile("\\d+");
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
