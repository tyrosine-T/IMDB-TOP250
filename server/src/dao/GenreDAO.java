package dao;

import util.DBUtil;
import entity.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO {
    // 获取Genre表所有数据
    public List<Genre> getAllGenres() {
        List<Genre> genreList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 获取数据库连接
            conn = DBUtil.getConnection();

            // 执行查询语句
            String sql = "SELECT * FROM Genres;";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // 构建genre对象列表
            while (rs.next()) {
                Integer genre_id = rs.getInt("genre_id");
                String movie_id = rs.getString("movie_id");
                String genre_name = rs.getString("genre");

                Genre genre = new Genre(genre_id, movie_id, genre_name);
                genreList.add(genre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭数据库连接
            DBUtil.closeConnection(conn, pstmt, rs);
        }
        return genreList;
    }


    public int addGenre(Genre genre) {
        int count = 0;
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        // 连接数据库，执行插入操作
        try {
            String getMaxIdSql = "SELECT MAX(genre_id) FROM Genres;";
            String insertSql = "INSERT INTO Genres (genre_id, movie_id, genre) VALUES (?, ?, ?);";

            // 查询当前数据库中最大的genre_movie_id
            PreparedStatement getMaxIdStmt = conn.prepareStatement(getMaxIdSql);
            rs = getMaxIdStmt.executeQuery();
            int maxId = 0;
            if (rs.next()) {
                maxId = rs.getInt(1);
            }

            // 生成新的genre_id
            int newId = maxId + 1;
            // 插入新genre
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setInt(1, newId);
            insertStmt.setString(2, genre.getMovie_id());
            insertStmt.setString(3, genre.getGenre());
            count = insertStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeConnection(conn, pstmt, rs);
        }
        //System.out.println(count);
        return count;
    }



    public int delete(int id){
        int count = 0;
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = conn.prepareStatement("delete from Genres where genre_id = ?");
            pstmt.setInt(1, id);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, pstmt, rs);
        }
        return count;
    }



    public  Genre selectById(int id){
        Genre u = new Genre();
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = conn.prepareStatement("select * from Genres where genre_id = ?");
            pstmt.setInt(1, id); // 将占位符 ？的值设置为 id
            rs = pstmt.executeQuery();
            while (rs.next()){
                u = new Genre(rs.getInt(1), // 从结果集获取第一列的整数值
                        rs.getString(2), // 从结果集获取第二列的字符串值，后面以此类推
                        rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, pstmt, rs);
        }
        return u;
    }


    public int update(Genre u){
        int count = 0;
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = conn.prepareStatement("update Genres set movie_id = ?, genre = ? where genre_id = ?");
            pstmt.setString(1, u.getMovie_id());
            pstmt.setString(2, u.getGenre());
            pstmt.setInt(3, u.getGenre_id());
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, pstmt, rs);
        }
        return count;
    }

    //根据条件检索
    public List<Genre> filterGenres(String searchKey, String searchValue) {
        List<Genre> filteredGenres = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        // 连接数据库并执行查询操作
        try {
            String sql = "SELECT * FROM Genres WHERE " + searchKey + " = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,  searchValue);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int genre_id = rs.getInt("genre_id");
                String movie_id = rs.getString("movie_id");
                String genre_name = rs.getString("genre");

                // 创建 genre 对象并添加到筛选后的部门列表中
                Genre genre = new Genre(genre_id, movie_id, genre_name);
                filteredGenres.add(genre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredGenres;
    }
}
