package com.MA.winner.localDataStorage;

import com.MA.winner.localDataStorage.models.Sp500tickerNamesRequest;
import com.MA.winner.localDataStorage.models.StockDataResponse;
import com.MA.winner.localDataStorage.models.StockMetaDataResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class AllTickersDataHandler {

    public List<StockMetaDataResponse> getAllTickers() throws IOException {

        String urlString = "https://raw.githubusercontent.com/datasets/s-and-p-500-companies/master/data/constituents.csv";
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
                    String[] elements = inputLine
                            .replaceAll("\"", "")
                            .replaceAll("\\.", "")
                            .replaceAll("\\&", "and")
                            .split(",");
                    String[] firstFourElements = new String[Math.min(4, elements.length)];
                    System.arraycopy(elements, 0, firstFourElements, 0, firstFourElements.length);
                    String reconstructedLine = String.join(",", firstFourElements);
                    System.out.println(reconstructedLine);
                    content.append(reconstructedLine).append("\n");
                }
                in.close();
                connection.disconnect();
                CsvMapper csvMapper = new CsvMapper();
                CsvSchema csvSchema = csvMapper.schemaFor(StockMetaDataResponse.class).withHeader();
                MappingIterator<StockMetaDataResponse> iterator = csvMapper.readerFor(StockMetaDataResponse.class)
                        .with(csvSchema)
                        .readValues(content.toString());
                List<StockMetaDataResponse> stockMetaDataResponses = iterator.readAll();
                stockMetaDataResponses.forEach(StockMetaDataResponse::printAll);
                return stockMetaDataResponses;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
        }
}
