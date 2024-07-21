package movie.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import movie.db.DatabaseConnection;
import movie.dto.Booking;

public class BookingDAO {

    public boolean bookMovie(String username, int movieId) {
        boolean isBooked = false;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO Booking (user_id, movie_id, booking_date) VALUES ((SELECT id FROM User WHERE username = ?), ?, ?)")) {
            stmt.setString(1, username);
            stmt.setInt(2, movieId);
            stmt.setDate(3, new Date(System.currentTimeMillis()));
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                isBooked = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isBooked;
    }

    public List<Booking> getUserBookings(String username) {
        List<Booking> bookings = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT b.id, m.title, b.booking_date " +
                             "FROM Booking b JOIN Movie m ON b.movie_id = m.id " +
                             "JOIN User u ON b.user_id = u.id " +
                             "WHERE u.username = ?")) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setMovieTitle(rs.getString("title"));
                booking.setBookingDate(rs.getDate("booking_date"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public boolean cancelBooking(int bookingId) {
        boolean isCancelled = false;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement("DELETE FROM Booking WHERE id = ?")) {
            stmt.setInt(1, bookingId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                isCancelled = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isCancelled;
    }
}
