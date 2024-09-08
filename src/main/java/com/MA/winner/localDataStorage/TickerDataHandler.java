package com.MA.winner.localDataStorage;

import com.MA.winner.localDataStorage.models.YahooStockPriceRequests;
import com.MA.winner.localDataStorage.models.StockDataResponse;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.hadoop.shaded.org.apache.http.ConnectionClosedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static com.MA.winner.utils.Utils.convertToUnixTimestamp;

public class TickerDataHandler {
    private final YahooStockPriceRequests yahooStockPriceRequests;
    public TickerDataHandler(String ticker, String startDate, String endDate) {
        this.yahooStockPriceRequests = YahooStockPriceRequests
                .builder()
                .ticker(ticker)
                .startDate(convertToUnixTimestamp(startDate))
                .endDate(convertToUnixTimestamp(endDate))
                .build();
    }

    public List<StockDataResponse> getTickerData() throws IOException {

        String urlString = yahooStockPriceRequests.getTickerDataURL();
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder content = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    String[] elements = inputLine.split(",");
                    String reconstructedLine = String.join(",", elements);
                    content.append(reconstructedLine).append("\n");
                }
                in.close();
                connection.disconnect();
                CsvMapper csvMapper = new CsvMapper();
                CsvSchema csvSchema = csvMapper.schemaFor(StockDataResponse.class).withHeader();
                MappingIterator<StockDataResponse> iterator = csvMapper.readerFor(StockDataResponse.class)
                        .with(csvSchema)
                        .readValues(content.toString());
                List<StockDataResponse> stockMetaDataResponses = iterator.readAll();
                return stockMetaDataResponses;
            }
            else {
                throw new ConnectionClosedException(responseCode + ": " + connection.getResponseMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
