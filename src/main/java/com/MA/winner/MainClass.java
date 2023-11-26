package com.MA.winner;

import com.MA.winner.localDataStorage.ExternalDataSourceHandler;
import com.MA.winner.localDataStorage.models.YahooStockPriceResponse;

import java.io.IOException;

public class MainClass {
    public static void main(String[] args) throws IOException {
        System.out.println("Application starts...");
        // tests
        ExternalDataSourceHandler externalDataSourceHandler = new ExternalDataSourceHandler("AAPL",
                "1700004151", "1701024151");
        YahooStockPriceResponse yahooStockPriceResponse = externalDataSourceHandler.runAnalysis();
        for (String key: yahooStockPriceResponse.getCols()) {
            System.out.println(key + " " + yahooStockPriceResponse.getStockData().get(key).toString());
        }
    }
}
