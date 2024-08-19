package com.MA.winner;

import com.MA.winner.localDataStorage.AnalysisDataHandler;

import java.io.IOException;

public class StockAnalysisController {

    float maxStockValue;
    String startDate;
    String endDate;
    String sector;
    AnalysisDataHandler analysisDataHandler;


    public StockAnalysisController(float maxStockValue, String startDate, String endDate, String sector) {
        this.maxStockValue = maxStockValue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sector = sector;
        this.analysisDataHandler = new AnalysisDataHandler(
                maxStockValue,
                startDate,
                endDate,
                sector);
    }

    public void run() throws IOException {
        analysisDataHandler.getStocksAnalysisData();
    }
}
