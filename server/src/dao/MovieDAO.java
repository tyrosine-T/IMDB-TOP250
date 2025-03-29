package dao;
import entity.*;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {
    // 获取Movies表所有数据
    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 获取数据库连接
            conn = DBUtil.getConnection();

            // 执行查询语句
            String sql = "SELECT m.movie_id, m.title, m.description, m.image_url, m.content_rating, m.duration, m.release_year, "
                    + "d.director AS director_name, GROUP_CONCAT(DISTINCT a.actor SEPARATOR ', ') AS actor_name, "
                    + "r.rating, GROUP_CONCAT(DISTINCT g.genre SEPARATOR ', ') AS genre " + "FROM Movies m "
                    + "LEFT JOIN Directors d ON m.movie_id = d.movie_id " + "LEFT JOIN Actors a ON m.movie_id = a.movie_id "
                    + "LEFT JOIN Ratings r ON m.movie_id = r.movie_id " + "LEFT JOIN Genres g ON m.movie_id = g.movie_id "
                    + "GROUP BY m.movie_id, m.title, m.description, m.image_url, m.content_rating, m.duration, m.release_year, d.director, r.rating";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // 构建movie对象列表
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
        } finally {
            // 关闭数据库连接
            DBUtil.closeConnection(conn, pstmt, rs);
        }

        return movieList;
    }

    public int addMovie(Movie movie, Integer rating_count) {
        int count = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                throw new SQLException("Database connection failed.");
            }

            conn.setAutoCommit(false);  // 开始事务
            String movie_id = movie.getMovie_id();

            if (movie_id == null || movie.getTitle() == null || movie.getDescription() == null || movie.getImage_url() == null || movie.getContent_rating() == null || movie.getDuration() == null || movie.getRelease_year() == null || movie.getDirector_name() == null || movie.getActor_name() == null || movie.getGenre() == null) {
                throw new IllegalArgumentException("Movie object and all its fields must not be null.");
            }

            // 插入到Movies表
            String sql = "INSERT INTO Movies (movie_id, title, description, image_url, content_rating, duration, release_year) VALUES (?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, movie.getMovie_id());
            pstmt.setString(2, movie.getTitle());
            pstmt.setString(3, movie.getDescription());
            pstmt.setString(4, movie.getImage_url());
            pstmt.setString(5, movie.getContent_rating());
            pstmt.setString(6, movie.getDuration());
            pstmt.setString(7, movie.getRelease_year());
            count += pstmt.executeUpdate();
            pstmt.close();

            // 插入到Ratings表
            String getMaxIdSql = "SELECT MAX(rating_id) FROM Ratings;";
            PreparedStatement getMaxIdStmt = conn.prepareStatement(getMaxIdSql);
            rs = getMaxIdStmt.executeQuery();
            int maxId = 0;
            if (rs.next()) {
                maxId = rs.getInt(1);
            }
            getMaxIdStmt.close();
            rs.close();

            sql = "INSERT INTO Ratings (rating_id, movie_id, rating, rating_count) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, maxId + 1);
            pstmt.setString(2, movie.getMovie_id());
            pstmt.setInt(3, movie.getRating());
            pstmt.setInt(4, rating_count);
            count += pstmt.executeUpdate();
            pstmt.close();

            conn.commit();  // 提交事务

            // 插入到Genres表
            String[] genres = movie.getGenre().split(",\\s*");
            for (String genre : genres) {
                //System.out.println(genre);
                Genre g = new Genre(movie.getMovie_id(), genre);
                GenreDAO genreDAO = new GenreDAO();
                count += genreDAO.addGenre(g);
            }



            // 插入到Directors表
            Director director = new Director(movie.getMovie_id(), movie.getDirector_name());
            DirectorDAO directorDAO = new DirectorDAO();
            count += directorDAO.addDirector(director);

            // 插入到Actors表
            String[] actors = movie.getActor_name().split(",\\s*");
            for (String actor : actors) {
                Actor u = new Actor(movie_id, actor);
                ActorDAO dao = new ActorDAO();
                count += dao.addActor(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();  // 回滚事务
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            count = 0;  // 如果发生异常，返回0表示没有成功插入记录
        } catch (Exception e) {
            e.printStackTrace();
            count = 0;  // 处理其他可能的异常
        } finally {
            DBUtil.closeConnection(conn, pstmt, rs);
        }
        return count;
    }




    public int delete(String movieId) {
        int count = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                throw new SQLException("Database connection failed.");
            }

            conn.setAutoCommit(false);  // 开始事务

            // 删除Actors表中的数据
            String sql = "DELETE FROM Actors WHERE movie_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, movieId);
            count += pstmt.executeUpdate();
            pstmt.close();

            // 删除Directors表中的数据
            sql = "DELETE FROM Directors WHERE movie_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, movieId);
            count += pstmt.executeUpdate();
            pstmt.close();

            // 删除Ratings表中的数据
            sql = "DELETE FROM Ratings WHERE movie_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, movieId);
            count += pstmt.executeUpdate();
            pstmt.close();

            // 删除Genres表中的数据
            sql = "DELETE FROM Genres WHERE movie_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, movieId);
            count += pstmt.executeUpdate();
            pstmt.close();

            // 删除Movies表中的数据
            sql = "DELETE FROM Movies WHERE movie_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, movieId);
            count += pstmt.executeUpdate();
            pstmt.close();

            conn.commit();  // 提交事务
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();  // 回滚事务
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            count = 0;  // 如果发生异常，返回0表示没有成功删除记录
        } finally {
            DBUtil.closeConnection(conn, pstmt, null);
        }
        return count;
    }





    public  Movie selectById(String id){//根据电影id查询
        Movie movie = new Movie();
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT m.movie_id, m.title, m.description, m.image_url, m.content_rating, m.duration, m.release_year, "
                    + "d.name AS director_name, GROUP_CONCAT(DISTINCT a.actor SEPARATOR ', ') AS actor_name, "
                    + "r.rating, GROUP_CONCAT(DISTINCT g.genre SEPARATOR ', ') AS genre " + "FROM Movies m "
                    + "LEFT JOIN Directors d ON m.movie_id = d.movie_id " + "LEFT JOIN Actors a ON m.movie_id = a.movie_id "
                    + "LEFT JOIN Ratings r ON m.movie_id = r.movie_id " + "LEFT JOIN Genres g ON m.movie_id = g.movie_id "
                    + "WHERE m.movie_id = ? "
                    + "GROUP BY m.movie_id, m.title, m.description, m.image_url, m.content_rating, m.duration, m.release_year, d.director, r.rating";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id); // 将占位符 ？的值设置为 id
            rs = pstmt.executeQuery();
            while (rs.next()){
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, pstmt, rs);
        }
        return movie;
    }


    public int update(Movie movie) {
        int count = 0;
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn.setAutoCommit(false);  // 开始事务

            // 更新Movies表
            String sql = "UPDATE Movies SET title = ?, description = ?, image_url = ?, content_rating = ?, duration = ?, release_year = ? WHERE movie_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getDescription());
            pstmt.setString(3, movie.getImage_url());
            pstmt.setString(4, movie.getContent_rating());
            pstmt.setString(5, movie.getDuration());
            pstmt.setString(6, movie.getRelease_year());
            pstmt.setString(7, movie.getMovie_id());
            count += pstmt.executeUpdate();

            pstmt.close();  // 关闭之前的PreparedStatement

            // 更新Directors表
            sql = "UPDATE Directors SET director = ? WHERE movie_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, movie.getDirector_name());
            pstmt.setString(2, movie.getMovie_id());
            count += pstmt.executeUpdate();

            pstmt.close();  // 关闭之前的PreparedStatement

            // 清空并更新Actors表
            sql = "DELETE FROM Actors WHERE movie_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, movie.getMovie_id());
            pstmt.executeUpdate();

            pstmt.close();  // 关闭之前的PreparedStatement

            String[] actors = movie.getActor_name().split(",\\s*");
            sql = "INSERT INTO Actors (movie_id, actor) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql);
            for (String actor : actors) {
                pstmt.setString(1, movie.getMovie_id());
                pstmt.setString(2, actor);
                pstmt.addBatch();
            }
            int[] actorCounts = pstmt.executeBatch();
            count += actorCounts.length;

            pstmt.close();  // 关闭之前的PreparedStatement

            // 更新Ratings表
            sql = "UPDATE Ratings SET rating = ? WHERE movie_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, movie.getRating());
            pstmt.setString(2, movie.getMovie_id());
            count += pstmt.executeUpdate();

            pstmt.close();  // 关闭之前的PreparedStatement

            // 清空并更新Genres表
            sql = "DELETE FROM Genres WHERE movie_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, movie.getMovie_id());
            pstmt.executeUpdate();

            pstmt.close();  // 关闭之前的PreparedStatement

            String[] genres = movie.getGenre().split(",\\s*");
            sql = "INSERT INTO Genres (movie_id, genre) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql);
            for (String genre : genres) {
                pstmt.setString(1, movie.getMovie_id());
                pstmt.setString(2, genre);
                pstmt.addBatch();
            }
            int[] genreCounts = pstmt.executeBatch();
            count += genreCounts.length;

            conn.commit();  // 提交事务
            return count;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();  // 回滚事务
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return 0;
        } finally {
            DBUtil.closeConnection(conn, pstmt, rs);
        }
    }


    //根据条件检索
    public List<Movie> filterMovies(String searchKey, String searchValue) {
        List<Movie> filteredMovies = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        // 连接数据库并执行查询操作
        try {
            StringBuilder sql = new StringBuilder("SELECT m.movie_id, m.title, m.description, m.image_url, m.content_rating, m.duration, m.release_year, "
                    + "d.director AS director_name, GROUP_CONCAT(DISTINCT a.actor SEPARATOR ', ') AS actor_name, "
                    + "r.rating, GROUP_CONCAT(DISTINCT g.genre SEPARATOR ', ') AS genre "
                    + "FROM Movies m "
                    + "LEFT JOIN Directors d ON m.movie_id = d.movie_id "
                    + "LEFT JOIN Actors a ON m.movie_id = a.movie_id "
                    + "LEFT JOIN Ratings r ON m.movie_id = r.movie_id "
                    + "LEFT JOIN Genres g ON m.movie_id = g.movie_id "
                    + "WHERE ");
            // 动态构建查询条件
            switch (searchKey) {
                case "movie_id":
                    sql.append("m.movie_id = ?");
                    break;
                case "title":
                    sql.append("m.title LIKE ?");
                    searchValue = "%" + searchValue + "%";
                    break;
                case "genre":
                    sql.append("g.genre LIKE ?");
                    searchValue = "%" + searchValue + "%";
                    break;
                case "content_rating":
                    sql.append("m.content_rating = ?");
                    break;
                case "duration":
                    sql.append("m.duration = ?");
                    break;
                case "release_year":
                    sql.append("m.release_year = ?");
                    break;
                default:
                    throw new IllegalArgumentException("无效的搜索关键字: " + searchKey);
            }
            sql.append(" GROUP BY m.movie_id, m.title, m.description, m.image_url, m.content_rating, m.duration, m.release_year, d.director, r.rating");

            pstmt = conn.prepareStatement(sql.toString());

            pstmt.setString(1, searchValue);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String movie_id = rs.getString("movie_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String image_url = rs.getString("image_url");
                String content_rating = rs.getString("content_rating");
                String duration = rs.getString("duration");
                String release_year = rs.getString("release_year");
                String director_name = rs.getString("director_name");
                String actor_name = rs.getString("actor_name");
                int rating = rs.getInt("rating");
                String genre = rs.getString("genre");

                // 创建 movie 对象并添加到筛选后的部门列表中
                Movie movie = new Movie(movie_id, title, description, image_url, content_rating, duration, release_year, director_name, actor_name, rating, genre);
                filteredMovies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredMovies;
    }
}
