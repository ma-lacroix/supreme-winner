package com.MA.winner.localDataStorage;

import com.MA.winner.localDataStorage.models.Sp500tickerNamesRequest;
import com.MA.winner.localDataStorage.models.StockDataResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class AllTickersDataHandler {

    public StockDataResponse getAllTickers() {

        Sp500tickerNamesRequest sp500tickerNamesRequest = new Sp500tickerNamesRequest();
        StockDataResponse stockDataResponse = new StockDataResponse(Arrays.asList("Symbol","Security","GICS Sector",
                "GICS Sub-Industry"));
        try {
            String command = "curl " + sp500tickerNamesRequest.getUrl();

            ProcessBuilder processBuilder = new ProcessBuilder(command.split("\\s+"));
            Process process = processBuilder.start();

            // Read the output of the command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                if (count > 0) {
                    int len = line.split(",").length;
                    for (int i = 0; i < 4; i++) {
                        stockDataResponse.getStockData().computeIfAbsent(stockDataResponse.getCols().get(i),
                                s -> new ArrayList<>()).add(line.split(",")[i]);
                    }
                }
                count++;
            }
            // Wait for the process to finish
            int exitCode = process.waitFor();
            System.out.println("Exit Code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return stockDataResponse;
    }
}
