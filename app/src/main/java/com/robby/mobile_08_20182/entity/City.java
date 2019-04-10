package com.robby.mobile_08_20182.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author Robby
 */
public class City implements Parcelable  {

    public static final String ARG_CITY = "arg_city";
    public static final String PCL_CITY = "pcl_city";

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("coord")
    private CityCoordinate coordinate;

    public City() {}

    private City(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CityCoordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
    }
}
