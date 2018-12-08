package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class TestUtils {

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
        Pattern numberRegex = Pattern.compile("\\d+");
        Matcher matcher = numberRegex.matcher(roomGuestsStr);
        List<String> results = new ArrayList<String>();
        while (matcher.find()) {
            results.add(matcher.group(0));
        }

        return results;
    }
}
