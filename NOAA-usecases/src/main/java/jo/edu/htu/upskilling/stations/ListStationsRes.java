package jo.edu.htu.upskilling.stations;

import java.util.ArrayList;
import java.util.List;

public class ListStationsRes {
   private List <Station>stations;

    public List<Station> getStations() {
        return stations;
    }

    public ListStationsRes(List<Station> stations) {
        this.stations = stations;
    }
}
