package com.MA.winner;

import com.MA.winner.controller.StockAnalysisController;
import com.MA.winner.localDataStorage.models.Strategy;

import java.io.IOException;

public class MainClass {

    public static void main(String[] args) throws Exception {
        System.out.println("Application starts...");
        StockAnalysisController stockAnalysisController = new StockAnalysisController(
                50.0f,
                "2024-01-31",
                "2024-08-31",
                "all",
                5000.0f,
                Strategy.STABILITY);
        stockAnalysisController.run();
    }
}
