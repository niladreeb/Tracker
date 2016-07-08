package com.demo.tracker.model;

import com.google.gson.annotations.SerializedName;

public class LocationEntity {

    @SerializedName("accuracy")
    public int accuracy;

    @SerializedName("coordinate")
    public PostCoordinateEntity coordinate;

    @SerializedName("heading")
    public float heading;

    @SerializedName("speed")
    public float speed;

    @SerializedName("timestamp")
    public String timestamp;

    public LocationEntity(int accuracy,PostCoordinateEntity coordinate,float heading,float speed,String timestamp) {
        this.accuracy = accuracy;
        this.coordinate = coordinate;
        this.heading = heading;
        this.speed = speed;
        this.timestamp = timestamp;
    }
}



