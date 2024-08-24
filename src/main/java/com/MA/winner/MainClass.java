package com.MA.winner;

import java.io.IOException;

public class MainClass {

    public static void main(String[] args) throws IOException {
        System.out.println("Application starts...");
        StockAnalysisController stockAnalysisController = new StockAnalysisController(
                50.0f,
                "2024-01-10",
                "2024-08-17",
                "all",
                5000.0f);
        stockAnalysisController.run();
    }
}
