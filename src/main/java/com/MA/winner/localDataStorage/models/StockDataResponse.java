package com.MA.winner.localDataStorage.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"date","open","high","low","close","adjClose","volume"})
public class StockDataResponse {

    String date;
    float open;
    float high;
    float low;
    float close;
    float adjClose;
    float volume;

}
