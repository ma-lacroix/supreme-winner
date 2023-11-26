package com.MA.winner;

import com.MA.winner.localDataStorage.ExternalDataSourceHandler;

import java.io.IOException;

public class MainClass {
    public static void main(String[] args) throws IOException {
        System.out.println("Application starts...");
        ExternalDataSourceHandler externalDataSourceHandler = new ExternalDataSourceHandler("AAPL",
                "1700024151", "1701024151");
        externalDataSourceHandler.runAnalysis();
    }
}
