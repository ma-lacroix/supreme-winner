package com.MA.winner.utils;

import com.MA.winner.localDataStorage.models.YahooStockPriceResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class ApiUtils {

    public YahooStockPriceResponse makeHTTPcall(String URLString) throws IOException {
        URL url = new URL(URLString);
        YahooStockPriceResponse yahooStockPriceResponse = new YahooStockPriceResponse();
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(true);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            int count = 0;
            while ((inputLine = in.readLine()) != null) {
                if (count > 0) {
                    int len = inputLine.split(",").length;
                    for (int i = 0; i < len; i++) {
                        yahooStockPriceResponse.getStockData().computeIfAbsent(yahooStockPriceResponse.getCols().get(i),
                                s -> new ArrayList<>()).add(inputLine.split(",")[i]);
                    }
                }
                count++;
            }
            in.close();
            con.disconnect();
            return yahooStockPriceResponse;
        } catch (Exception e) {
            throw new ConnectException("Couldn't get data.");
        }
    }

    public void makeCurlCall(String url) {
        try {
            String command = "curl " + url;

            ProcessBuilder processBuilder = new ProcessBuilder(command.split("\\s+"));
            Process process = processBuilder.start();

            // Read the output of the command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the process to finish
            int exitCode = process.waitFor();
            System.out.println("Exit Code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

