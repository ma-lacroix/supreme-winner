package com.MA.winner.performanceCalculations;

import com.MA.winner.localDataStorage.models.StockPerformanceData;
import org.apache.spark.sql.SparkSession;

import java.util.List;
import java.util.Random;

public class PerfCalculatorHandler {

    List<StockPerformanceData> stockPerformanceDataList;
    int stocksListSize;
    long numSamples;
    Random random;
    protected SparkSession spark;

    public PerfCalculatorHandler(List<StockPerformanceData> stockPerformanceDataList, long numSamples) {
        this.stockPerformanceDataList = stockPerformanceDataList;
        this.stocksListSize = stockPerformanceDataList.size();
        this.numSamples = numSamples;
        this.random = new Random();
        this.spark = SparkSession.builder()
                .appName("performance")
                .master("local[*]")
                .getOrCreate();
    }

    public double[] randomShareOfStocks() {
        int[] numbers = new int[stocksListSize];
        double[] shares = new double[stocksListSize];
        int total = 0;

        for (int i = 0; i < stocksListSize; i++) {
            int num = random.nextInt(100) + 1;
            numbers[i] = num;
            total += num;
        }
        for (int i = 0; i < stocksListSize; i++) {
            shares[i] = (double) numbers[i] / total;
        }
        return shares;
    }

    public void generatePortolio() {
        double[] shares = randomShareOfStocks();
        for (int i = 0; i < stocksListSize; i++) {
            System.out.println(stockPerformanceDataList.get(i).getSymbol() + " " + shares[i]);
        }
    }

    // TODO: generate SparkDF from this point
}
