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
    protected SparkSession spark;

    public PerfCalculatorHandler(List<StockPerformanceData> stockPerformanceDataList, long numSamples, float budget) {
        this.stockPerformanceDataList = stockPerformanceDataList;
        this.stocksListSize = stockPerformanceDataList.size();
        this.numSamples = numSamples;
        this.budget = budget;
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
        // The secret sauce
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
        if (sharpe <= 1.0f) {
            // Tossing away negatives
            return null;
        }
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

    public Map<String, Integer> generateBestPortfolio(Row rowWithMaxSharpe) {
        Map<String, Integer> inv = new HashMap<>();
        int size = rowWithMaxSharpe.size() - rowWithMaxSharpe.size() % 4;
        for (int i = 0; i < size; i+=4) {
            String name = rowWithMaxSharpe.schema().fieldNames()[i].split("_")[0];
            float close = rowWithMaxSharpe.getFloat(i);
            double share = rowWithMaxSharpe.getDouble(i + 3);
            int stocks = Math.toIntExact(Math.round((share * budget) / close));
            if (stocks > 0) {
                inv.put(name, stocks);
            }
        }
        return inv;
    }

    public void generateResults() {
        // TODO: why does this create a heap memory error when I filter out some stocks
        List<Row> rows = new ArrayList<>();
        for (int i = 0; i < numSamples; i++) {
            if (i % 100000 == 0) {
                logger.info((int) ((i / (double) numSamples) * 100) + " % done");
            }
            Row portfolio = generatePortolio();
            if (portfolio != null) {
                rows.add(portfolio);
            }
        }
        logger.info("100 % done");
        Dataset<Row> df = spark.createDataFrame(rows, generateSchema());
        float maxSharpe = df.agg(max("sharpe")).first().getFloat(0);
        Row rowWithMaxSharpe = df.filter(col("sharpe").equalTo(maxSharpe)).first();
        System.out.println(rowWithMaxSharpe);
        for (int i = 0; i < rowWithMaxSharpe.size(); i++) {
            String name = rowWithMaxSharpe.schema().fieldNames()[i];
            Object object = rowWithMaxSharpe.get(i);
            logger.info(name + " " + object);
        }
        Map<String, Integer> best = generateBestPortfolio(rowWithMaxSharpe);
        printRecommendation(best);
    }
}
