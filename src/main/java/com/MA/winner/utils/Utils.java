package com.MA.winner.utils;

import com.MA.winner.localDataStorage.models.StockPerformanceData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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

    public static void printAllStockPerformanceData(StockPerformanceData stockPerformanceData) {
        System.out.println("Symbol: " + stockPerformanceData.getSymbol() +
                ", Close: " + stockPerformanceData.getClose() +
                ", ROI: " + stockPerformanceData.getRoi() +
                ", STD: " + stockPerformanceData.getStd() +
                ", AVG: " + stockPerformanceData.getAvg());
    }

    public static void printRecommendation(Map<String, Integer> inv) {
        for (String key: inv.keySet()) {
            System.out.println(key + " " + inv.get(key));
        }
    }

    public static String getFmpApiKey() {
        String varName = "FMP_KEY";
        String fmpKey = System.getenv(varName);
        if (fmpKey == null) {
            throw new IllegalStateException(varName + " not found. Check environment variables.");
        }
        return fmpKey;
    }
}

