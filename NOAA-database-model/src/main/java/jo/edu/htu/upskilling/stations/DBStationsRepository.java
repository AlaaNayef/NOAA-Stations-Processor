package jo.edu.htu.upskilling.stations;

import javax.sql.DataSource;
import java.sql.*;

public class DBStationsRepository implements StationsRepository {
    DataSource dataSource;
    private static final String createStation = "create table stations " +
            "(" +
            "station_id varchar (6)," +
            "wban_number varchar (5)," +
            "station_name varchar(28)," +
            "country_id varchar(3)," +
            "state_of_US varchar(2)," +
            "icao_id varchar(4)," +
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
    private static final String selectCount = "select count(*)from stations";
    private static String selectFilters = "select station_id,wban_number,station_name,state_of_US,latitude,longitude " +
            "from stations" +
            " where station_id like CONCAT(?,'%') or (wban_number like CONCAT(?,'%')) or (getDistance(latitude,longitude,?,?)<=0.5)";
//
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
    public void update(Station station) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateStation)) {
                prepareUpdateStatement(station, preparedStatement);
            }
        } catch (SQLException exception) {
            throw new DBAccessException(exception);
        }
    }

    private void prepareUpdateStatement(Station station, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, station.getStationName());
        preparedStatement.setString(2, station.getCountryId());
        preparedStatement.setString(3, station.getStateOfUS());
        preparedStatement.setString(4, station.getICAO_Id());
        if (station.getLatitude() == (null)) {
            preparedStatement.setNull(5, Types.DOUBLE);
            preparedStatement.setNull(6, Types.DOUBLE);
            preparedStatement.setNull(7, Types.DOUBLE);
        } else {
            preparedStatement.setDouble(5, station.getLatitude());
            preparedStatement.setDouble(6, station.getLongitude());
            preparedStatement.setDouble(7, station.getAltitude());
        }
        preparedStatement.setDate(8, station.getBeginPeriod());
        preparedStatement.setDate(9, station.getEndPeriod());
        preparedStatement.setString(10, station.getStationId());
        preparedStatement.setString(11, station.getWbanNumber());
        int updatedRows = preparedStatement.executeUpdate();
        System.out.println("updated rows" + updatedRows);

        if (updatedRows == 0) {
            throw new RecordNotFoundException("No rows where updated");
        }
    }

    @Override
    public int selectTotalRecordsCount() {
        int updatedRows;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectCount)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    resultSet.first();
                    updatedRows = resultSet.getInt("count(*)");

                }
            }
        } catch (SQLException exception) {
            throw new DBAccessException(exception);
        }

        return updatedRows;
    }

    @Override
    public void selectWithFilters(Filters filters) {
        String varWhere = "";

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectFilters)) {
                //preparedStatement.setInt(1, filters.getStation_id());
                //preparedStatement.setInt(2, filters.getWban_number());
                String i = filters.getStation_id().substring(0,3);
                String i1 = filters.getWban_number().substring(0,2);

                System.out.println("i is "+i );
                preparedStatement.setString(1, i);
                preparedStatement.setString(2, i1);
               preparedStatement.setDouble(3, filters.getLatitude());
               preparedStatement.setDouble(4, filters.getLongitude());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {

                    while (resultSet.next()) {
                        System.out.print(resultSet.getString("station_id") + "  ");
                        System.out.print(resultSet.getString("wban_number") + "  ");
                        System.out.print(resultSet.getString("station_name"));
                        System.out.print(resultSet.getString("state_of_US") + "  ");
                        System.out.print(resultSet.getDouble("latitude") + "  ");
                        System.out.println(resultSet.getDouble("longitude") + "  ");

                    }

                }
            }
        } catch (SQLException exception) {
            throw new DBAccessException(exception);
        }
    }

}
