package com.MA.winner.localDataStorage;

import com.MA.winner.localDataStorage.models.YahooStockPriceRequests;
import com.MA.winner.localDataStorage.models.StockDataResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import static com.MA.winner.utils.Utils.convertToUnixTimestamp;

public class TickerDataHandler {
    private final YahooStockPriceRequests yahooStockPriceRequests;
    public TickerDataHandler(String ticker, String startDate, String endDate) {
        this.yahooStockPriceRequests = YahooStockPriceRequests
                .builder()
                .ticker(ticker)
                .startDate(convertToUnixTimestamp(startDate))
                .endDate(convertToUnixTimestamp(endDate))
                .build();
    }

    public StockDataResponse getTickerData() throws IOException {
        URL url = new URL(yahooStockPriceRequests.getTickerDataURL());
        StockDataResponse stockDataResponse = new StockDataResponse(Arrays.asList("Date","Open","High","Low","Close","Adj Close","Volume"));
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
                        stockDataResponse.getStockData().computeIfAbsent(stockDataResponse.getCols().get(i),
                                s -> new ArrayList<>()).add(inputLine.split(",")[i]);
                    }
                }
                count++;
            }
            in.close();
            con.disconnect();
            return stockDataResponse;
        } catch (Exception e) {
            throw new ConnectException("Couldn't get data.");
        }
    }
}
