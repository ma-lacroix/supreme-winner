package com.MA.winner;

import com.MA.winner.localDataStorage.AnalysisDataHandler;
import com.MA.winner.localDataStorage.models.StocksRawData;

import java.io.IOException;


public class MainClass {

    public static void main(String[] args) throws IOException {
        System.out.println("Application starts...");

        // TODO: tests
        AnalysisDataHandler analysisDataHandler = new AnalysisDataHandler("2023-11-01", "2023-11-14","Industrials");
        StocksRawData stocksRawData = analysisDataHandler.getStocksAnalysisData();
        System.out.println(stocksRawData.getStocksAnalysisData().toString());
        // TODO: run performance calculations next
    }
}
