package com.MA.superCode.config;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BigQueryConfig {

    private final BigQuery bigquery;

    public BigQueryConfig() {
        this.bigquery = BigQueryOptions.getDefaultInstance().getService();
    }
}
