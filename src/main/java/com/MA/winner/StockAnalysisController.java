package com.MA.winner;

import com.MA.winner.localDataStorage.AnalysisDataHandler;
import com.MA.winner.localDataStorage.models.StockPerformanceData;
import com.MA.winner.performanceCalculations.PerfCalculatorHandler;

import java.io.IOException;
import java.util.List;

public class StockAnalysisController {

    float maxStockValue;
    String startDate;
    String endDate;
    String sector;
    float budget;
    AnalysisDataHandler analysisDataHandler;


    public StockAnalysisController(float maxStockValue, String startDate, String endDate, String sector, float budget) {
        this.maxStockValue = maxStockValue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sector = sector;
        this.budget = budget;
        this.analysisDataHandler = new AnalysisDataHandler(
                maxStockValue,
                startDate,
                endDate,
                sector);
    }

    public void run() throws IOException {
        List<StockPerformanceData> stockPerformanceDataList = analysisDataHandler.getStocksAnalysisData();
        PerfCalculatorHandler perfCalculatorHandler = new PerfCalculatorHandler(stockPerformanceDataList,
                5_000_000L, budget);
        perfCalculatorHandler.generateResults();
    }
}
