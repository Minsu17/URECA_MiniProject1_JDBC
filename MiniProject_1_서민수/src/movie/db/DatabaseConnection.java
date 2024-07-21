package movie.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/movie_booking";
    private static final String USER = "root";
    private static final String PASSWORD = "2358";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static boolean validateUser(String username, String password) {
        boolean isValid = false;
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM User WHERE username = ? AND password = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                isValid = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValid;
    }

    public static boolean registerUser(String username, String password, String email) {
        boolean isRegistered = false;
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement("INSERT INTO User (username, password, email) VALUES (?, ?, ?)")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                isRegistered = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isRegistered;
    }
}
