package com.MA.winner.performanceCalculations;

import com.MA.winner.localDataStorage.AnalysisDataHandler;
import com.MA.winner.localDataStorage.models.StockPerformanceData;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

import static com.MA.winner.utils.Utils.printRecommendation;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.max;

public class PerfCalculatorHandler {

    private static final Logger logger = Logger.getLogger(AnalysisDataHandler.class.getName());
    List<StockPerformanceData> stockPerformanceDataList;
    int stocksListSize;
    float budget;
    long numSamples;
    Random random;
    float maxSharpe;
    protected SparkSession spark;

    public PerfCalculatorHandler(List<StockPerformanceData> stockPerformanceDataList, long numSamples, float budget) {
        this.stockPerformanceDataList = stockPerformanceDataList;
        this.stocksListSize = stockPerformanceDataList.size();
        this.numSamples = numSamples;
        this.budget = budget;
        this.random = new Random();
        this.maxSharpe = 0.0f;
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

    public float getSharpeRatio(double[] shares) {
        // The secret sauce
        float num = 1.0f;
        float denum = 1.0f;
        for (int i = 0; i < stocksListSize; i++) {
            num += (float) (stockPerformanceDataList.get(i).getRoi() * shares[i]);
            denum += (float) (stockPerformanceDataList.get(i).getStd() * shares[i]);
        }
        return num/denum;
    }

    public double[] generatePortolio() {
        double[] portfolio = new double[stocksListSize];
        for (int i = 0; i < numSamples; i++) {
            if (i % 100000 == 0) {
                logger.info((int) ((i / (double) numSamples) * 100) + " % done");
            }
            double[] shares = randomShareOfStocks();
            float sharpe = getSharpeRatio(shares);
            if (sharpe >= maxSharpe) {
                maxSharpe = sharpe;
                portfolio = shares;
            }
        }
        return portfolio;
    }

    public Map<String, Integer> generateBestPortfolio(double[] rowWithMaxSharpe) {
        Map<String, Integer> inv = new HashMap<>();
        for (int i = 0; i < stocksListSize; i++) {
            String name = stockPerformanceDataList.get(i).getSymbol();
            float close = stockPerformanceDataList.get(i).getClose();
            double share = rowWithMaxSharpe[i];
            int stocks = Math.toIntExact(Math.round((share * budget) / close));
            if (stocks > 0) {
                inv.put(name, stocks);
            }
        }
        return inv;
    }

    public void generateResults() throws Exception {
        double[] portfolio = generatePortolio();
        logger.info("100 % done");
        Map<String, Integer> best = generateBestPortfolio(portfolio);
        printRecommendation(best);
    }
}
