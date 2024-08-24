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
}
