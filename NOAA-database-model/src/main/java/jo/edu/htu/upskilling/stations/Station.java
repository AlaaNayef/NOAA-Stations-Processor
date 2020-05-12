package jo.edu.htu.upskilling.stations;

import java.sql.Date;

public class Station {
    private String stationId;
    private String wbanNumber;
    private String stationName;
    private String countryId;
    private String stateOfUS;
    private String ICAO_Id;
    private Double longitude;
    private Double latitude;
    private Double altitude;
    private Date beginPeriod;
    private Date endPeriod;


    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public void setWbanNumber(String wbanNumber) {
        this.wbanNumber = wbanNumber;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public void setStateOfUS(String stateOfUS) {
        this.stateOfUS = stateOfUS;
    }

    public void setICAO_Id(String ICAO_Id) {
        this.ICAO_Id = ICAO_Id;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public void setBeginPeriod(Date beginPeriod) {
        this.beginPeriod = beginPeriod;
    }

    public void setEndPeriod(Date endPeriod) {
        this.endPeriod = endPeriod;
    }

    public String getStationId() {
        return stationId;
    }

    public String getWbanNumber() {
        return wbanNumber;
    }

    public String getStationName() {
        return stationName;
    }

    public String getCountryId() {
        return countryId;
    }

    public String getStateOfUS() {
        return stateOfUS;
    }

    public String getICAO_Id() {
        return ICAO_Id;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public Date getBeginPeriod() {
        return beginPeriod;
    }

    public Date getEndPeriod() {
        return endPeriod;
    }
}
