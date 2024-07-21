package movie.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import movie.db.DatabaseConnection;
import movie.dto.Movie;

public class MovieDAO {

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Movie")) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getInt("id"));
                movie.setTitle(rs.getString("title"));
                movie.setGenre(rs.getString("genre"));
                movie.setDuration(rs.getInt("duration"));
                movie.setDescription(rs.getString("description"));
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
