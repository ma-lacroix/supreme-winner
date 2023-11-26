package com.MA.winner.localDataStorage.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

import static com.MA.winner.utils.ApiUtils.makeHTTPcall;

@Getter
@Setter
@Builder
public class YahooStockPriceRequests {

    private String ticker;
    private String startDate;
    private String endDate;

    public YahooStockPriceRequests(String ticker, String startDate, String endDate) {
        this.ticker = ticker;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void makeCall() throws IOException {
        makeHTTPcall("https://query1.finance.yahoo.com/v7/finance/download/" + ticker +
                "?period1=" + startDate + "&period2=" + endDate + "&interval=1d&events=history");
    }
}