package com.MA.superCode.BigQuery.Models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class QueryRequest implements Serializable {

    List<String> resultsColumns;
    String queryObject;
    String queryName;
}
