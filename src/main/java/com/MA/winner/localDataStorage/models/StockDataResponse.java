package com.MA.winner.localDataStorage.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Setter
public class StockDataResponse {

    private Map<String, List<String>> stockData;

    private List<String> cols;

    public StockDataResponse(List<String> cols) {
        this.stockData = new HashMap<>();
        this.cols = cols;
        for (String col: cols){
            stockData.put(col, new ArrayList<>());
        }
    }
}
