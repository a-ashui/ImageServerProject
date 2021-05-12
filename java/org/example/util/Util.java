package org.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.w3c.dom.ranges.RangeException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {

    private static final ObjectMapper M = new ObjectMapper();

    private static final MysqlDataSource DS = new MysqlDataSource();

    //TODO:学完多线程双重校验锁的单例模式
    static {
        DS.setURL("jdbc:mysql://localhost:3306/image_system");
        DS.setUser("root");
        DS.setPassword("001130");
        DS.setUseSSL(false);
        DS.setCharacterEncoding("utf-8");

    }

    /**
     * java对象序列化为json字符串
     */
    public static String serialize(Object o){
        try {
            return M.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("序列化java对象失败：" +o, e);
        }
    }

    public static Connection getConnection(){
        try {
            return DS.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("数据库连接获取失败",e);
        }
    }

    public static void close(Connection c, Statement s, ResultSet rs) {
        try {
            if(rs != null) rs.close();
            if(s != null) s.close();
            if(c != null) c.close();
        } catch (SQLException e) {
            throw new RuntimeException("释放数据库资源失败",e);
        }
    }

    public static void close(Connection c,Statement s) {
        close(c,s,null);
    }

    public static void main(String[] args) throws SQLException {
        System.out.println(DS.getConnection());
    }

}
