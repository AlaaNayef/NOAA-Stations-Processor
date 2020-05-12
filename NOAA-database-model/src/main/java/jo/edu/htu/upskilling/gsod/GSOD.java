package jo.edu.htu.upskilling.gsod;

import java.time.LocalDate;

public class GSOD {

    private String station_Id;
    private String wban;
    private LocalDate date;
    private double meanTemperature;
    private int meanTempCount;
    private double maxTemp;
    private double minTemp;
    private double windSpeed;
    private int windSpeedCount;

    public int getWindSpeedCount() {
        return windSpeedCount;
    }

    public void setMeanTempCount(int meanTempCount) {
        this.meanTempCount = meanTempCount;
    }

    public void setWindSpeedCount(int windSpeedCount) {
        this.windSpeedCount = windSpeedCount;
    }

    public void setStation_Id(String station_Id) {
        this.station_Id = station_Id;
    }

    public void setWban(String wban) {
        this.wban = wban;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setMeanTemperature(double meanTemperature) {
        this.meanTemperature = meanTemperature;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getStation_Id() {
        return station_Id;
    }

    public String getWban() {
        return wban;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getMeanTemperature() {
        return meanTemperature;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public int getMeanTempCount() {
        return meanTempCount;
    }
}
