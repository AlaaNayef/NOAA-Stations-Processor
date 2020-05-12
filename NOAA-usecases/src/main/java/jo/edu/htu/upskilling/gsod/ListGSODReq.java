package jo.edu.htu.upskilling.gsod;

import java.time.LocalDate;

public class ListGSODReq {

    private String stationId;
    private String wbanNum;
    private LocalDate fromDate;
    private LocalDate toDate;

    public ListGSODReq(String stationId, String wbanNum, LocalDate fromDate, LocalDate toDate) {
        this.stationId = stationId;
        this.wbanNum = wbanNum;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getStationId() {
        return stationId;
    }

    public String getWbanNum() {
        return wbanNum;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }
}
