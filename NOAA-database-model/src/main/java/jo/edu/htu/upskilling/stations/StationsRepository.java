package jo.edu.htu.upskilling.stations;

public interface StationsRepository {

    void createTable();

    void insert(Station station);

    void update(Station station);

    int selectTotalRecordsCount();

    void selectWithFilters(Filters filters);

}
