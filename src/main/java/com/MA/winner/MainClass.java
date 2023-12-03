package com.MA.winner;

import com.MA.winner.localDataStorage.AllTickersDataHandler;
import com.MA.winner.localDataStorage.TickerDataHandler;
import com.MA.winner.localDataStorage.models.StockDataResponse;

import java.io.IOException;

import static com.MA.winner.utils.Utils.printResults;

public class MainClass {

    public static void main(String[] args) throws IOException {
        System.out.println("Application starts...");

        // TODO: these are tests only
        AllTickersDataHandler allTickersDataHandler = new AllTickersDataHandler();
        StockDataResponse sp500tickerNamesResponse =  allTickersDataHandler.getAllTickers();
        printResults(sp500tickerNamesResponse);

        TickerDataHandler tickerDataHandler = new TickerDataHandler("BKNG",
                "2023-11-20", "2023-12-01");
        StockDataResponse yahooStockPriceResponse = tickerDataHandler.getTickerData();
        printResults(yahooStockPriceResponse);

        // TODO: run performance calculations next
    }
}
