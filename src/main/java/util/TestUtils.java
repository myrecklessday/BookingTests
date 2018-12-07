package util;

public class TestUtils {

    public String getArrivalDay(String arrivalDate){
        String[] splitArrivalDate = arrivalDate.split("-");
        String arrivalDay = splitArrivalDate[2];
        if (arrivalDay.substring(0, 1).contentEquals("0")){
            return arrivalDay.substring(1);
        }
        return arrivalDay;
    }

    public String getDepartureDay(String departureDate){
        String[] splitDepartureDate = departureDate.split("-");
        String departureDay = splitDepartureDate[2];
        if (departureDay.substring(0, 1).contentEquals("0")){
            return departureDay.substring(1);
        }
        return departureDay;
    }
}
