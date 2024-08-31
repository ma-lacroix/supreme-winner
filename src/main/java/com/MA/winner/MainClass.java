package com.MA.winner;

import com.MA.winner.controller.StockAnalysisController;
import com.MA.winner.localDataStorage.models.Strategy;

import java.io.IOException;

public class MainClass {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Application starts...");
        StockAnalysisController stockAnalysisController = new StockAnalysisController(
                50.0f,
                "2024-07-31",
                "2024-08-31",
                "all",
                5000.0f,
                Strategy.RETURN);
        stockAnalysisController.run();
        Thread.sleep(500000);
    }
}
