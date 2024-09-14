package com.MA.winner.localDataStorage.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static com.MA.winner.utils.Utils.getFmpApiKey;

@Getter
@Setter
@Builder
public class FmpStockPriceRequests {
    private String ticker;
    private String startDate;
    private String endDate;

    public FmpStockPriceRequests(String ticker, String startDate, String endDate) {
        this.ticker = ticker;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTickerDataURL() {
        return "https://financialmodelingprep.com/api/v3/historical-price-full/" + ticker +
                "?apikey=" + getFmpApiKey() + "&from=" + startDate + "&to=" + endDate;
    }
}
