package com.MA.winner.performanceCalculations;

import com.MA.winner.localDataStorage.AnalysisDataHandler;
import com.MA.winner.localDataStorage.models.StocksRawData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PerfCalculatorHandler {

    private static final Logger logger = Logger.getLogger(AnalysisDataHandler.class.getName());
    StocksRawData stocksRawData;

    String[] tickers;

    float totalBudget;

    float defaultPortfolioReturn;

    float maxSharpeValue;

    float riskLevel;

    Float[] returns;

    Float[] risks;

    Map<String, Integer> bestPortfolio;

    Integer maxNumOfStocks;

    public PerfCalculatorHandler(StocksRawData stocksRawData, Float totalBudget) {
        this.stocksRawData = stocksRawData;
        this.tickers = stocksRawData.getStocksAnalysisData().keySet().toArray(new String[0]);;
        this.totalBudget = totalBudget;
        this.defaultPortfolioReturn = 0.03f;
        this.maxSharpeValue = -Float.MAX_VALUE;
        this.riskLevel = 0.25f;
        this.returns = getReturns();
        this.risks = getRisks();
        this.bestPortfolio = new HashMap<>();
        this.maxNumOfStocks = 0;
    }


    public Float[] getReturns() {
        Float[] returns = new Float[tickers.length];
        for (int i = 0; i < tickers.length; i++) {
            returns[i] = stocksRawData.getStocksAnalysisData().get(tickers[i]).get("avgReturn");
        }
        return returns;
    }

    public Float[] getRisks() {
        Float[] risks = new Float[tickers.length];
        for (int i = 0; i < tickers.length; i++) {
            risks[i] = stocksRawData.getStocksAnalysisData().get(tickers[i]).get("stdClose");
        }
        return risks;
    }

    public boolean isDiverseEnough(Integer[] weights) {
        int zeros = 0;
        for (Integer weight : weights) {
            if (weight == 0) zeros++;
        }
        for (int i = 0; i < tickers.length; i++) {
            float amount = stocksRawData.getStocksAnalysisData().get(tickers[i]).get("avgClose") * weights[i];
            if (amount/totalBudget <= (float) 1 /weights.length && weights[i] > 0) {
                return false;
            }
        }
        return ((float) zeros/weights.length <= riskLevel);
    }

    public boolean isWithinBudget(float budgetLeft) {
        // budget spent should be around 90%-110% of totalBudget
        return (budgetLeft >= -(totalBudget * 0.1f) && budgetLeft <= totalBudget * 0.1f);
    }

    public float getBudgetLet(Integer[] weights) {
        // budget spent should be around 90%-110% of totalBudget
        float budgetLeft = totalBudget;
        for (int i = 0; i < tickers.length; i++) {
            budgetLeft -= stocksRawData.getStocksAnalysisData().get(tickers[i]).get("avgClose") * weights[i];
        }
        return budgetLeft;
    }

    public float getSharpeRatio(Integer[] weights) {
        float numerator = 0.0f;
        float denominator = 0.0f;
        int totalWeights = 0;
        for (Integer weight : weights) {
            totalWeights += weight;
        }
        for (int i = 0; i < weights.length; i++) {
            numerator += (weights[i] * (returns[i] - defaultPortfolioReturn))/totalWeights;
            for (int j = 0; j < weights.length; j++) {
                // TODO: normally we'd have correlation coefficients between instruments here
                denominator += (weights[i] * weights[j] * risks[i] * risks[j]) * (1 + (float) weights[i] /totalWeights);
            }
        }
        return (numerator/denominator);
    }

    public void overrideBestPortfolio(Integer[] weights) {
        bestPortfolio = new HashMap<>();
        for (int i = 0; i < tickers.length; i++) {
            bestPortfolio.put(tickers[i], weights[i]);
        }
    }

    public void solve(Integer[] weights, int index) {
        if (index == weights.length) {
            return;
        }

        for (int i = 0; i <= maxNumOfStocks; i++) {
            weights[index] = i;
            float budgetLeft = getBudgetLet(weights);
            if (budgetLeft < 0.0f && !isWithinBudget(budgetLeft)) {
                weights[index] = 0;
                return;
            }
            if (isWithinBudget(budgetLeft) && isDiverseEnough(weights)) {
                float sharpe = getSharpeRatio(weights);
                if (sharpe > maxSharpeValue) {
                    overrideBestPortfolio(weights);
                    maxSharpeValue = sharpe;
                }
            }
            solve(weights, index + 1);
        }
    }

    public void recursionController() {
        for (String ticker: tickers) {
            float price = stocksRawData.getStocksAnalysisData().get(ticker).get("avgClose");
            maxNumOfStocks = Math.max(maxNumOfStocks, (int) (totalBudget / price));
        }
        Integer[] weights = new Integer[tickers.length];
        Arrays.fill(weights, 0);
        int index = 0;
        solve(weights, index);

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
