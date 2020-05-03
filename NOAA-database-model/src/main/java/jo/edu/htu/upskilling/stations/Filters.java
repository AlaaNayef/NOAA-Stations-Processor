package jo.edu.htu.upskilling.stations;

public class Filters {

private String station_id;
private String wban_number;
private Double latitude;
private Double longitude;

    public Filters(String station_id, String wban_number) {
        this.station_id = station_id;
        this.wban_number = wban_number;
    }

    public Filters(String station_id, String wban_number, Double latitude, Double longitude) {
        this.station_id = station_id;
        this.wban_number = wban_number;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public void setWban_number(String wban_number) {
        this.wban_number = wban_number;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getStation_id() {
        return station_id;
    }

    public String getWban_number() {
        return wban_number;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
