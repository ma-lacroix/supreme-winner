package com.MA.superCode.BigQuery;

import com.MA.superCode.BigQuery.Models.QueryRequest;
import com.MA.superCode.config.BigQueryConfig;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobId;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.JobStatus;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;
import jdk.jshell.Snippet;

import java.sql.ResultSet;
import java.util.UUID;

public class BigQueryMain {

    BigQueryConfig bigQueryConfig;

    public BigQueryMain() {
        this.bigQueryConfig = new BigQueryConfig();
    }

    public TableResult runQuery(QueryRequest queryRequest) throws InterruptedException {

        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(
                queryRequest.getQueryObject())
                .setUseLegacySql(false)
                .build();
        JobId jobId = JobId.of(UUID.randomUUID().toString());
        Job queryJob = bigQueryConfig.getBigquery().create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

        JobStatus status = queryJob.getStatus();
        while (status.getState() != JobStatus.State.DONE) {
            queryJob.waitFor();
            status = queryJob.getStatus();
        }
        if (queryJob.getStatus().getError() != null) {
            // You can also look at queryJob.getStatus().getExecutionErrors() for all
            // errors, not just the latest one.
            throw new RuntimeException(queryJob.getStatus().getError().toString());
        }
        return queryJob.getQueryResults();
    }

    public void printQueryResults(TableResult tableResult) {
        for (FieldValueList row : tableResult.iterateAll()) {
            String url = row.get("url").getStringValue();
            String viewCount = row.get("view_count").getStringValue();
            System.out.printf("%s : %s views\n", url, viewCount);
        }
    }
}
