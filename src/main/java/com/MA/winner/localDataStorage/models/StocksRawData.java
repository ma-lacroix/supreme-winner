package com.MA.winner.localDataStorage.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class StocksRawData {

    private Map<String, Map<String, Float>> stocksAnalysisData;

}
