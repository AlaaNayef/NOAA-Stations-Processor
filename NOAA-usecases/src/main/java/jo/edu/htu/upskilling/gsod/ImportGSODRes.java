package jo.edu.htu.upskilling.gsod;

public class ImportGSODRes {
    private int totalNumOfRecords;
    private int newRecordsCount;
    private int updatedRecordsCount;

    public void setTotalNumOfRecords(int totalNumOfRecords) {
        this.totalNumOfRecords = totalNumOfRecords;
    }

    public void setNewRecordsCount(int newRecordsCount) {
        this.newRecordsCount = newRecordsCount;
    }

    public void setUpdatedRecordsCount(int updatedRecordsCount) {
        this.updatedRecordsCount = updatedRecordsCount;
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
}
