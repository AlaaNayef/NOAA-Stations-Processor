package jo.edu.htu.upskilling.users;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DBUSerRepository implements UserRepository {

    private DataSource dataSource;

    private static final String SQL_CREATE = "create table users " +
            "(" +
            "username varchar(255) primary key," +
            " name varchar(255) not null ," +
            "email varchar(255) unique," +
            "password varchar(255) unique," +
            "status int)";
    private static final String SQL_INSERT = "insert into users(username,name,email,password,status) values (?,?,?,?,?)";
    private static final String UPDATE_Password = "update users set password = ? where username = ?";
    private static final String UPDATE_STATUS = "update users set status = ? where username = ?";
    private static final String SELECT_USERS = "select username Username , name Name, email EMail, status from users ";
    private static final String SELECT_USER_BY_USERNAME = "select username,name,email, password,status where username =? ";

    public DBUSerRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createTable(UserTransferObj user) {

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insert(UserTransferObj user) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
                preparedStatement.setString(1, user.getUserName());
                preparedStatement.setString(2, user.getName());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getPassword());
                preparedStatement.setInt(5, user.getStatus());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println();
        }

    }

    public void updatePassword(String userName,String newPassword) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_Password)) {
                statement.setString(1, newPassword);
                statement.setString(2, userName);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateStatus(String userName,int status) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_STATUS)) {
                statement.setInt(1, status);
                statement.setString(2, userName);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public UserTransferObj findByUserName(String userName) {
        UserTransferObj user = new UserTransferObj();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_USERNAME)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        if (userName.equals(resultSet.getString("username"))) {
                            user.setUserName(resultSet.getString("username"));
                            user.setName(resultSet.getString("name"));
                            user.setEmail(resultSet.getString("email"));
                            user.setPassword(resultSet.getString("password"));
                            user.setStatus(resultSet.getInt("status"));
                        }else{
                            return null;
                        }
                    }
                }
            }
        } catch (SQLException exception) {
            System.out.println(exception);
        }
        return user;
    }

    public List<UserTransferObj> AllUsers() {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_USERS)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<UserTransferObj> AllUsers = new ArrayList<>();
                    while (resultSet.next()) {
                        UserTransferObj user = new UserTransferObj();
                        user.setUserName(resultSet.getString("username"));
                        user.setName(resultSet.getString("name"));
                        user.setEmail(resultSet.getString("email"));
                        //user.setPassword(resultSet.getString("password"));
                        user.setStatus(resultSet.getInt("status"));
                        AllUsers.add(user);
                    }
                    return AllUsers;
                }

            }
        } catch (SQLException exception) {
            System.out.println(exception);
        }

        return null;
    }


}
