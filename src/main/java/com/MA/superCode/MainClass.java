package com.MA.superCode;

import com.MA.superCode.BigQuery.BigQueryMain;
import com.MA.superCode.BigQuery.Models.QueryRequest;
import com.MA.superCode.DataRequests.DataRequestObjects;
import com.google.cloud.bigquery.TableResult;


public class MainClass {
    public static void main(String[] args) throws InterruptedException {
        BigQueryMain bigQueryMain = new BigQueryMain();
        DataRequestObjects dataRequestObjects = new DataRequestObjects();
        for (QueryRequest queryRequest: dataRequestObjects.getBigQueryRequests()) {
            TableResult results = bigQueryMain.runQuery(queryRequest);
            bigQueryMain.printQueryResults(results, queryRequest);
            Thread.sleep(500);
        }

    }
}
