package jo.edu.htu.upskilling.stations;

import java.util.List;

public interface StationsRepository {

    void createTable();

    void insert(Station station);
    void insert(List<Station> stations);

    int update(Station station);

    int selectTotalRecordsCount();

    List<Station> findStationsByStationId(String stationId);

    List<Station> findStationsByWbanNumber(String WbanNumber);

    List<Station> findStationsByGeoLocation(Double latitude, Double longitude);

}
