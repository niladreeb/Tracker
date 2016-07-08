package com.demo.tracker.model;

import com.google.gson.annotations.SerializedName;

public class TimestampEntity {

    @SerializedName("date")
    public String date;

    public TimestampEntity(String date) {
        this.date = date;
    }
}