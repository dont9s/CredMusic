package com.gojek.weather.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static String getDayOfWeek(String date) {


        String output;
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy-MM-dd"); // the day of the week abbreviated


        try {
            Date input = simpleDateformat.parse(date);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(input);

            output = getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));


        } catch (ParseException e) {
            output = date;

            e.printStackTrace();
        }

        return output;


    }

    private static String getDayOfWeek(int value) {
        String day = "";
        switch (value) {
            case 1:
                day = "Sunday";
                break;
            case 2:
                day = "Monday";
                break;
            case 3:
                day = "Tuesday";
                break;
            case 4:
                day = "Wednesday";
                break;
            case 5:
                day = "Thursday";
                break;
            case 6:
                day = "Friday";
                break;
            case 7:
                day = "Saturday";
                break;
        }
        return day;
    }
}
