package pl.limescode.server.db;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {

    private Map<String, String> users;

    private static DatabaseManager manager = null;

    private DatabaseManager() {
        users = extractUsersFromDB();
    }

    private static Connection connection;
    private static Statement statement;

    public static DatabaseManager getDatabaseManager() {
        if (manager == null) {
            manager = new DatabaseManager();
        }
        return manager;
    }

    private Map<String, String> extractUsersFromDB() {
        Map<String, String> users = new HashMap<>();
        try (Statement stmp = getConnection()) {
            statement = stmp;

            long time = System.currentTimeMillis();
            connection.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                String login = resultSet.getString(2);
                String password = resultSet.getString(3);
                users.put(login, password);
            }
            statement.executeBatch();
            connection.setAutoCommit(true);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public boolean authenticateUser(String login, String password) {
        String recievedPassword = users.get(login);
        return recievedPassword.equals(password);
    }

    private static Statement getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:chat.db");
        statement = connection.createStatement();
        return statement;
    }

}
