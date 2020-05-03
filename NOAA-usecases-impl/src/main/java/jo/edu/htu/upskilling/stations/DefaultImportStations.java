package jo.edu.htu.upskilling.stations;

import com.google.protobuf.Empty;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.sql.SQLException;

public class DefaultImportStations implements ImportStations {
    private DBStationsRepository repository;
    private String line;
    private int updatedRowsCount;
    private int insertedRowsCount;
    private int totalRowsCount;


    public DefaultImportStations(DBStationsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void importStations(ImportStationsReq request) {
        Path path = request.getPath();
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {

            while ((line = bufferedReader.readLine()) != null) {

                Station station = new Station();
                station.setStationId(line.substring(0, 6));
                station.setWbanNumber(line.substring(7, 12));
                if (line.substring(13, 42).trim().isEmpty()) {
                    station.setStationName(null);
                } else {
                    station.setStationName(line.substring(13, 42));
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
                if (line.substring(51, 56).trim().isEmpty()) {
                    station.setICAO_Id(null);
                } else {
                    station.setICAO_Id(line.substring(51, 56));
                }

                if (!(line.substring(57, 64).trim().isEmpty())) {
                    station.setLatitude(Double.parseDouble(line.substring(57, 64)));
                    station.setLongitude(Double.parseDouble(line.substring(65, 73)));
                    station.setAltitude(Double.parseDouble(line.substring(74, 81)));
                } else {
                    station.setLatitude(null);
                    station.setLongitude(null);
                    station.setAltitude(null);
                }
                //station.setBeginPeriod(Date.valueOf("1999-06-02"));
                //station.setEndPeriod(Date.valueOf("1996-06-02"));
//                System.out.println("Station id" + line.substring(0, 6));
//                System.out.println("Wban" + line.substring(7, 12));
//                System.out.println("Station_name" + line.substring(13, 42));
//                System.out.println("country_id" + line.substring(43, 47));
//                System.out.println("StateOfUS" + line.substring(48, 50));
//                System.out.println("ICAO_Id" + line.substring(51, 56));
//                System.out.println("Latitude" + line.substring(57, 64));
//                System.out.println("Longitude" + line.substring(65, 73));
//                System.out.println("Altitude" + line.substring(74, 81));
//                System.out.println("beginDate" + line.substring(82, 90));
//                System.out.println(line.substring(82, 86) + "-" + line.substring(86, 88) + "-" + line.substring(88, 90));
                station.setBeginPeriod(Date.valueOf(line.substring(82, 86) + "-" + line.substring(86, 88) + "-" + line.substring(88, 90)));
                station.setEndPeriod(Date.valueOf(line.substring(91, 95) + "-" + line.substring(95, 97) + "-" + line.substring(97, 99)));

                try {
                    repository.update(station);
                    updatedRowsCount++;
                } catch (RecordNotFoundException exception) {
                    repository.insert(station);
                    insertedRowsCount++;
                }


            }

        } catch (IOException exception) {
            System.out.println(exception);
        }
        ImportStationRes result =new ImportStationRes();


        result.setNewRecordsCount(insertedRowsCount);
        result.setUpdatedRecordsCount(updatedRowsCount);
        int totalNumOfRecords = repository.selectTotalRecordsCount();
        result.setTotalNumOfRecords(totalNumOfRecords);
        System.out.println("totalNumOfRecords"+totalNumOfRecords);
        System.out.println("insertedRowsCount"+insertedRowsCount);
        System.out.println("updatedRowsCount"+updatedRowsCount);
    }
}
