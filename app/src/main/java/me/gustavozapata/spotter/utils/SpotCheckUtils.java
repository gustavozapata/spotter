package me.gustavozapata.spotter.utils;

import android.graphics.Color;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SpotCheckUtils {

    public static void colourResult(String result, TextView textView) {
        if (result != null) {
            switch (result) {
                case "No action required":
                    textView.setTextColor(Color.rgb(34, 192, 88));
                    break;
                case "Produced documents":
                    textView.setTextColor(Color.rgb(245, 156, 22));
                    break;
                case "Advice given":
                    textView.setTextColor(Color.rgb(18, 141, 210));
                    break;
                case "Driver detained":
                    textView.setTextColor(Color.rgb(225, 23, 47));
                    break;
            }
        }
    }

    public static Date pickDate(int year, int monthOfYear, int dayOfMonth){
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static String convertDateToString(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.UK);
        return simpleDateFormat.format(date);
    }

    public static Date convertStringToDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("dd MMM, yyyy", Locale.UK);
        try {
            return format.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
