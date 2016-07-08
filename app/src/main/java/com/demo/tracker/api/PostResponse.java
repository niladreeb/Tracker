package com.demo.tracker.api;

import com.demo.tracker.model.PostCoordinateEntity;
import com.demo.tracker.model.TimestampEntity;
import com.google.gson.annotations.SerializedName;

public class PostResponse {

    @SerializedName("accuracy")
    public int accuracy;

    @SerializedName("coordinate")
    public PostCoordinateEntity coordinate;

    @SerializedName("heading")
    public float heading;

    @SerializedName("speed")
    public float speed;

    @SerializedName("timestamp")
    public TimestampEntity timestamp;


}



