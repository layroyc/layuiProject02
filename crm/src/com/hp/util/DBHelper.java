package com.hp.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DBHelper {

    static DruidDataSource dataSource;

    static{
        Properties properties = new Properties();
        try {
            properties.load(DBHelper.class.getClassLoader().getResourceAsStream("jdbc.properties"));

            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        try {
           return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close(Connection conn, PreparedStatement ps){
        try {
            if (conn!=null){
                conn.close();
            }
            if(ps!=null){
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection conn, PreparedStatement ps, ResultSet rs){
        try {
            if (rs!=null){
                rs.close();
            }
            close(conn,ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(DBHelper.getConnection());
    }



}
