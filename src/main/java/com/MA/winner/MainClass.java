package com.MA.winner;

import com.MA.winner.controller.StockAnalysisController;
import com.MA.winner.localDataStorage.models.Strategy;


public class MainClass {

    public static void main(String[] args) throws Exception {
        System.out.println("Application starts...");
        StockAnalysisController stockAnalysisController = new StockAnalysisController(
                200.0f,
                "2024-08-01",
                "2024-09-15",
                "Energy",
                5000.0f,
                Strategy.RETURN);
        stockAnalysisController.run();
    }
}
