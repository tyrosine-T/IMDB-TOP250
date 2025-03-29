package util;

import java.sql.*;

public class DBUtil {
    // 在类进行实例加载时执行的一次性初始化操作，
    // 简单来说就是被 **static修饰的代码块** 会在该类首次被加载的时候执行，
    // 并且只执行一次


    static{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // 打印异常信息在程序中出错的位置及原因
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     * 一个建立数据库连接的方法
     * 该方法的返回类型就是一个 Connection 对象
     */
    //
    public static Connection getConnection(){
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/movie?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            String username = "root";
            String password = "123456";
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("tes");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     *
     * @param conn
     * @param pstmt
     * @param rs
     * 关闭数据库连接
     */
    public static void closeConnection(Connection conn, PreparedStatement pstmt, ResultSet rs){
        try{
            if(rs != null){
                // rs 不为空，说明已经有一个数据库查询结果了，现在需要关闭该查询结果。
                rs.close();
            }
            if(pstmt != null){
                // rs 不为空，说明已经有一个数据库查询结果了，现在需要关闭该查询结果。
                pstmt.close();
            }
            if(conn != null){
                // rs 不为空，说明已经有一个数据库查询结果了，现在需要关闭该查询结果。
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
