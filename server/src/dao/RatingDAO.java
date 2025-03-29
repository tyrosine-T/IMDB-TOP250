package dao;
import util.DBUtil;
import entity.Rating;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RatingDAO {
    // 获取Ratings表所有数据
    public List<Rating> getAllRatings() {
        List<Rating> ratingList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 获取数据库连接
            conn = DBUtil.getConnection();

            // 执行查询语句
            String sql = "SELECT * FROM Ratings;";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // 构建rating对象列表
            while (rs.next()) {
                Integer rating_id = rs.getInt("rating_id");
                String movie_id = rs.getString("movie_id");
                int rating = rs.getInt("rating");
                Integer rating_count = rs.getInt("rating_count");

                Rating u = new Rating(rating_id, movie_id, rating, rating_count);
                ratingList.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭数据库连接
            DBUtil.closeConnection(conn, pstmt, rs);
        }

        return ratingList;
    }


    public int addRating(String movie_id, int rating) {
        int count = 0;
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        // 连接数据库，执行插入操作
        try {
            String getRatingIdSql = "SELECT rating_id, rating, rating_count FROM Ratings WHERE movie_id = ?";
            String updateSql = "UPDATE Ratings SET rating = ?, rating_count = ? WHERE rating_id = ?";

            // 根据movie_id查询rating_id
            pstmt = conn.prepareStatement(getRatingIdSql);
            pstmt.setString(1, movie_id);

            rs = pstmt.executeQuery();
            int rating_id = 0;
            int oldRating = 0;
            int oldRating_count = 0;
            if (rs.next()) {
                rating_id = rs.getInt("rating_id");
                oldRating = rs.getInt("rating");
                oldRating_count = rs.getInt("rating_count");
            }

            int newRating = (oldRating*oldRating_count + rating)/(oldRating_count + 1);

            // 执行更新操作，更新评分和评分数量
            pstmt = conn.prepareStatement(updateSql);
            pstmt.setInt(1, newRating);  // 更新后的评分
            pstmt.setInt(2, oldRating_count + 1);  // 更新后的评分数量
            pstmt.setInt(3, rating_id);  // 更新对应的 rating_id
            count = pstmt.executeUpdate();

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
            pstmt = conn.prepareStatement("delete from Ratings where rating_id = ?;");
            pstmt.setInt(1, id);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, pstmt, rs);
        }
        return count;
    }



    public  Rating selectById(int id){
        Rating u = new Rating();
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = conn.prepareStatement("select * from Ratings where rating_id = ?");
            pstmt.setInt(1, id); // 将占位符 ？的值设置为 id
            rs = pstmt.executeQuery();
            while (rs.next()){
                u = new Rating(rs.getInt(1), // 从结果集获取第一列的整数值
                        rs.getString(2), // 从结果集获取第二列的字符串值，后面以此类推
                        rs.getInt(3),
                        rs.getInt(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, pstmt, rs);
        }
        return u;
    }


    public int update(Rating u){
        int count = 0;
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = conn.prepareStatement("update Ratings set movie_id = ?, rating = ? ,rating_count = ? where rating_id = ?");
            pstmt.setString(1, u.getMovie_id());
            pstmt.setInt(2, u.getRating());
            pstmt.setInt(3, u.getRating_count());
            pstmt.setInt(4, u.getRating_id());
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, pstmt, rs);
        }
        return count;
    }

    //根据条件检索
    public List<Rating> filterRatings(String searchKey, String searchValue) {
        List<Rating> filteredRatings = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        // 连接数据库并执行查询操作
        try {
            String sql = "SELECT * FROM Ratings WHERE " + searchKey + " LIKE ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + searchValue + "%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int rating_id = rs.getInt("rating_id");
                String movie_id = rs.getString("movie_id");
                int rating = rs.getInt("rating");
                Integer rating_count = rs.getInt("rating_count");

                // 创建 rating 对象并添加到筛选后的部门列表中
                Rating u = new Rating(rating_id, movie_id, rating, rating_count);
                filteredRatings.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredRatings;
    }
}
