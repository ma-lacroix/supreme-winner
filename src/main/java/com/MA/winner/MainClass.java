package com.MA.winner;

import java.io.IOException;

public class MainClass {

    public static void main(String[] args) throws IOException {
        System.out.println("Application starts...");
        StockAnalysisController stockAnalysisController = new StockAnalysisController(
                50.0f,
                "2024-01-10",
                "2024-08-17",
                "Energy",
                2000.0f);
        stockAnalysisController.run();
    }
}
