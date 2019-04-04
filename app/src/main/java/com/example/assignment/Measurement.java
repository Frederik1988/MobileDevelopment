package com.example.assignment;

import java.io.Serializable;

public class Measurement implements Serializable {
    private int deviceId;
    private double pm25;
    private double pm10;
    private double co2;
    private double o3;
    private double pressure;
    private double temperature;
    private double humidity;
    private long utc;
    private double latitude;
    private double longitude;
    private double noise;
    private String userId;

    public Measurement(int deviceId, double pm25, double pm10, double co2, double o3, double pressure, double temperature, double humidity, long utc, double latitude, double longitude, double noise, String userId) {
        this.deviceId = deviceId;
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.co2 = co2;
        this.o3 = o3;
        this.pressure = pressure;
        this.temperature = temperature;
        this.humidity = humidity;
        this.utc = utc;
        this.latitude = latitude;
        this.longitude = longitude;
        this.noise = noise;
        this.userId = userId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public double getPm25() {
        return pm25;
    }

    public void setPm25(double pm25) {
        this.pm25 = pm25;
    }

    public double getPm10() {
        return pm10;
    }

    public void setPm10(double pm10) {
        this.pm10 = pm10;
    }

    public double getCo2() {
        return co2;
    }

    public void setCo2(double co2) {
        this.co2 = co2;
    }

    public double getO3() {
        return o3;
    }

    public void setO3(double o3) {
        this.o3 = o3;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public long getUtc() {
        return utc;
    }

    public void setUtc(long utc) {
        this.utc = utc;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getNoise() {
        return noise;
    }

    public void setNoise(double noise) {
        this.noise = noise;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
