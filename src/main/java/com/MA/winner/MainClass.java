package com.MA.winner;

import com.MA.winner.localDataStorage.AnalysisDataHandler;
import com.MA.winner.localDataStorage.models.StocksRawData;
import com.MA.winner.performanceCalculations.PerfCalculatorHandler;

import java.io.IOException;

import static com.MA.winner.utils.Utils.printStocksRawData;


public class MainClass {

    public static void main(String[] args) throws IOException {
        System.out.println("Application starts...");
        AnalysisDataHandler analysisDataHandler = new AnalysisDataHandler(
                50.0f,
                "2023-12-01",
                "2023-12-31",
                "Energy");
        StocksRawData stocksRawData = analysisDataHandler.getStocksAnalysisData();
        printStocksRawData(stocksRawData);
        PerfCalculatorHandler perfCalculatorHandler = new PerfCalculatorHandler(stocksRawData, 1000.0f);
        perfCalculatorHandler.fetchBestPortfolio();
    }
}
