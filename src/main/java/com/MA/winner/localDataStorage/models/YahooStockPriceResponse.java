package com.MA.winner.localDataStorage.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Setter
public class YahooStockPriceResponse {

    private Map<String, List<String>> stockData;

    private List<String> cols;

    public YahooStockPriceResponse() {
        this.stockData = new HashMap<>();
        this.cols = Arrays.asList("Date","Open","High","Low","Close","Adj Close","Volume");
        for (String col: cols){
            stockData.put(col, new ArrayList<>());
        }
    }
}
