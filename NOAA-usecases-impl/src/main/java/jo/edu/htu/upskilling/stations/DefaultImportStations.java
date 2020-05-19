package jo.edu.htu.upskilling.stations;

import jo.edu.htu.upskilling.importExceptions.ImportException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DefaultImportStations implements ImportStations {
    private StationsRepository repository;
    private String line;
    private int updatedRowsCount;
    private int insertedRowsCount;
    private int totalRowsCount;

    public DefaultImportStations(StationsRepository repository) {
        this.repository = repository;
    }

    @Override
    public ImportStationRes importStations(ImportStationsReq request) {
        // Same as gsod import
        Path path = request.getPath();
        List<Station> AllStations = new ArrayList<>();
        List<Station> stationsNotExisted = new ArrayList<>();
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            while ((line = bufferedReader.readLine()) != null) {
                Station station = new Station();
                station.setStationId(line.substring(0, 6));
                station.setWbanNumber(line.substring(7, 12));
                if (line.substring(13, 43).trim().isEmpty()) {
                    station.setStationName(null);
                } else {
                    station.setStationName(line.substring(13, 43));
                }
                if (line.substring(43, 47).trim().isEmpty()) {
                    station.setCountryId(null);
                } else {
                    station.setCountryId(line.substring(43, 47));
                }
                if (line.substring(48, 50).trim().isEmpty()) {
                    station.setStateOfUS(null);
                } else {
                    station.setStateOfUS(line.substring(48, 50));
                }
                if (line.substring(51, 57).trim().isEmpty()) {
                    station.setIcaoId(null);
                } else {
                    station.setIcaoId(line.substring(51, 57));
                }

                if (!(line.substring(57, 64).trim().isEmpty())) {
                    station.setLatitude(Double.parseDouble(line.substring(57, 64)));
                } else {
                    station.setLatitude(null);
                }
                if (!(line.substring(65, 73).trim().isEmpty())) {
                    station.setLongitude(Double.parseDouble(line.substring(65, 73)));
                } else {
                    station.setLongitude(null);
                }
                if (!(line.substring(74, 81).trim().isEmpty())) {
                    station.setAltitude(Double.parseDouble(line.substring(74, 81)));
                } else {
                    station.setAltitude(null);
                }
                station.setBeginPeriod(Date.valueOf(line.substring(82, 86) + "-" + line.substring(86, 88) + "-" + line.substring(88, 90)));
                station.setEndPeriod(Date.valueOf(line.substring(91, 95) + "-" + line.substring(95, 97) + "-" + line.substring(97, 99)));

                AllStations.add(station);
                System.out.println(AllStations.size());

            }

            for (Station station : AllStations) {
                System.out.println(station.getStationName());

                int updated = repository.update(station);
                if (updated == 0) {
                    stationsNotExisted.add(station);

                } else {
                    updatedRowsCount++;
                }
            }

            repository.insert(stationsNotExisted);
//            for (Station station : stationsNotExisted) {
//                System.out.println(station.getStationName() + "station name");
//            }
            insertedRowsCount = stationsNotExisted.size();

        } catch (
                IOException exception) {
            throw new ImportException(exception);
        }

        ImportStationRes result = new ImportStationRes();

        result.setNewRecordsCount(insertedRowsCount);
        result.setUpdatedRecordsCount(updatedRowsCount);
        totalRowsCount = repository.selectTotalRecordsCount();
        result.setTotalNumOfRecords(totalRowsCount);

        return result;
    }
}
