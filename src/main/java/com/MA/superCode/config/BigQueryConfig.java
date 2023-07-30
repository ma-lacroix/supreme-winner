package com.MA.superCode.config;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BigQueryConfig {

    private final BigQuery bigquery;

    public BigQueryConfig() {
        this.bigquery = BigQueryOptions.getDefaultInstance().getService();
    }
}
