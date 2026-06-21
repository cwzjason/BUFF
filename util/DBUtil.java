package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Unified Database Connection Utility Class
 * Change cloud server address here only
 */
public class DBUtil {
    
    // ========== 修改这里即可切换数据库 ==========
    private static final String URL      = "jdbc:mysql://127.0.0.1:3306/db1";
    private static final String USER     = "root";
    private static final String PASSWORD = "123456";
    // ==========================================
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public static String getUrl()      { return URL; }
    public static String getUser()     { return USER; }
    public static String getPassword() { return PASSWORD; }
}
