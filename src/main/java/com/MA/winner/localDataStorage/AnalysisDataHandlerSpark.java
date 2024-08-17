package com.MA.winner.localDataStorage;

import com.MA.winner.localDataStorage.models.StockDataResponse;
import com.MA.winner.localDataStorage.models.StockMetaDataResponse;
import com.MA.winner.localDataStorage.models.StocksRawData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.spark.sql.Row;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.*;

public class AnalysisDataHandlerSpark {

    private static final Logger logger = Logger.getLogger(AnalysisDataHandlerSpark.class.getName());
    private final AllTickersDataHandler allTickersDataHandler;
    private final Float maxStockValue;
    private final String startDate;
    private final String endDate;
    private final String sector;
//    protected SparkSession spark;

    public AnalysisDataHandlerSpark(Float maxStockValue, String startDate, String endDate, String sector) {
        this.allTickersDataHandler = new AllTickersDataHandler();
        this.maxStockValue = maxStockValue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sector = (sector==null) ? "all" : sector;
//        this.spark = SparkSession.builder()
//                .appName("stocks")
//                .master("local[*]")
//                .getOrCreate();
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

    public StocksRawData getStocksAnalysisData() throws IOException {
        logger.info("Getting stocks data");
        Map<String, Map<String, Float>> stocksAnalysisData = new HashMap<>();
        List<String> tickers = getSp500Tickers();
        System.out.println(tickers.toString());
        // TODO: use Spark
        return StocksRawData.builder()
                .stocksAnalysisData(stocksAnalysisData)
                .build();
    }
}
