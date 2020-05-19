package jo.edu.htu.upskilling.stations;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import jo.edu.htu.upskilling.gsod.ImportGSODReq;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ImportStationTest {
    public static void main(String[] args) {
        MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/NOAA_processor?serverTimezone=UTC");
        dataSource.setUser("root");
        dataSource.setPassword("root");
        DBStationsRepository repository = new DBStationsRepository(dataSource);
        repository.createTable();
        DefaultImportStations importStations = new DefaultImportStations(repository);
        Path pwd = Paths.get(".", "StationsTest.txt");
        String userHome = System.getProperty("user.home");
        System.out.println(userHome);
        Path tempDirectory = Paths.get(userHome).resolve("stations-temp");
        try {
            Files.createDirectories(tempDirectory);
            Path fileToImport = Files.createTempFile("stationat", ".temp");

        } catch (IOException exception) {
            System.out.println("no success");
        }

        System.out.println("start importing");

        ImportStationsReq stationsReq = new ImportStationsReq(pwd);


        long startTime = System.currentTimeMillis();
        ImportStationRes importStationRes = importStations.importStations(stationsReq);
        System.out.println(importStationRes.getUpdatedRecordsCount()+"updated Rows");
        long endTime = System.currentTimeMillis();
        System.out.println("import took: " + ((endTime - startTime) / 1000) + " seconds");


//        ListStations listStations = new DefaultListStations(repository);
//        List<Station> stationList = new ArrayList<>();
//        ListStationsRes res = new ListStationsRes(stationList);
//        res = listStations.listStations(new ListStationsReq("999999", "99999"));
//        for (int i = 0; i < res.getStations().size(); i++) {
//            System.out.println(res.getStations().get(i).getStationName());
//        }
        System.out.println("done");
    }
}
