package com.MA.superCode.DataRequests;

import com.MA.superCode.BigQuery.Models.QueryRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class DataRequestObjects {
    // Trying to simulate a DB for testing purposes

    List<QueryRequest> bigQueryRequests;

    public DataRequestObjects() {
        bigQueryRequests = new ArrayList<>();
        requestObjects();
    }

    public void requestObjects() {
        // This is the "DB for holding BQ query requests"

        List<String> crimes1 = new ArrayList<>(Arrays.asList("year", "major_category", "cnt"));
        QueryRequest queryRequest1 = QueryRequest.builder()
                .resultsColumns(crimes1)
                .queryName("Major Crime Categories in London")
                .queryObject(
                        "   SELECT year, major_category, SUM(value) AS cnt"
                                + " FROM `bigquery-public-data.london_crime.crime_by_lsoa` "
                                + " GROUP BY 1,2"
                                + " ORDER BY year, cnt DESC ")
                .build();
        bigQueryRequests.add(queryRequest1);

        List<String> crimes2 = new ArrayList<>(Arrays.asList("year", "minor_category", "cnt"));
        QueryRequest queryRequest2 = QueryRequest.builder()
                .resultsColumns(crimes2)
                .queryName("Minor Crime Categories in London")
                .queryObject(
                        "   SELECT year, minor_category, SUM(value) AS cnt"
                                + " FROM `bigquery-public-data.london_crime.crime_by_lsoa` "
                                + " GROUP BY 1,2"
                                + " ORDER BY year, cnt DESC ")
                .build();
        bigQueryRequests.add(queryRequest2);
    }


}
