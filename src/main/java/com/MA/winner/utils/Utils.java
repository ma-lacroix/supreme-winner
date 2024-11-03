package com.MA.winner.utils;

import com.MA.winner.localDataStorage.models.MeanInversionResult;
import com.MA.winner.localDataStorage.models.StockPerformanceData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public static void printSharpeRecommendation(Map<String, Integer> inv) {
        for (String key: inv.keySet()) {
            System.out.println(key + " " + inv.get(key));
        }
    }

    public static void printMeanInversionRecommendation(Map<String, MeanInversionResult> inv) {
        for (String key: inv.keySet()) {
            System.out.println(key + " " + inv.get(key).toString());
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

    public static List<StockPerformanceData> getDummyStockPerformanceData() {
        // Returns a list of fake stock performance data for testing purposes
        List<StockPerformanceData> dummyStockPerformanceDataList = new ArrayList<>();
        dummyStockPerformanceDataList.add(StockPerformanceData
                .builder()
                .symbol("DUM1")
                .avg(100.0f)
                .std(2.5f)
                .roi(1.03f)
                .close(10.f)
                .build());
        dummyStockPerformanceDataList.add(StockPerformanceData
                .builder()
                .symbol("DUM2")
                .avg(50.0f)
                .std(1.5f)
                .roi(0.5f)
                .close(20.f)
                .build());
        dummyStockPerformanceDataList.add(StockPerformanceData
                .builder()
                .symbol("DUM3")
                .avg(130.0f)
                .std(1.0f)
                .roi(2.5f)
                .close(20.f)
                .build());
        dummyStockPerformanceDataList.add(StockPerformanceData
                .builder()
                .symbol("DUM4")
                .avg(30.0f)
                .std(5.0f)
                .roi(0.1f)
                .close(33.f)
                .build());
        return dummyStockPerformanceDataList;
    }
}

