package jo.edu.htu.upskilling.stations;

public class ImportStationRes {
    private int totalNumOfRecords;
    private int newRecordsCount;
    private int updatedRecordsCount;
    private int totNumberOfStationsInDB;

    public void setTotalNumOfRecords(int totalNumOfRecords) {
        this.totalNumOfRecords = totalNumOfRecords;
    }

    public void setNewRecordsCount(int newRecordsCount) {
        this.newRecordsCount = newRecordsCount;
    }

    public void setUpdatedRecordsCount(int updatedRecordsCount) {
        this.updatedRecordsCount = updatedRecordsCount;
    }

    public void setTotNumberOfStationsInDB(int totNumberOfStationsInDB) {
        this.totNumberOfStationsInDB = totNumberOfStationsInDB;
    }

    public int getTotalNumOfRecords() {
        return totalNumOfRecords;
    }

    public int getNewRecordsCount() {
        return newRecordsCount;
    }

    public int getUpdatedRecordsCount() {
        return updatedRecordsCount;
    }

    public int getTotNumberOfStationsInDB() {
        return totNumberOfStationsInDB;
    }
}
