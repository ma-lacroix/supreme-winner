package com.MA.winner.utils;

import com.MA.winner.localDataStorage.models.StockDataResponse;

public class Utils {

    public static void printResults(StockDataResponse response) {
        for (String key: response.getCols()) {
            System.out.println(key + " " + response.getStockData().get(key).toString());
        }
    }
}

