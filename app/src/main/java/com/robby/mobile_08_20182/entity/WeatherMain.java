package com.robby.mobile_08_20182.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author Robby
 */
public class WeatherMain {

    @SerializedName("temp")
    private double temperature;
    @SerializedName("pressure")
    private double pressure;
    @SerializedName("humidity")
    private double humidity;
    @SerializedName("temp_min")
    private double minTemp;
    @SerializedName("temp_max")
    private double maxTemp;

    public double getTemperature() {
        return temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }
}
