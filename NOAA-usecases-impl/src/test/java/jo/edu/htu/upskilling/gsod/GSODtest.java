package jo.edu.htu.upskilling.gsod;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.sun.xml.internal.ws.api.message.Packet;
import jo.edu.htu.upskilling.gsod.*;

import javax.activation.DataSource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class GSODtest {

    public static void main(String[] args) {
        MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/NOAA_processor?serverTimezone=UTC");
        dataSource.setUser("root");
        dataSource.setPassword("root");

        DBGSODRepository repository = new DBGSODRepository(dataSource);
        repository.createTable();
        ImportGSOD importGSOD = new DefaultImportGSOD(repository);
        Path filePath = Paths.get(".", "GSOD.txt");
        long startTime = System.currentTimeMillis();
        importGSOD.importGSOD(new ImportGSODReq(filePath));
        System.out.println(repository.selectTotalRecordCount());
        long endTime = System.currentTimeMillis();
        System.out.println("import took: " + ((endTime - startTime) / 1000) + " seconds");
        List<GSOD> gsodbyDate = repository.findGSODBYDate(LocalDate.of(2015, 8, 29), LocalDate.of(2020, 4, 22));
        System.out.println(gsodbyDate.size() + "size of the result");
        //700631 25715  20170901    48.9 15    44.0 15  9999.9  0  1004.7 15    9.5 15    1.9 15    7.0  999.9    51.8*   46.4*  0.00I 999.9  000000
        GSOD gsodByStationId = repository.findGSODByStationId("700631");
        GSOD gsodByWbanNumber = repository.findGSODByWbanNumber("25715");
        System.out.println(gsodByWbanNumber.getStation_Id() + "Same object" + gsodByStationId.getWban());
       DefaultListGSOD listGSOD5 = new DefaultListGSOD(repository);
        ListGSODReq request = new ListGSODReq(null, null, null,null);
        ListGSODRes listGSODRes = listGSOD5.listGSOD(request);
        System.out.println(listGSODRes.getGSODs().size()+"siiiizeees");
    }
}
