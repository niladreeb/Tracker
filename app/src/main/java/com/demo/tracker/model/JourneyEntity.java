package com.demo.tracker.model;

import com.google.gson.annotations.SerializedName;

public class JourneyEntity {

    @SerializedName("journeyID")
    public String id;

    @SerializedName("startLocality")
    public String startLocality;

    @SerializedName("endLocality")
    public String endLocality;

    @SerializedName("distance")
    public String distance;

    @SerializedName("duration")
    public String duration;

    @SerializedName("startDate")
    public DateEntity startDate;

    @SerializedName("endDate")
    public DateEntity endDate;

    @SerializedName("startCoordinate")
    public GetCoordinateEntity startCoordinate;

    @SerializedName("endCoordinate")
    public GetCoordinateEntity endCoordinate;

    @SerializedName("route")
    public String route;
}
