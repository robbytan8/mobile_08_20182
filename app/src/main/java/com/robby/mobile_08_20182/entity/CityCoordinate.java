package com.robby.mobile_08_20182.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author Robby
 */
public class CityCoordinate {

    @SerializedName("lon")
    private double longitude;
    @SerializedName("lat")
    private double latitude;

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
