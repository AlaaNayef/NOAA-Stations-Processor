package jo.edu.htu.upskilling.utilities;

import jo.edu.htu.upskilling.dbexceptions.DBAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TotalNumOfRecords {

    public static int rowsCount(DataSource dataSource,String sql){
        int updatedRows;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
}
