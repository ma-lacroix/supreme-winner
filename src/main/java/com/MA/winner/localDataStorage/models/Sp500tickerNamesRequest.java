package com.MA.winner.localDataStorage.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sp500tickerNamesRequest {

    private String url;

    public Sp500tickerNamesRequest() {
        this.url = "https://raw.githubusercontent.com/datasets/s-and-p-500-companies/master/data/constituents.csv";
    }
}
