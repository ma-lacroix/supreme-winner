package com.MA.winner.localDataStorage.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class StockDataResponse {

    String date;
    float open;
    float high;
    float low;
    float close;
    float adjClose;
    float volume;

}
