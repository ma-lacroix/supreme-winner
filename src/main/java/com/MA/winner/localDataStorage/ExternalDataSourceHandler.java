package com.MA.winner.localDataStorage;

import com.MA.winner.localDataStorage.models.YahooStockPriceRequests;
import com.MA.winner.localDataStorage.models.YahooStockPriceResponse;
import com.MA.winner.utils.ApiUtils;

import java.io.IOException;

public class ExternalDataSourceHandler {
    private final YahooStockPriceRequests yahooStockPriceRequests;
    private final ApiUtils apiUtils;

    public ExternalDataSourceHandler(String ticker, String startDate, String endDate) {
        this.yahooStockPriceRequests = YahooStockPriceRequests
                .builder()
                .ticker(ticker)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        this.apiUtils = new ApiUtils();
    }

    public YahooStockPriceResponse runAnalysis() throws IOException {
        return apiUtils.makeHTTPcall(yahooStockPriceRequests.getTickerDataURL());
    }

}
