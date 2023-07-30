package com.MA.superCode.BigQuery.Models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class QueryRequest implements Serializable {
    String queryObject;
    String queryName;
}
