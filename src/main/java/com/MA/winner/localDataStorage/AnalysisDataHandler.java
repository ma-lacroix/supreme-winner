package com.MA.winner.localDataStorage;

import com.MA.winner.localDataStorage.models.StockDataResponse;
import com.MA.winner.localDataStorage.models.StocksRawData;

import java.util.logging.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalysisDataHandler {

    private static final Logger logger = Logger.getLogger(AnalysisDataHandler.class.getName());

    private final AllTickersDataHandler allTickersDataHandler;

    private final Float maxStockValue;
    private final String startDate;
    private final String endDate;
    private final String sector;

    public AnalysisDataHandler(Float maxStockValue, String startDate, String endDate, String sector) {
        this.allTickersDataHandler = new AllTickersDataHandler();
        this.maxStockValue = maxStockValue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sector = (sector==null) ? "all" : sector;
    }

    public List<String> getSp500Tickers() {
        logger.info("Getting sp500 tickers");
        if (sector.equals("all")) {
            return allTickersDataHandler.getAllTickers().getStockData().get("Symbol");
        }
        List<String> tickers = new ArrayList<>();
        StockDataResponse data = allTickersDataHandler.getAllTickers();
        for (int i = 0; i < data.getStockData().get("Sector").size(); i++) {
            if (data.getStockData().get("Sector").get(i).equals(sector)) {
                tickers.add(data.getStockData().get("Symbol").get(i));
            }
        }
        return tickers;
    }

    public StocksRawData getStocksAnalysisData() throws IOException {
        logger.info("Getting stocks data");
        Map<String, Map<String, Float>> stocksAnalysisData = new HashMap<>();
        List<String> tickers = getSp500Tickers();
        for (String ticker: tickers) {
            TickerDataHandler tickerDataHandler = new TickerDataHandler(ticker, startDate, endDate);
            StockDataResponse stockDataResponse = tickerDataHandler.getTickerData();
            int len = stockDataResponse.getStockData().get("Date").size();
            // skipping tickers above budget
            if (Float.parseFloat(stockDataResponse.getStockData().get("Close").get(len-1)) > maxStockValue) {
                continue;
            }
            // TODO: missing -> ROIs
            Map<String, Float> stockDescriptiveData = new HashMap<>();
            float totalClose = 0;
            float minClose = Float.MAX_VALUE;
            float maxClose = 0;
            float totalDeviationClose = 0;
            float totalVolume = 0;
            float minVolume = Float.MAX_VALUE;
            float maxVolume = 0;
            float totalDeviationVolume = 0;
            for (int i = 0; i < len; i++) {
                float close = Float.parseFloat(stockDataResponse.getStockData().get("Close").get(i));
                float volume = Float.parseFloat(stockDataResponse.getStockData().get("Volume").get(i));
                totalClose += close;
                minClose = Math.min(close, minClose);
                maxClose = Math.max(close, maxClose);
                totalVolume += volume;
                minVolume = Math.min(volume, minVolume);
                maxVolume = Math.max(volume, maxVolume);
            }
            float roi = Float.parseFloat(stockDataResponse.getStockData().get("Close").get(len-1))/
                    Float.parseFloat(stockDataResponse.getStockData().get("Close").get(0)) - 1;
            stockDescriptiveData.put("minClose", minClose);
            stockDescriptiveData.put("maxClose", maxClose);
            stockDescriptiveData.put("avgClose", totalClose/len);
            stockDescriptiveData.put("avgReturn", roi);
            stockDescriptiveData.put("minVolume", minVolume);
            stockDescriptiveData.put("maxVolume", maxVolume);
            stockDescriptiveData.put("avgVolume", totalVolume/len);
            // standard deviation
            for (int i = 0; i < len; i++) {
                float close = Float.parseFloat(stockDataResponse.getStockData().get("Close").get(i));
                float volume = Float.parseFloat(stockDataResponse.getStockData().get("Volume").get(i));
                totalDeviationClose += (float) Math.pow(close - stockDescriptiveData.get("avgClose"),2);
                totalDeviationVolume += (float) Math.pow(volume - stockDescriptiveData.get("avgVolume"),2);
            }
            stockDescriptiveData.put("stdClose", (float) Math.sqrt(totalDeviationClose/len));
            stockDescriptiveData.put("stdVolume", (float) Math.sqrt(totalDeviationVolume/len));
            stocksAnalysisData.put(ticker, stockDescriptiveData);
        }
        return StocksRawData.builder()
                .stocksAnalysisData(stocksAnalysisData)
                .build();
    }
}
