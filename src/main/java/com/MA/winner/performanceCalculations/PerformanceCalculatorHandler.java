package com.MA.winner.performanceCalculations;

import java.util.Map;

public interface PerformanceCalculatorHandler<T, V> {

    void generateResults();

    double[] generatePortfolio();

    Map<String, V>  fetchBestPortfolio(T data);
}
