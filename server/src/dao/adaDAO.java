package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Movie;
import util.DBUtil;

public class adaDAO {

    public static List<Movie> searchMovies(String title, String director, String actor, String genre, String content_rating,
                                           String duration , String release_year) {

        List<Movie> movieList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT m.movie_id, m.title, m.description, m.image_url, m.content_rating, m.duration, m.release_year, "
                + "d.director AS director_name, GROUP_CONCAT(DISTINCT a.actor SEPARATOR ', ') AS actor_name, "
                + "r.rating, GROUP_CONCAT(DISTINCT g.genre SEPARATOR ', ') AS genre "
                + "FROM Movies m "
                + "LEFT JOIN Directors d ON m.movie_id = d.movie_id "
                + "LEFT JOIN Actors a ON m.movie_id = a.movie_id "
                + "LEFT JOIN Ratings r ON m.movie_id = r.movie_id "
                + "LEFT JOIN Genres g ON m.movie_id = g.movie_id "
                + "WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (title != null && !title.isEmpty()) {
            sql.append(" AND m.title LIKE ?");
            params.add("%" + title + "%");
        }
        if (director != null && !director.isEmpty()) {
            sql.append(" AND d.name = ?");
            params.add("%" + director + "%");
        }
        if (actor != null && !actor.isEmpty()) {
            sql.append(" AND a.actor LIKE ?");
            params.add("%" + actor + "%");
        }
        if (genre != null && !genre.isEmpty()) {
            sql.append(" AND g.genre LIKE ?");
            params.add("%" + genre + "%");
        }
        if (content_rating != null && !content_rating.isEmpty()) {
            sql.append(" AND m.content_rating = ?");
            params.add("%" + content_rating + "%");
        }
        if (duration != null && !duration.isEmpty()) {
            sql.append(" AND m.duration = ?");
            params.add("%" + duration + "%");
        }
        if (release_year != null && !release_year.isEmpty()) {
            sql.append(" AND m.release_year = ?");
            params.add("%" + release_year + "%");
        }

        sql.append(" GROUP BY m.movie_id, m.title, m.description, m.image_url, m.content_rating, m.duration, m.release_year, d.director, r.rating");

        try {
            Connection conn = null;
            PreparedStatement pstmt = null;
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Movie movie = new Movie();
                movie.setMovie_id(rs.getString("movie_id"));
                movie.setTitle(rs.getString("title"));
                movie.setDescription(rs.getString("description"));
                movie.setImage_url(rs.getString("image_url"));
                movie.setContent_rating(rs.getString("content_rating"));
                movie.setDuration(rs.getString("duration"));
                movie.setRelease_year(rs.getString("release_year"));
                movie.setDirector_name(rs.getString("director_name"));
                movie.setActor_name(rs.getString("actor_name"));
                movie.setRating(rs.getInt("rating"));
                movie.setGenre(rs.getString("genre"));

                movieList.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movieList;
    }
}
