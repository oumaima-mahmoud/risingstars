package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private Connection cnx;
    private static DataSource instance;
    private String url = "jdbc:mysql://localhost:3306/tunistade";
    private String user = "root";
    private String password = "";

    private DataSource() {
        try {
            this.cnx = DriverManager.getConnection(this.url, this.user, this.password);
            System.out.println("Connected to DB !");
        } catch (SQLException var2) {
            SQLException ex = var2;
            System.out.println(ex.getMessage());
        }

    }

    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }

        return instance;
    }

    public Connection getConnection() {
        return this.cnx;
    }
}

