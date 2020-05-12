package jo.edu.htu.upskilling.stations;

import jo.edu.htu.upskilling.dbexceptions.DBAccessException;
import jo.edu.htu.upskilling.dbexceptions.RecordNotFoundException;
import jo.edu.htu.upskilling.utilities.TotalNumOfRecords;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBStationsRepository implements StationsRepository {
    DataSource dataSource;
    private static final String createStation = "create table IF NOT EXISTS stations " +
            "(" +
            "station_id varchar (6)," +
            "wban_number varchar (5)," +
            "station_name varchar(30)," +
            "country_id varchar(3)," +
            "state_of_US varchar(2)," +
            "icao_id varchar(5)," +
            "latitude decimal (7,4)," +
            "longitude decimal (6,3)," +
            "altitude decimal (6,1)," +
            "begin_period DATE ," +
            "end_period DATE ," +
            "primary key (station_id,wban_number)) ";

    private static final String insertStation = "insert into stations " +
            "(station_id,wban_number,station_name," +
            "country_id,state_of_US,icao_id,longitude," +
            "latitude,altitude,begin_period,end_period)" +
            " values (?,?,?,?,?,?,?,?,?,?,?)";
    private static final String updateStation = "update stations set" +
            " station_name=?,country_id=?," +
            "state_of_US =? , icao_id =?,latitude=?,longitude=?," +
            "altitude=?, begin_period=?,end_period=?" +
            " where (station_id=? && wban_number=? )";
    private static final String SELECT_COUNT = "select count(*)from stations";
    private static final String QUERY_BY_STATION_ID = "select station_id,wban_number,station_name," +
            "country_id,state_of_US,ICAO_Id,latitude,longitude,altitude,begin_period,end_period from stations " +
            "where station_id like CONCAT(?,'%') ";
    private static final String QUERY_BY_WBAN_NUMBER = "select station_id,wban_number,station_name," +
            "country_id,state_of_US,ICAO_Id,latitude,longitude,altitude,begin_period,end_period from stations " +
            "where wban_number like CONCAT(?,'%') ";
    private static final String QUERY_BY_GEO_LOCATION = "select station_id,wban_number,station_name," +
            "country_id,state_of_US,ICAO_Id,latitude,longitude,altitude,begin_period,end_period from stations " +
            "where getDistance(latitude,longitude,?,?)<=0.5 ";

    public DBStationsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void createTable() {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(createStation)) {
                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            throw new DBAccessException(exception);
        }
    }

    @Override

    public void insert(Station station) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertStation)) {
                prepareStatementInsert(station, preparedStatement);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException exception) {
            throw new DBAccessException(exception);
        }

    }

    @Override
    public void insert(List<Station> stations) {
        int i = 0;
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertStation)) {
                for (Station station : stations) {
                    prepareStatementInsert(station, preparedStatement);
                    preparedStatement.addBatch();
                    i++;
                    if (((i % 1000) == 0) || (i == stations.size())) {
                        preparedStatement.executeBatch();
                        connection.commit();
                    }
                }
            } catch (SQLException exception) {

                connection.rollback();
                throw new DBAccessException(exception);
            }
        } catch (SQLException exception) {

            throw new DBAccessException(exception);
        }
    }

    private void prepareStatementInsert(Station station, PreparedStatement preparedStatement) throws SQLException {
        System.out.println(station.getStationName() + "Station Name is");

        preparedStatement.setString(1, station.getStationId());
        preparedStatement.setString(2, station.getWbanNumber());
        preparedStatement.setString(3, station.getStationName());
        preparedStatement.setString(4, station.getCountryId());
        preparedStatement.setString(5, station.getStateOfUS());
        preparedStatement.setString(6, station.getICAO_Id());
        if (station.getLatitude() == (null)) {
            preparedStatement.setNull(7, Types.DOUBLE);
            preparedStatement.setNull(8, Types.DOUBLE);
            preparedStatement.setNull(9, Types.DOUBLE);
        } else {
            preparedStatement.setObject(7, station.getLatitude());
            preparedStatement.setObject(8, station.getLongitude());
            preparedStatement.setObject(9, station.getAltitude());
        }
        preparedStatement.setDate(10, station.getBeginPeriod());
        preparedStatement.setDate(11, station.getEndPeriod());
    }

    @Override
    public int update(Station station) {
        int updatedRows;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateStation)) {
                prepareUpdateStatement(station, preparedStatement);
                updatedRows = preparedStatement.executeUpdate();
            }
        } catch (SQLException exception) {
            throw new DBAccessException(exception);
        }
        return updatedRows;
    }

    private void prepareUpdateStatement(Station station, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, station.getStationName());
        preparedStatement.setString(2, station.getCountryId());
        preparedStatement.setString(3, station.getStateOfUS());
        preparedStatement.setString(4, station.getICAO_Id());
        if (station.getLatitude() == (null)) {
            preparedStatement.setNull(5, Types.DOUBLE);
        } else {
            preparedStatement.setDouble(5, station.getLatitude());
        }
        if (station.getLongitude() == null) {
            preparedStatement.setNull(6, Types.DOUBLE);
        } else {
            preparedStatement.setDouble(6, station.getLongitude());
        }
        if (station.getAltitude() == null) {
            preparedStatement.setNull(7, Types.DOUBLE);
        } else {
            preparedStatement.setDouble(7, station.getAltitude());
        }
        preparedStatement.setDate(8, station.getBeginPeriod());
        preparedStatement.setDate(9, station.getEndPeriod());
        preparedStatement.setString(10, station.getStationId());
        preparedStatement.setString(11, station.getWbanNumber());

    }

    @Override
    public int selectTotalRecordsCount() {
        return TotalNumOfRecords.rowsCount(dataSource, SELECT_COUNT);
    }


    @Override
    public List<Station> findStationsByStationId(String stationId) {
        List<Station> filteredStations = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_BY_STATION_ID)) {
                stationId = stationId.substring(0, 3);
                preparedStatement.setString(1, stationId);

                prepareStations(filteredStations, preparedStatement);
            }
        } catch (SQLException exception) {
            throw new DBAccessException(exception);
        }
        return filteredStations;
    }

    private void prepareStations(List<Station> filteredStations, PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Station station = new Station();
                station.setStationId(resultSet.getString("station_id"));
                station.setWbanNumber(resultSet.getString("wban_number"));
                station.setStationName(resultSet.getString("station_name"));
                station.setCountryId(resultSet.getString("country_id"));
                station.setStateOfUS(resultSet.getString("state_of_US"));
                station.setICAO_Id(resultSet.getString("icao_id"));
                station.setLatitude(resultSet.getDouble("latitude"));
                station.setLongitude(resultSet.getDouble("longitude"));
                station.setAltitude(resultSet.getDouble("altitude"));
                station.setBeginPeriod(resultSet.getDate("begin_period"));
                station.setEndPeriod(resultSet.getDate("end_period"));
                filteredStations.add(station);
            }
        }
    }

    @Override
    public List<Station> findStationsByWbanNumber(String WbanNumber) {
        List<Station> filteredStations = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_BY_WBAN_NUMBER)) {
                WbanNumber = WbanNumber.substring(0, 2);
                preparedStatement.setString(1, WbanNumber);
                prepareStations(filteredStations, preparedStatement);
            }
        } catch (SQLException exception) {
            throw new DBAccessException(exception);
        }
        return filteredStations;
    }


    @Override
    public List<Station> findStationsByGeoLocation(Double latitude, Double longitude) {
        List<Station> filteredStations = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_BY_GEO_LOCATION)) {
                preparedStatement.setDouble(1, latitude);
                preparedStatement.setDouble(2, longitude);
                prepareStations(filteredStations, preparedStatement);
            }
        } catch (SQLException exception) {
            throw new DBAccessException(exception);
        }
        return filteredStations;

    }

}
