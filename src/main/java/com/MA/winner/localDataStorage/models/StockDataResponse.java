package com.MA.winner.localDataStorage.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockDataResponse implements Serializable {
    @JsonProperty("symbol")
    private String tickerName;
    @JsonProperty("historical")
    private List<StockDailyData> stockDailyDataList;
}
