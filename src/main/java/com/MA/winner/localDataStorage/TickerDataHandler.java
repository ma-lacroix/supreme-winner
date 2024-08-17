package com.MA.winner.localDataStorage;

import com.MA.winner.localDataStorage.models.YahooStockPriceRequests;
import com.MA.winner.localDataStorage.models.StockDataResponse;
import jdk.jshell.spi.ExecutionControl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public StockDataResponse getTickerData() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("not there");
//        URL url = new URL(yahooStockPriceRequests.getTickerDataURL());
//        List<StockDataResponse> stockDataResponses = new ArrayList<>();
//        try {
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//            con.setDoOutput(true);
//            // TODO: use ObjectMapper
//            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            String inputLine;
//            int count = 0;
//            while ((inputLine = in.readLine()) != null) {
//                if (count > 0) {
//                    int len = inputLine.split(",").length;
//                    for (int i = 0; i < len; i++) {
//                        stockDataResponse.getStockData().computeIfAbsent(stockDataResponse.getCols().get(i),
//                                s -> new ArrayList<>()).add(inputLine.split(",")[i]);
//                    }
//                }
//                count++;
//            }
//            in.close();
//            con.disconnect();
//            return stockDataResponse;
//        } catch (Exception e) {
//            throw new ConnectException("Couldn't get data.");
//        }
    }
}
