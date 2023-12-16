package com.MA.winner.performanceCalculations;

import com.MA.winner.localDataStorage.AnalysisDataHandler;
import com.MA.winner.localDataStorage.models.StocksRawData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class PerfCalculatorHandler {

    private static final Logger logger = Logger.getLogger(AnalysisDataHandler.class.getName());
    StocksRawData stocksRawData;

    float totalBudget;

    float defaultPortfolioReturn;

    float maxSharpeValue;

    Map<String, Integer> bestPortfolio;

    Map<String, Integer> maxNumOfStocks;

    public PerfCalculatorHandler(StocksRawData stocksRawData, Float totalBudget) {
        this.stocksRawData = stocksRawData;
        this.totalBudget = totalBudget;
        this.defaultPortfolioReturn = 0.03f;
        this.maxSharpeValue = -1.0f;
        this.bestPortfolio = new HashMap<>();
        this.maxNumOfStocks = new HashMap<>();
    }


    public List<Float> getReturns(String[] tickers) {
        List<Float> returns = new ArrayList<>();
        for (String ticker: tickers) {
            returns.add(stocksRawData.getStocksAnalysisData().get(ticker).get("avgReturn"));
        }
        return returns;
    }

    public List<Float> getRisks(String[] tickers) {
        List<Float> returns = new ArrayList<>();
        for (String ticker: tickers) {
            returns.add(stocksRawData.getStocksAnalysisData().get(ticker).get("stdClose"));
        }
        return returns;
    }

    public Boolean isWithinBudget(String[] tickers, Integer[] weights) {
        // budget spent should be around 90%-110% of totalBudget
        float budgetLeft = totalBudget;
        for (int i = 0; i < tickers.length; i++) {
            budgetLeft -= stocksRawData.getStocksAnalysisData().get(tickers[i]).get("avgClose") * weights[i];
        }
        return (budgetLeft <= totalBudget * 1.1 && budgetLeft >= totalBudget *0.9);
    }

    public float getSharpeRatio(Integer[] weights, List<Float> returns, List<Float> risks) {
        float numerator = 0.0f;
        float denominator = 0.0f;
        for (int i = 0; i < weights.length; i++) {
            numerator += weights[i] * (returns.get(i) - defaultPortfolioReturn);
            denominator += weights[i] * risks.get(i);
        }
        return numerator/denominator;
    }

    public void overrideBestPortfolio(String[] tickers, Integer[] weights) {
        bestPortfolio = new HashMap<>();
        for (int i = 0; i < tickers.length; i++) {
            bestPortfolio.put(tickers[i], weights[i]);
        }
    }

    public void solve(String[] tickers, Integer[] weights, int index) {
        // base case needs to be improved
        if (index == tickers.length) {
            logger.info("Trying: " + Arrays.toString(weights));
            if (isWithinBudget(tickers, weights)) {
                logger.info(Arrays.toString(weights));
                List<Float> returns = getReturns(tickers);
                List<Float> risks = getRisks(tickers);
                float sharpe = getSharpeRatio(weights, returns, risks);
                if (sharpe > maxSharpeValue) {
                    overrideBestPortfolio(tickers, weights);
                }
            }
            return;
        }
        for (int i = 0; i <= maxNumOfStocks.get(tickers[index]); i++) {
            weights[index] = i;
            solve(tickers, weights, index + 1);
        }
    }

    public void recursionController() {
        String[] tickers = stocksRawData.getStocksAnalysisData().keySet().toArray(new String[0]);
        for (String ticker: tickers) {
            float price = stocksRawData.getStocksAnalysisData().get(ticker).get("avgClose");
            maxNumOfStocks.put(ticker, (int) (totalBudget / price));
        }
        Integer[] weights = new Integer[tickers.length];
        Arrays.fill(weights, 0);
        int index = 0;
        solve(tickers, weights, index);
    }

    public void fetchBestPortfolio() {
        logger.info("Fetching best portfolio...");
        recursionController();
        logger.info("Sharpe says: \n");
        for (String key: bestPortfolio.keySet()) {
            System.out.println(key + " - Amount: " + bestPortfolio.get(key) + " - Price: " +
                    stocksRawData.getStocksAnalysisData().get(key).get("avgClose") * bestPortfolio.get(key));
        }
    }
}
