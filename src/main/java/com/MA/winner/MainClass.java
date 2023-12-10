package com.MA.winner;

import com.MA.winner.localDataStorage.AnalysisDataHandler;
import com.MA.winner.localDataStorage.models.StocksRawData;

import java.io.IOException;

import static com.MA.winner.utils.Utils.printStocksRawData;


public class MainClass {

    public static void main(String[] args) throws IOException {
        System.out.println("Application starts...");

        // TODO: tests
        AnalysisDataHandler analysisDataHandler = new AnalysisDataHandler("2023-11-01", "2023-11-14","Health Care");
        StocksRawData stocksRawData = analysisDataHandler.getStocksAnalysisData();
        printStocksRawData(stocksRawData);
        // TODO: run performance calculations next
    }
}
