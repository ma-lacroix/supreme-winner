package com.MA.winner.controller;

import com.MA.winner.localDataStorage.AnalysisDataHandler;
import com.MA.winner.localDataStorage.models.StockPerformanceData;
import com.MA.winner.localDataStorage.models.Strategy;
import com.MA.winner.performanceCalculations.SharpeCalculatorHandler;

import java.util.List;

public class StockAnalysisController {

    float maxStockValue;
    String startDate;
    String endDate;
    String sector;
    float budget;
    AnalysisDataHandler analysisDataHandler;


    public StockAnalysisController(float maxStockValue, String startDate, String endDate, String sector,
                                   float budget, Strategy strategy) {
        this.maxStockValue = maxStockValue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sector = sector;
        this.budget = budget;
        this.analysisDataHandler = new AnalysisDataHandler(
                maxStockValue,
                startDate,
                endDate,
                sector,
                strategy);
    }

    public void run() throws Exception {
        List<StockPerformanceData> stockPerformanceDataList = analysisDataHandler.getStocksAnalysisData();
        SharpeCalculatorHandler sharpeCalculatorHandler = new SharpeCalculatorHandler(stockPerformanceDataList,
                50_000_000L, budget);
        sharpeCalculatorHandler.generateResults();
    }
}
