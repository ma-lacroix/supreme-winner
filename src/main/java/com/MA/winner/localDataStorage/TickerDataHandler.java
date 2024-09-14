package com.MA.winner.localDataStorage;

import com.MA.winner.localDataStorage.models.FmpStockPriceRequests;
import com.MA.winner.localDataStorage.models.StockDataResponse;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.hadoop.shaded.org.apache.http.ConnectionClosedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class TickerDataHandler {
    private final FmpStockPriceRequests fmpStockPriceRequests;
    public TickerDataHandler(String ticker, String startDate, String endDate) {
        this.fmpStockPriceRequests = FmpStockPriceRequests
                .builder()
                .ticker(ticker)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

    public List<StockDataResponse> getTickerData() throws IOException {

        String urlString = fmpStockPriceRequests.getTickerDataURL();
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
                // TODO: use Jackson to map response from fmp

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
