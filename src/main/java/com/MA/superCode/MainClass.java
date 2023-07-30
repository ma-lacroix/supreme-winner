package com.MA.superCode;

import com.MA.superCode.BigQuery.BigQueryMain;
import com.MA.superCode.BigQuery.Models.QueryRequest;
import com.google.cloud.bigquery.TableResult;

import java.util.Objects;

public class MainClass {
    public static void main(String[] args) throws InterruptedException {
        BigQueryMain bigQueryMain = new BigQueryMain();

        QueryRequest queryRequest = QueryRequest.builder()
                .queryName("Testing for fun")
                .queryObject("SELECT CONCAT('https://stackoverflow.com/questions/', "
                        + "CAST(id as STRING)) as url, view_count "
                        + "FROM `bigquery-public-data.stackoverflow.posts_questions` "
                        + "WHERE tags like '%google-bigquery%' "
                        + "ORDER BY view_count DESC "
                        + "LIMIT 10")
                .build();

        TableResult results = bigQueryMain.runQuery(queryRequest);
        bigQueryMain.printQueryResults(results);

    }
}
