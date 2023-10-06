package DB;

import Lib.PropsLoader;
import java.sql.*;
import java.util.HashMap;

public class DataManager {

    private static final PropsLoader propsLoader;

    static {
        propsLoader = new PropsLoader();
    }

    public static final String DRIVER = propsLoader.getDriver();
    public static final String SERVER_NAME = propsLoader.getServerName();
    public static final String DATABASE_NAME = propsLoader.getDatabaseName();
    public static final String PORT = propsLoader.getPort();
    public static final String USER = propsLoader.getUser();
    public static final String PASSWORD = propsLoader.getPassword();

    public static final String DB_CONFIG = SERVER_NAME
            + ":" + PORT + ";databaseName=" + DATABASE_NAME + ";user=" + USER + ";password=" + PASSWORD + ";";

    public static final String URL = "jdbc:sqlserver://" + DB_CONFIG + "encrypt=true;trustServerCertificate=true;";

    private static Connection conn;

    // --------------------- Init server and create connection ---------------------
    // \\
    static {
        try {
            Class.forName(DRIVER); // Tao doi tuong
            // Change this url if you want to connect to other server.
            conn = DriverManager.getConnection(URL);
            // ResultSet resultSet = statement.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --------------------- Setters and Getters --------------------- \\
    public static Connection getConnection() {
        return conn;
    }

}
