package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {
    private static DbConnection dbConnection;
    private static Connection con;

    private static final String URL = "jdbc:mysql://localhost:3306/carsale";
    private static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "ijse");
    }


    private DbConnection() throws SQLException {
        con = DriverManager.getConnection(URL, props);
    }

    public static DbConnection getInstance() throws SQLException {
        return (null == dbConnection) ? dbConnection = new DbConnection() : dbConnection;
    }

    public Connection getConnection() {
        return con;
    }
}
