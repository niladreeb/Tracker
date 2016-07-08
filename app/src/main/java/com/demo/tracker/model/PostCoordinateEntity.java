package com.demo.tracker.model;

import com.google.gson.annotations.SerializedName;

public class PostCoordinateEntity {

    @SerializedName("lat")
    public double lat;

    @SerializedName("lng")
    public double lng;

    public PostCoordinateEntity(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}