package com.MA.winner.localDataStorage;

import com.MA.winner.localDataStorage.models.YahooStockPriceRequests;

import java.io.IOException;

public class ExternalDataSourceHandler {
    private final YahooStockPriceRequests yahooStockPriceRequests;

    public ExternalDataSourceHandler(String ticker, String startDate, String endDate) {
        this.yahooStockPriceRequests = YahooStockPriceRequests
                .builder()
                .ticker(ticker)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

    public void runAnalysis() throws IOException {
        yahooStockPriceRequests.makeCall();
    }

}
