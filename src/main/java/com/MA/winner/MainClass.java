package com.MA.winner;

import com.MA.winner.StockAnalysisController;

import java.io.IOException;


public class MainClass {

    public static void main(String[] args) throws IOException {
        System.out.println("Application starts...");
        StockAnalysisController stockAnalysisController = new StockAnalysisController(
                50.0f,
                "2024-08-10",
                "2024-08-17",
                "Energy");
        stockAnalysisController.run();
    }
}
