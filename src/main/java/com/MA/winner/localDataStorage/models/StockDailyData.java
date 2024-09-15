package com.MA.winner.localDataStorage.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockDailyData implements Serializable {

    @JsonProperty("date")
    private String date;
    @JsonProperty("open")
    private float open;
    @JsonProperty("high")
    private float high;
    @JsonProperty("low")
    private float low;
    @JsonProperty("close")
    private float close;
    @JsonProperty("adjClose")
    private float adjClose;
    @JsonProperty("volume")
    private float volume;
    @JsonProperty("unadjustedVolume")
    private int unadjustedVolume;
    @JsonProperty("change")
    private float change;
    @JsonProperty("changePercent")
    private float changePercent;
    @JsonProperty("vwap")
    private float vwap;
    @JsonProperty("label")
    private String label;
    @JsonProperty("changeOverTime")
    private float changeOverTime;
}
