package dao;

import util.DBUtil;
import entity.Director;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DirectorDAO {
    // 获取Director表所有数据
    public List<Director> getAllDirectors() {
        List<Director> directorList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 获取数据库连接
            conn = DBUtil.getConnection();

            // 执行查询语句
            String sql = "SELECT * FROM Directors;";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // 构建director对象列表
            while (rs.next()) {
                Integer director_movie_id = rs.getInt("director_movie_id");
                String movie_id = rs.getString("movie_id");
                String name = rs.getString("director");

                Director director = new Director(director_movie_id, movie_id, name);
                directorList.add(director);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭数据库连接
            DBUtil.closeConnection(conn, pstmt, rs);
        }
        return directorList;
    }


    public int addDirector(Director director) {
        int count = 0;
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        // 连接数据库，执行插入操作
        try {
            String getMaxIdSql = "SELECT MAX(director_movie_id) FROM Directors;";
            String insertSql = "INSERT INTO Directors (director_movie_id, movie_id, director) VALUES (?, ?, ?);";

            // 查询当前数据库中最大的director_movie_id
            PreparedStatement getMaxIdStmt = conn.prepareStatement(getMaxIdSql);
            rs = getMaxIdStmt.executeQuery();
            int maxId = 0;
            if (rs.next()) {
                maxId = rs.getInt(1);
            }

            // 生成新的director_movie_id
            int newId = maxId + 1;

            // 插入新director
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setInt(1, newId);
            insertStmt.setString(2, director.getMovie_id());
            insertStmt.setString(3, director.getName());
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
            pstmt = conn.prepareStatement("delete from Directors where director_movie_id = ?");
            pstmt.setInt(1, id);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, pstmt, rs);
        }
        return count;
    }



    public  Director selectById(int id){
        Director u = new Director();
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = conn.prepareStatement("select * from Directors where director_movie_id = ?");
            pstmt.setInt(1, id); // 将占位符 ？的值设置为 id
            rs = pstmt.executeQuery();
            while (rs.next()){
                u = new Director(rs.getInt(1), // 从结果集获取第一列的整数值
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


    public int update(Director u){
        int count = 0;
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = conn.prepareStatement("update Directors set movie_id = ?, director = ? where director_movie_id = ?");
            pstmt.setString(1, u.getMovie_id());
            pstmt.setString(2, u.getName());
            pstmt.setInt(3, u.getDirector_movie_id());
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, pstmt, rs);
        }
        return count;
    }

    //根据条件检索
    public List<Director> filterDirectors(String searchKey, String searchValue) {
        List<Director> filteredDirectors = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        // 连接数据库并执行查询操作
        try {
            String sql = "SELECT * FROM Directors WHERE " + searchKey + " = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,  searchValue);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int director_movie_id = rs.getInt("director_movie_id");
                String movie_id = rs.getString("movie_id");
                String name = rs.getString("director");

                // 创建 director 对象并添加到筛选后的部门列表中
                Director director = new Director(director_movie_id, movie_id, name);
                filteredDirectors.add(director);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredDirectors;
    }
}
