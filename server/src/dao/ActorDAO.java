package dao;
import util.DBUtil;
import entity.Actor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActorDAO {
    // 获取Actors表所有数据
    public List<Actor> getAllActors() {
        List<Actor> actorList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 获取数据库连接
            conn = DBUtil.getConnection();

            // 执行查询语句
            String sql = "SELECT * FROM Actors;";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // 构建actor对象列表
            while (rs.next()) {
                Integer actor_id = rs.getInt("actor_id");
                String movie_id = rs.getString("movie_id");
                String name = rs.getString("actor");

                Actor actor = new Actor(actor_id, movie_id, name);
                actorList.add(actor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭数据库连接
            DBUtil.closeConnection(conn, pstmt, rs);
        }

        return actorList;
    }


    public int addActor(Actor actor) {
        int count = 0;
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        // 连接数据库，执行插入操作
        try {
            String getMaxIdSql = "SELECT MAX(actor_id) FROM Actors;";
            String insertSql = "INSERT INTO Actors (actor_id, movie_id, actor) VALUES (?, ?, ?);";

            // 查询当前数据库中最大的actor_id
            PreparedStatement getMaxIdStmt = conn.prepareStatement(getMaxIdSql);
            rs = getMaxIdStmt.executeQuery();
            int maxId = 0;
            if (rs.next()) {
                maxId = rs.getInt(1);
            }

            // 生成新的actor_id
            int newId = maxId + 1;
            //System.out.println(newId);
            // 插入新actor
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setInt(1, newId);
            insertStmt.setString(2, actor.getMovie_id());
            insertStmt.setString(3, actor.getName());
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
            pstmt = conn.prepareStatement("delete from Actors where actor_id = ?");
            pstmt.setInt(1, id);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, pstmt, rs);
        }
        return count;
    }



    public  Actor selectById(int id){
        Actor u = new Actor();
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = conn.prepareStatement("select * from Actors where actor_id = ?");
            pstmt.setInt(1, id); // 将占位符 ？的值设置为 id
            rs = pstmt.executeQuery();
            while (rs.next()){
                u = new Actor(rs.getInt(1), // 从结果集获取第一列的整数值
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


    public int update(Actor u){
        int count = 0;
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = conn.prepareStatement("update Actors set movie_id = ?, actor = ? where actor_id = ?");
            pstmt.setString(1, u.getMovie_id());
            pstmt.setString(2, u.getName());
            pstmt.setInt(3, u.getActor_id());
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, pstmt, rs);
        }
        return count;
    }

    //根据条件检索
    public List<Actor> filterActors(String searchKey, String searchValue) {
        List<Actor> filteredActors = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        // 连接数据库并执行查询操作
        try {
            String sql = "SELECT * FROM Actors WHERE " + searchKey + " LIKE ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + searchValue + "%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int actor_id = rs.getInt("actor_id");
                String movie_id = rs.getString("movie_id");
                String name = rs.getString("actor");

                // 创建 actor 对象并添加到筛选后的部门列表中
                Actor actor = new Actor(actor_id, movie_id, name);
                filteredActors.add(actor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredActors;
    }
}
