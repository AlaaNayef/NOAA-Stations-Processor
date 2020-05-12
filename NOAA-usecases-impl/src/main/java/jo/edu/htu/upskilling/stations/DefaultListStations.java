package jo.edu.htu.upskilling.stations;

import java.util.ArrayList;
import java.util.List;

public class DefaultListStations implements ListStations {
    private DBStationsRepository repository;
    List<Station> allStations = new ArrayList<>();

    public DefaultListStations(DBStationsRepository repository) {
        this.repository = repository;
    }

    @Override
    public ListStationsRes listStations(ListStationsReq request) {
        if(request.getStation_id()!=null) {
            allStations.addAll(repository.findStationsByStationId(request.getStation_id()));
        }
        if(request.getWban_number()!= null){
            allStations.addAll(repository.findStationsByWbanNumber(request.getWban_number()));
        }
        if(request.getLatitude()!=null && request.getLongitude()!=null){
            allStations.addAll(repository.findStationsByGeoLocation(request.getLatitude(),request.getLongitude()));
        }
        ListStationsRes listStationsRes=new ListStationsRes(allStations);
        return listStationsRes;
    }
}
