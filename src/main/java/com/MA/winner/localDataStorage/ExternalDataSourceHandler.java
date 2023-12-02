package com.MA.winner.localDataStorage;

import com.MA.winner.localDataStorage.models.YahooStockPriceRequests;
import com.MA.winner.localDataStorage.models.YahooStockPriceResponse;
import com.MA.winner.utils.ApiUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExternalDataSourceHandler {
    private final YahooStockPriceRequests yahooStockPriceRequests;
    private final ApiUtils apiUtils;;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public ExternalDataSourceHandler(String ticker, String startDate, String endDate) {
        this.yahooStockPriceRequests = YahooStockPriceRequests
                .builder()
                .ticker(ticker)
                .startDate(convertToUnixTimestamp(startDate))
                .endDate(convertToUnixTimestamp(endDate))
                .build();
        this.apiUtils = new ApiUtils();
    }

    public String convertToUnixTimestamp(String someDateString) {
        long unixTimestamp;
        try {
            Date date = sdf.parse(someDateString);
            unixTimestamp = date.getTime() / 1000; // Convert milliseconds to seconds
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return String.valueOf(unixTimestamp);
    }

    public YahooStockPriceResponse runAnalysis() throws IOException {
        return apiUtils.makeHTTPcall(yahooStockPriceRequests.getTickerDataURL());
    }

    public void makeCurl() {
        apiUtils.makeCurlCall("https://raw.githubusercontent.com/datasets/s-and-p-500-companies/master/data/constituents.csv");
    }

}
