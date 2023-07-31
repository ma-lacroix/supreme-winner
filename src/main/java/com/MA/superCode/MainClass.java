package com.MA.superCode;

import com.MA.superCode.BigQuery.BigQueryMain;
import com.MA.superCode.BigQuery.Models.QueryRequest;
import com.google.cloud.bigquery.TableResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainClass {
    public static void main(String[] args) throws InterruptedException {
        BigQueryMain bigQueryMain = new BigQueryMain();
        List<String> crimes = new ArrayList<>(Arrays.asList("year", "major_category", "cnt"));
        QueryRequest queryRequest = QueryRequest.builder()
                .resultsColumns(crimes)
                .queryName("Crime Categories in London")
                .queryObject(
                        "   SELECT year, major_category, SUM(value) AS cnt"
                        + " FROM `bigquery-public-data.london_crime.crime_by_lsoa` "
                        + " GROUP BY 1,2"
                        + " ORDER BY year, cnt DESC ")
                .build();
        TableResult results = bigQueryMain.runQuery(queryRequest);
        bigQueryMain.printQueryResults(results, queryRequest);

    }
}
