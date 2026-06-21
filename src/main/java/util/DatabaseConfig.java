package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static Properties props = new Properties();
    
    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("db_config.properties")) {
            if (input != null) {
                props.load(input);
            } else {
                // 默认配置
                props.setProperty("db.url", "jdbc:mysql://127.0.0.1:3306/db1");
                props.setProperty("db.username", "root");
                props.setProperty("db.password", "123456");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // 使用默认配置
            props.setProperty("db.url", "jdbc:mysql://127.0.0.1:3306/db1");
            props.setProperty("db.username", "root");
            props.setProperty("db.password", "123456");
        }
    }
    
    public static String getUrl() {
        return props.getProperty("db.url");
    }
    
    public static String getUsername() {
        return props.getProperty("db.username");
    }
    
    public static String getPassword() {
        return props.getProperty("db.password");
    }
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(getUrl(), getUsername(), getPassword());
    }
}