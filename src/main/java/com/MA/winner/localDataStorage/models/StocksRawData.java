package com.MA.winner.localDataStorage.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class StocksRawData {

    private Map<String, Map<String, Float>> stocksAnalysisData;

}
