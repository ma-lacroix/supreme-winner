package com.MA.winner;

import com.MA.winner.controller.StockAnalysisController;
import com.MA.winner.localDataStorage.models.Strategy;


public class MainClass {

    public static void main(String[] args) throws Exception {
        System.out.println("Application starts...");
        StockAnalysisController stockAnalysisController = new StockAnalysisController(
                50.0f,
                "2024-09-01",
                "2024-09-04",
                "Information Technology",
                5000.0f,
                Strategy.BALANCED);
        stockAnalysisController.run();
    }
}
