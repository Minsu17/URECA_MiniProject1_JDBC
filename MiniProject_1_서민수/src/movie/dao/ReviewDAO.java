package movie.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import movie.db.DatabaseConnection;
import movie.dto.Review;

public class ReviewDAO {

    public boolean addReview(int movieId, String username, int rating, String comment) {
        boolean isAdded = false;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO Review (movie_id, user_id, rating, comment, review_date) VALUES (?, (SELECT id FROM User WHERE username = ?), ?, ?, ?)")) {
            stmt.setInt(1, movieId);
            stmt.setString(2, username);
            stmt.setInt(3, rating);
            stmt.setString(4, comment);
            stmt.setDate(5, new Date(System.currentTimeMillis()));
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                isAdded = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAdded;
    }

    public List<Review> getReviews(int movieId) {
        List<Review> reviews = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT r.id, u.username, r.rating, r.comment, r.review_date " +
                             "FROM Review r JOIN User u ON r.user_id = u.id " +
                             "WHERE r.movie_id = ?")) {
            stmt.setInt(1, movieId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Review review = new Review();
                review.setId(rs.getInt("id"));
                review.setUsername(rs.getString("username"));
                review.setRating(rs.getInt("rating"));
                review.setComment(rs.getString("comment"));
                review.setReviewDate(rs.getDate("review_date"));
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public boolean deleteReview(int reviewId) {
        boolean isDeleted = false;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement("DELETE FROM Review WHERE id = ?")) {
            stmt.setInt(1, reviewId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                isDeleted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }

    public boolean updateReview(int reviewId, int rating, String comment) {
        boolean isUpdated = false;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement("UPDATE Review SET rating = ?, comment = ? WHERE id = ?")) {
            stmt.setInt(1, rating);
            stmt.setString(2, comment);
            stmt.setInt(3, reviewId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                isUpdated = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdated;
    }
}
