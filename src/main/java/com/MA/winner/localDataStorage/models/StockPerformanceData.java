package com.MA.winner.localDataStorage.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StockPerformanceData {
    String symbol;
    float close;
    float roi;
    float std;
    float avg;

    public void printAll() {
        System.out.println("Symbol: " + symbol + ", Close: " + close + ", ROI: " + roi + ", STD: " + std + ", AVG: " + avg);
    }
}
