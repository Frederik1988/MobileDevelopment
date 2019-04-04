package com.example.assignment;

public class Wristband {
    private int deviceId;
    private double pm25;
    private double pm10;
    private double co2;
    private double o3;
    private double pressure;
    private double temperature;
    private double humidity;

    public Wristband(int deviceId, double pm25, double pm10, double co2, double o3, double pressure, double temperature, double humidity) {
        this.deviceId = deviceId;
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.co2 = co2;
        this.o3 = o3;
        this.pressure = pressure;
        this.temperature = temperature;
        this.humidity = humidity;
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

    @Override
    public String toString() {
        return
                "DeviceId = " + deviceId + "\n" +
                "PM25 = " + pm25 + "\n" +
                "PM10 = " + pm10 + "\n" +
                "CO2 = " + co2 + "\n" +
                "O3 = " + o3 + "\n" +
                "Pressure = " + pressure + "\n" +
                "Temperature = " + temperature + "\n" +
                "Humidity = " + humidity;
    }
}
