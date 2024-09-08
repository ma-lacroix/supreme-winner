package com.MA.winner.performanceCalculations;

import java.util.Map;

public interface PerformanceCalculatorHandler<T> {

    void generateResults();

    double[] generatePortfolio();

    Map<String, Integer>  fetchBestPortfolio(T data);
}
