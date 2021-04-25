package com.example.airmonitor;

public class HelperClass {

    String dateTime,entry_id,air_quality,pressure,humidity,dewPoint,temperature;

    public HelperClass() {
    }

    public HelperClass(String dateTime, String entry_id, String air_quality, String pressure, String humidity, String dewPoint, String temperature) {
        this.dateTime = dateTime;
        this.entry_id = entry_id;
        this.air_quality = air_quality;
        this.pressure = pressure;
        this.humidity = humidity;
        this.dewPoint = dewPoint;
        this.temperature = temperature;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getEntry_id() {
        return entry_id;
    }

    public void setEntry_id(String entry_id) {
        this.entry_id = entry_id;
    }

    public String getAir_quality() {
        return air_quality;
    }

    public void setAir_quality(String air_quality) {
        this.air_quality = air_quality;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(String dewPoint) {
        this.dewPoint = dewPoint;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
