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
@JsonPropertyOrder({"symbol", "security", "sector", "industry"})
public class StockMetaDataResponse {
    // "Symbol","Security","Sector","Industry"
    String symbol;
    String security;
    String sector;
    String industry;


    public void printAll() {
        System.out.println(symbol);
        System.out.println(security);
        System.out.println(sector);
        System.out.println(industry);
    }
}
