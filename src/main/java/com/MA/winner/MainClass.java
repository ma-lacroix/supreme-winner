package com.MA.winner;

import com.MA.winner.localDataStorage.AnalysisDataHandler;

import java.io.IOException;


public class MainClass {

    public static void main(String[] args) throws IOException {
        System.out.println("Application starts...");
        AnalysisDataHandler analysisDataHandler = new AnalysisDataHandler(
                50.0f,
                "2024-08-10",
                "2024-08-17",
                "Energy");
        analysisDataHandler.getStocksAnalysisData();
//        PerfCalculatorHandler perfCalculatorHandler = new PerfCalculatorHandler(stocksRawData, 1000.0f);
//        perfCalculatorHandler.fetchBestPortfolio();
    }
}
