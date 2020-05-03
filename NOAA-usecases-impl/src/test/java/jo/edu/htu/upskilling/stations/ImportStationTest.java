package jo.edu.htu.upskilling.stations;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ImportStationTest {
    public static void main(String[] args) {
        MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/NOAA_processor?serverTimezone=UTC");
        dataSource.setUser("root");
        dataSource.setPassword("root");
        DBStationsRepository repository=new DBStationsRepository(dataSource);
        //repository.createTable();
        DefaultImportStations importStations=new DefaultImportStations(repository);
        Path pwd = Paths.get(".", "StationsTest.txt");
        ImportStationsReq stationsReq=new ImportStationsReq(pwd);

        importStations.importStations(stationsReq);
        System.out.println("done");
    }
}
