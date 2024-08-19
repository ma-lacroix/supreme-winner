package com.MA.winner.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String convertToUnixTimestamp(String someDateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long unixTimestamp;
        try {
            Date date = sdf.parse(someDateString);
            unixTimestamp = date.getTime() / 1000; // Convert milliseconds to seconds
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return String.valueOf(unixTimestamp);
    }
}

