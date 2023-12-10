package com.MA.winner.performanceCalculations;

import com.MA.winner.localDataStorage.models.StocksRawData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerfCalculatorHandler {

    StocksRawData stocksRawData;

    Float totalBudget;

    Float defaultPortfolioReturn;

    public PerfCalculatorHandler(StocksRawData stocksRawData, Float totalBudget) {
        this.stocksRawData = stocksRawData;
        this.totalBudget = totalBudget;
        this.defaultPortfolioReturn = 0.03f;
    }

    public float getSharpeRatio(List<Integer> weights, List<Float> returns, List<Float> risks) {
        float numerator = 0.0f;
        float denominator = 0.0f;
        for (int i = 0; i < weights.size(); i++) {
            numerator += weights.get(i) * (returns.get(i) - defaultPortfolioReturn);
            denominator += weights.get(i) * risks.get(i);
        }
        return numerator/denominator;
    }

    public List<Integer> genWeights() {
        // TODO
        return new ArrayList<>();
    }

    public List<String> genTickerCombinations() {
        // TODO
        return new ArrayList<>();
    }

}
