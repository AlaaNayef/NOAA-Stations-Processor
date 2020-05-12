package jo.edu.htu.upskilling.gsod;

import jo.edu.htu.upskilling.dbexceptions.DBAccessException;
import jo.edu.htu.upskilling.dbexceptions.RecordNotFoundException;
import jo.edu.htu.upskilling.utilities.TotalNumOfRecords;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DBGSODRepository implements GSODRepository {

    private DataSource dataSource;

    private static final String CREATE_TABLE = "create table IF NOT EXISTS GSOD" +
            "(" +
            "station_id varchar (6)," +
            "wban_number varchar (5)," +
            "year_mo_day DATE ," +
            "mean_temperature double," +
            "wind_speed double," +
            "max_temp double," +
            "min_temp double" +
            ")";
    private static final String SELECT_COUNT = "select count(*)from GSOD";
    private static final String INSERT_GSOD = "insert into GSOD " +
            "(station_id,wban_number,year_mo_day,mean_temperature,wind_speed,max_temp,min_temp)" +
            "values (?,?,?,?,?,?,?)";
    private static final String UPDATE_GSOD = "update GSOD set year_mo_day=?,mean_temperature=?,wind_speed=?,max_temp=?,min_temp=?" +
            "where station_id=? && wban_number=? ";
    private static final String QUERY_BY_STATION_ID = "select " +
            "station_id, wban_number,year_mo_day,mean_temperature,wind_speed,max_temp,min_temp" +
            " from GSOD where (station_id = ?) ";
    private static final String QUERY_BY_WBAN_NUMBER = "select " +
            "station_id,wban_number,year_mo_day,mean_temperature,wind_speed,max_temp,min_temp" +
            " from GSOD where wban_number=?";
    private static final String QUERY_BY_DATE = "select " +
            "station_id,wban_number,year_mo_day,mean_temperature,wind_speed,max_temp,min_temp " +
            "from GSOD where year_mo_day between ? and ?";

    public DBGSODRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void createTable() {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException exception) {
            throw new DBAccessException(exception);
        }
    }

    private void prepareGSODToInsert(GSOD gsod, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, gsod.getStation_Id());
        preparedStatement.setString(2, gsod.getWban());
        preparedStatement.setDate(3, Date.valueOf(gsod.getDate()));
        preparedStatement.setDouble(4, gsod.getMeanTemperature());
        preparedStatement.setDouble(5, gsod.getWindSpeed());
        preparedStatement.setDouble(6, gsod.getMaxTemp());
        preparedStatement.setDouble(7, gsod.getMinTemp());
    }

    @Override
    public void insert(LinkedList<GSOD> AllGSOD) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_GSOD)) {
                int i = 0;
                for (GSOD gsod : AllGSOD) {
                    prepareGSODToInsert(gsod, preparedStatement);
                    preparedStatement.addBatch();

                    i++;
                    if (i % 200 == 0 || i == AllGSOD.size()) {
                        int[] ints = preparedStatement.executeBatch();
                        connection.commit();
                    }// (1000 , 424) -- (500 , 476) --( 2794,420) (523 without batching)(431,hashmap)(423, linked list)
                    //update took 33 seconds for the hole file :)
                }
            } catch (SQLException exception) {
                connection.rollback();
                throw new DBAccessException(exception);
            }

        } catch (
                SQLException exception) {
            throw new DBAccessException(exception);
        }

    }

    @Override
    public void insert(GSOD gsod) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_GSOD)) {
                prepareGSODToInsert(gsod, preparedStatement);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException exception) {
            throw new DBAccessException(exception);
        }
    }

    @Override
    public int update(GSOD gsod) {
        int updatedRows;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_GSOD)) {
                preparedStatement.setDate(1, Date.valueOf(gsod.getDate()));
                preparedStatement.setDouble(2, gsod.getMeanTemperature());
                preparedStatement.setDouble(3, gsod.getWindSpeed());
                preparedStatement.setDouble(4, gsod.getMaxTemp());
                preparedStatement.setDouble(5, gsod.getMinTemp());
                preparedStatement.setString(6, gsod.getStation_Id());
                preparedStatement.setString(7, gsod.getWban());
                updatedRows = preparedStatement.executeUpdate();
            }
        } catch (SQLException exception) {
            throw new DBAccessException(exception);
        }
        return updatedRows;
    }

    @Override
    public int selectTotalRecordCount() {

        return TotalNumOfRecords.rowsCount(dataSource, SELECT_COUNT);
    }

    @Override
    public GSOD findGSODByStationId(String stationId) {
        GSOD gsod = new GSOD();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_BY_STATION_ID)) {
                preparedStatement.setString(1, stationId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        gsod.setStation_Id(resultSet.getString("station_id"));
                        gsod.setWban(resultSet.getString("wban_number"));
                        gsod.setMeanTemperature(resultSet.getDouble("mean_temperature"));
                        gsod.setWindSpeed(resultSet.getDouble("wind_speed"));
                        gsod.setMaxTemp(resultSet.getDouble("max_temp"));
                        gsod.setMinTemp(resultSet.getDouble("min_temp"));
                        String year_mo_day = resultSet.getString("year_mo_day");
                        gsod.setDate(LocalDate.parse(year_mo_day));
                    }
                }
            }
        } catch (SQLException exception) {
            throw new DBAccessException(exception);
        }
        return gsod;
    }

    @Override
    public GSOD findGSODByWbanNumber(String WbanNumber) {
        GSOD gsod = new GSOD();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_BY_WBAN_NUMBER)) {
                preparedStatement.setString(1, WbanNumber);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        gsod.setStation_Id(resultSet.getString("station_id"));
                        gsod.setWban(resultSet.getString("wban_number"));
                        gsod.setMeanTemperature(resultSet.getDouble("mean_temperature"));
                        gsod.setWindSpeed(resultSet.getDouble("wind_speed"));
                        gsod.setMaxTemp(resultSet.getDouble("max_temp"));
                        gsod.setMinTemp(resultSet.getDouble("min_temp"));
                        String year_mo_day = resultSet.getString("year_mo_day");
                        gsod.setDate(LocalDate.parse(year_mo_day));
                    }
                }
            }
        } catch (SQLException exception) {
            throw new DBAccessException(exception);
        }
        return gsod;
    }

    @Override
    public List<GSOD> findGSODBYDate(LocalDate from, LocalDate to) {
        List<GSOD> GSODs = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_BY_DATE)) {
                Date dateFrom = Date.valueOf(from);
                Date dateTo = Date.valueOf(to);
                preparedStatement.setDate(1, dateFrom);
                preparedStatement.setDate(2, dateTo);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        GSOD gsod = new GSOD();

                        gsod.setStation_Id(resultSet.getString("station_id"));
                        gsod.setWban(resultSet.getString("wban_number"));
                        gsod.setMeanTemperature(resultSet.getDouble("mean_temperature"));
                        gsod.setWindSpeed(resultSet.getDouble("wind_speed"));
                        gsod.setMaxTemp(resultSet.getDouble("max_temp"));
                        gsod.setMinTemp(resultSet.getDouble("min_temp"));
                        String year_mo_day = resultSet.getString("year_mo_day");
                        gsod.setDate(LocalDate.parse(year_mo_day));
                        GSODs.add(gsod);

                    }
                }
            }
        } catch (SQLException exception) {
            throw new DBAccessException(exception);
        }
        return GSODs;
    }


}
