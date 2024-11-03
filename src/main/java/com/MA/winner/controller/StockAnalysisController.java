package com.MA.winner.controller;

import com.MA.winner.localDataStorage.AnalysisDataHandler;
import com.MA.winner.localDataStorage.models.StockPerformanceData;
import com.MA.winner.localDataStorage.models.Strategy;
import com.MA.winner.performanceCalculations.MeanInversionCalculatorHandler;
import com.MA.winner.performanceCalculations.SharpeCalculatorHandler;
import lombok.Builder;

import java.util.List;

import static com.MA.winner.utils.Utils.getDummyStockPerformanceData;

@Builder
public class StockAnalysisController {

    boolean test;
    float maxStockValue;
    String startDate;
    String endDate;
    String sector;
    float budget;
    Strategy strategy;

    public StockAnalysisController(boolean test, float maxStockValue, String startDate, String endDate,
                                   String sector, float budget, Strategy strategy) {
        this.test = test;
        this.maxStockValue = maxStockValue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sector = sector;
        this.budget = budget;
    }

    public void run() throws Exception {
        List<StockPerformanceData> stockPerformanceDataList = test ?
                getDummyStockPerformanceData()
                : new AnalysisDataHandler(maxStockValue,startDate,endDate,sector,strategy).getStocksAnalysisData();
        SharpeCalculatorHandler sharpeCalculatorHandler = new SharpeCalculatorHandler(stockPerformanceDataList,
                50_000_000L, budget);
        sharpeCalculatorHandler.generateResults();
        MeanInversionCalculatorHandler meanInversionCalculatorHandler = new MeanInversionCalculatorHandler(stockPerformanceDataList, budget);
        meanInversionCalculatorHandler.generateResults();
    }
}
