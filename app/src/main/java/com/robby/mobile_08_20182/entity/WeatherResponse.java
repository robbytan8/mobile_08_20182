package com.robby.mobile_08_20182.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author Robby
 */
public class WeatherResponse implements Parcelable {

    public static final String WEATHER_DET = "wea_det";

    @SerializedName("name")
    private String cityName;
    @SerializedName("coord")
    private CityCoordinate coordinate;
    @SerializedName("main")
    private WeatherMain main;

    public WeatherResponse() {}

    private WeatherResponse(Parcel in) {
        cityName = in.readString();
    }

    public static final Creator<WeatherResponse> CREATOR = new Creator<WeatherResponse>() {
        @Override
        public WeatherResponse createFromParcel(Parcel in) {
            return new WeatherResponse(in);
        }

        @Override
        public WeatherResponse[] newArray(int size) {
            return new WeatherResponse[size];
        }
    };

    public String getCityName() {
        return cityName;
    }

    public CityCoordinate getCoordinate() {
        return coordinate;
    }

    public WeatherMain getMain() {
        return main;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cityName);
    }
}
