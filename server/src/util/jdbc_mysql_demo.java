package util;

import java.sql.*;

public class jdbc_mysql_demo {

    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL,一定要先下载jar包
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/movie?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";


    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "123456";

    public static void main(String[] args) {


        Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开数据库连接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行查询
            // 要执行sql语句，必须获得statement实例
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();//创建一个 Statement 对象，用于发送 SQL 语句到数据库。
            String sql;
            sql = "SELECT actor_id, movie_id, actor FROM actors";
            ResultSet rs = stmt.executeQuery(sql);//执行查询 SQL（SELECT 语句），并将查询结果返回到 ResultSet 对象中。

            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索，通过列名获取数据
                int actor_id  = rs.getInt("actor_id");
                String movie_id = rs.getString("movie_id");
                String actor = rs.getString("actor");

                // 输出数据
                System.out.print("actor_id: " + actor_id);
                System.out.print(", movie_id: " + movie_id);
                System.out.print(", actor: " + actor);
                System.out.print("\n");
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }
}