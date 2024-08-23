package com.MA.winner.performanceCalculations;

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

    public float getSharpeRatio(double[] shares) {
        float num = 1.0f;
        float denum = 1.0f;
        for (int i = 0; i < stocksListSize; i++) {
            num += (float) (stockPerformanceDataList.get(i).getRoi() * shares[i]);
            denum += (float) (stockPerformanceDataList.get(i).getStd() * shares[i]);
        }
        return num/denum;
    }

    public Row generatePortolio() {
        double[] shares = randomShareOfStocks();
        float sharpe = getSharpeRatio(shares);
        List<Object> rowElements = new ArrayList<>();
        for (int i = 0; i < stocksListSize; i++) {
            rowElements.add(stockPerformanceDataList.get(i).getClose());
            rowElements.add(stockPerformanceDataList.get(i).getStd());
            rowElements.add(stockPerformanceDataList.get(i).getRoi());
            rowElements.add(shares[i]);
        }
        rowElements.add(sharpe);
        return RowFactory.create(rowElements.toArray());
    }

    public StructType generateSchema() {
        List<StructField> fields = new ArrayList<>();
        for (StockPerformanceData stockPerformanceData: stockPerformanceDataList) {
            String tickerName = stockPerformanceData.getSymbol();
            fields.add(DataTypes.createStructField(tickerName + "_clo", DataTypes.FloatType, false));
            fields.add(DataTypes.createStructField(tickerName + "_std", DataTypes.FloatType, false));
            fields.add(DataTypes.createStructField(tickerName + "_roi", DataTypes.FloatType, false));
            fields.add(DataTypes.createStructField(tickerName + "_sha", DataTypes.DoubleType, false));
        }
        fields.add(DataTypes.createStructField("sharpe", DataTypes.FloatType, false));
        return DataTypes.createStructType(fields);
    }

    public void generateResults() {
        List<Row> rows = new ArrayList<>();
        for (int i = 0; i < numSamples; i++) {
            rows.add(generatePortolio());
        }
        Dataset<Row> df = spark.createDataFrame(rows, generateSchema());
        df.show(100);
        // TODO: all that's left now is to calculate which portfolios can be bought and which one is the best
        // Another idea: separate the portfolio metadata in a separate dataframe
    }
}
