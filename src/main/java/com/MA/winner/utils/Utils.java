package com.MA.winner.utils;

import com.MA.winner.localDataStorage.models.StockDataResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static void printResults(StockDataResponse response) {
        for (String key: response.getCols()) {
            System.out.println(key + " " + response.getStockData().get(key).toString());
        }
    }

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

