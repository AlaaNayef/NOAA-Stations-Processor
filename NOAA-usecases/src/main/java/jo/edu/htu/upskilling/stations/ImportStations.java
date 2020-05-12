package jo.edu.htu.upskilling.stations;

import jo.edu.htu.upskilling.gsod.ImportGSODRes;

public interface ImportStations {

    ImportStationRes importStations(ImportStationsReq request);
}
