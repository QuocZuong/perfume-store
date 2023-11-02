package Lib;

import java.io.FileInputStream;
import java.util.Properties;

public class PropsLoader {

    private Properties props;
    private boolean noData;

    public PropsLoader() {
        props = new Properties();

        try {
            noData = false;

            String filePath = String.format("%s/%s", System.getProperty("user.dir").replace("\\", "/"),
                    "db.properties");

            String osName = System.getProperty("os.name");
            if (osName.equals("Linux")) {
                filePath = String.format("%s/%s","/opt/tomcat/updated/bin", "db.properties");
            }

            System.out.println("File: " + filePath);
            FileInputStream in = new FileInputStream(filePath);

            props.load(in);
            in.close();
        } catch (Exception e) {
            noData = true;
            e.printStackTrace();
        }
    }

    public String getDriver() {
        return noData ? "" : props.getProperty("DRIVER");
    }

    public String getServerName() {
        return noData ? "" : props.getProperty("SERVER_NAME");
    }

    public String getPort() {
        return noData ? "" : props.getProperty("PORT");
    }

    public String getDatabaseName() {
        return noData ? "" : props.getProperty("DATABASE_NAME");
    }

    public String getUser() {
        return noData ? "" : props.getProperty("USER");
    }

    public String getPassword() {
        return noData ? "" : props.getProperty("PASSWORD");
    }
}
