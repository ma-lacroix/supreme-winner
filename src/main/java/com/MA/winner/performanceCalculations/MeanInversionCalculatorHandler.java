package com.MA.winner.performanceCalculations;

import com.MA.winner.localDataStorage.AnalysisDataHandler;
import com.MA.winner.localDataStorage.models.MeanInversionResult;
import com.MA.winner.localDataStorage.models.StockPerformanceData;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.MA.winner.utils.Utils.printMeanInversionRecommendation;

public class MeanInversionCalculatorHandler implements PerformanceCalculatorHandler<double[], MeanInversionResult>{

    private static final Logger logger = Logger.getLogger(AnalysisDataHandler.class.getName());
    List<StockPerformanceData> stockPerformanceDataList;
    int stocksListSize;
    float budget;
    protected SparkSession spark;

    public MeanInversionCalculatorHandler(List<StockPerformanceData> stockPerformanceDataList, float budget) {
        this.stockPerformanceDataList = stockPerformanceDataList;
        this.budget = budget;
        this.stocksListSize = stockPerformanceDataList.size();
    }

    @Override
    public double[] generatePortfolio() {
        //  Unused function
        double[] portfolio = new double[stocksListSize];
        Arrays.fill(portfolio, 1.0);
        return portfolio;
    }

    @Override
    public Map<String, MeanInversionResult> fetchBestPortfolio(double[] data) {
        Map<String, MeanInversionResult> results = new HashMap<>();
        stockPerformanceDataList.forEach(stock -> {
            float upper = stock.getAvg() + (stock.getStd() * 1.96f);
            float lower = stock.getAvg() - (stock.getStd() * 1.96f);
            if (stock.getClose() >= upper) {
                results.put(stock.getSymbol(), MeanInversionResult.SELL);
            } else if (stock.getClose() <= lower) {
                results.put(stock.getSymbol(), MeanInversionResult.BUY);
            } else {
                results.put(stock.getSymbol(), MeanInversionResult.HOLD);
            }
        });
        return results;
    }

    @Override
    public void generateResults() {
        double[] portfolio = generatePortfolio();
        Map<String, MeanInversionResult> recommendations = fetchBestPortfolio(portfolio);
        printMeanInversionRecommendation(recommendations);
    }
}
