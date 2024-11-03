package com.MA.winner;

import com.MA.winner.controller.StockAnalysisController;
import com.MA.winner.localDataStorage.models.Strategy;


public class MainClass {

    public static void main(String[] args) throws Exception {
        System.out.println("Application starts...");
        StockAnalysisController stockAnalysisController = StockAnalysisController.builder()
                .test(true)
                .maxStockValue(200.0f)
                .startDate("2024-09-01")
                .endDate("2024-10-15")
                .sector("Industrials")
                .budget(5000.0f)
                .strategy(Strategy.RETURN)
                .build();
        stockAnalysisController.run();
    }
}
