package jo.edu.htu.upskilling.gsod;

import jo.edu.htu.upskilling.dbexceptions.RecordNotFoundException;
import jo.edu.htu.upskilling.importExceptions.ImportException;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class DefaultImportGSOD implements ImportGSOD {

    private GSODRepository repository;

    // TODO those should be a local variables
    private int updatedRows;
    private int newRowsCount;

    public DefaultImportGSOD(GSODRepository repository) {
        this.repository = repository;
    }

    @Override
    public ImportGSODRes importGSOD(ImportGSODReq request) {
        Path path = request.getPath();

        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            LinkedList<GSOD> GSODList = readFile(bufferedReader);
            LinkedList<GSOD> GSODNotExisted = new LinkedList<>();
            updateIfExists(GSODNotExisted, GSODList);
            insertNewRecords(GSODNotExisted);
        } catch (IOException exception) {
            throw new ImportException(exception);
        }

        return returnResult();
    }

    private ImportGSODRes returnResult() {
        ImportGSODRes importGSODRes = new ImportGSODRes();
        importGSODRes.setTotalNumOfRecords(repository.selectTotalRecordCount());
        importGSODRes.setNewRecordsCount(newRowsCount);
        importGSODRes.setUpdatedRecordsCount(updatedRows);
        return importGSODRes;
    }

    private void insertNewRecords(LinkedList<GSOD> GSODNotExisted) {
        repository.insert(GSODNotExisted);
        newRowsCount = GSODNotExisted.size();
    }

    private void updateIfExists(LinkedList<GSOD> GSODNotExisted, LinkedList<GSOD> GSODList) {
        for (GSOD gsod : GSODList) {
            int updated = repository.update(gsod);
            if (updated == 0) {
                GSODNotExisted.add(gsod);
            } else {
                updatedRows++;
            }
        }
    }

    private LinkedList<GSOD> readFile(BufferedReader bufferedReader) throws IOException {
        String line;
        LinkedList<GSOD> GSODList = new LinkedList<>();
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line.substring(0, 6));
            if (!(line.substring(0, 6).equals("STN---"))) {
                GSOD gsod = new GSOD();
                gsod.setStation_Id(line.substring(0, 6));
                gsod.setWban(line.substring(7, 12));
                gsod.setDate(LocalDate.parse(line.substring(14, 18) + "-" + line.substring(18, 20) + "-" + line.substring(20, 22)));
                gsod.setMeanTemperature(Double.parseDouble(line.substring(24, 30)));
                gsod.setWindSpeed(Double.parseDouble(line.substring(78, 83)));
                gsod.setMaxTemp(Double.parseDouble(line.substring(102, 108)));
                gsod.setMinTemp(Double.parseDouble(line.substring(110, 116)));
                GSODList.add(gsod);
            }
        }
        return GSODList;
    }
}
