package jo.edu.htu.upskilling.gsod;

import jo.edu.htu.upskilling.stations.Station;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public interface GSODRepository {

    void createTable();

    void insert(GSOD gsod);

    void insert(LinkedList<GSOD> AllGSOD);

    int update(GSOD gsod);

    int selectTotalRecordCount();

    GSOD findGSODByStationId(String stationId);

    GSOD findGSODByWbanNumber(String WbanNumber);

    List<GSOD> findGSODBYDate(LocalDate from, LocalDate to);
}
