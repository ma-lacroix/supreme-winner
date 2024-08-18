package com.MA.winner.localDataStorage;

import com.MA.winner.localDataStorage.models.StockDataResponse;
import com.MA.winner.localDataStorage.models.StockMetaDataResponse;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.RowFactory;

import static org.apache.spark.sql.functions.*;

public class AnalysisDataHandler {

    private static final Logger logger = Logger.getLogger(AnalysisDataHandler.class.getName());
    private final AllTickersDataHandler allTickersDataHandler;
    private final Float maxStockValue;
    private final String startDate;
    private final String endDate;
    private final String sector;
    protected SparkSession spark;

    public AnalysisDataHandler(Float maxStockValue, String startDate, String endDate, String sector) {
        this.allTickersDataHandler = new AllTickersDataHandler();
        this.maxStockValue = maxStockValue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sector = (sector==null) ? "all" : sector;
        this.spark = SparkSession.builder()
                .appName("stocks")
                .master("local[*]")
                .getOrCreate();
    }

    public List<String>  getSp500Tickers() throws IOException {
        logger.info("Getting sp500 tickers");
        List<String> tickers = new ArrayList<>();
        List<StockMetaDataResponse> stockMetaDataResponses = allTickersDataHandler.getAllTickers();
        if (sector.equals("all")) {
            stockMetaDataResponses.forEach(response -> tickers.add(response.getSymbol()));
        } else {
            stockMetaDataResponses.stream()
                    .filter(response -> response.getSector().equals(sector))
                    .forEach(response -> tickers.add(response.getSymbol()));
        }
        return tickers;
    }

    public List<Row> fetchTickerData(List<String> tickers) {
        List<Row> rows = new ArrayList<>();
        for (String tickerName: tickers) {
            TickerDataHandler tickerDataHandler = new TickerDataHandler(tickerName, startDate, endDate);
            List<StockDataResponse> stockDataResponses = tickerDataHandler.getTickerData();
            for (StockDataResponse response: stockDataResponses) {
                rows.add(RowFactory.create(tickerName,
                        response.getDate(),
                        response.getOpen(),
                        response.getHigh(),
                        response.getLow(),
                        response.getClose(),
                        response.getAdjClose(),
                        response.getVolume()));
            }
        }
        return rows;
    }

    public Dataset<Row> createSparkDF(List<Row> rows) {

        StructType schema = new StructType(new StructField[]{
                DataTypes.createStructField("symbol", DataTypes.StringType, true),
                DataTypes.createStructField("date", DataTypes.StringType, true),
                DataTypes.createStructField("open", DataTypes.FloatType, true),
                DataTypes.createStructField("high", DataTypes.FloatType, true),
                DataTypes.createStructField("low", DataTypes.FloatType, true),
                DataTypes.createStructField("close", DataTypes.FloatType, true),
                DataTypes.createStructField("adjClose", DataTypes.FloatType, true),
                DataTypes.createStructField("volume", DataTypes.FloatType, true)
        });
        Dataset<Row> df = spark.createDataFrame(rows, schema);
        String maxDate = df.select("date").agg(max(col("date"))).first().getString(0); // weekends
        Dataset<Row> stocksWithingBudget = df.filter(df.col("date").equalTo(maxDate))
                .filter(df.col("close").leq(maxStockValue)).select(col("symbol"));
        df = df.alias("t1")
                .join(stocksWithingBudget.alias("t2"), col("t1.symbol").equalTo(col("t2.symbol")))
                .drop(col("t2.symbol"));
        return df;
    }

    public Dataset<Row> genStocksAnalysisData(Dataset<Row> df) {
        String minDate = df.select("date").agg(min(col("date"))).first().getString(0); // weekends
        String maxDate = df.select("date").agg(max(col("date"))).first().getString(0); // weekends
        Dataset<Row> roiDFMin = df.filter(col("date").equalTo(minDate))
                .select(col("symbol"), col("close"));
        Dataset<Row> roiDFMax = df.filter(col("date").equalTo(maxDate))
                .select(col("symbol"), col("close"));
        Dataset<Row> roiDF = roiDFMin.alias("t1")
                .join(roiDFMax.alias("t2"), col("t1.symbol").equalTo(col("t2.symbol")))
                        .withColumn("roi", round(col("t2.close")
                                .minus(col("t1.close"))
                                .divide(col("t1.close"))
                                .multiply(100), 2))
                .drop(col("t2.symbol"), col("t2.close"));
        Dataset<Row> basicDataDF = df.groupBy(col("symbol")).agg(
                    round(std(col("close")),2).alias("std"),
                    round(avg(col("close")),2).alias("avg"));
        return roiDF.alias("t1").join(basicDataDF.alias("t2"), col("t1.symbol").equalTo(col("t2.symbol")))
                .drop(col("t2.symbol"));
    }

    public void getStocksAnalysisData() throws IOException {
        logger.info("Getting stocks data");
        List<String> tickers = getSp500Tickers();
        System.out.println(tickers.toString());
        List<Row> dataRows = fetchTickerData(tickers);
        Dataset<Row> sparkDF = createSparkDF(dataRows);
        sparkDF = genStocksAnalysisData(sparkDF);
        sparkDF.show();
        // TODO: do something with the DF
    }
}
