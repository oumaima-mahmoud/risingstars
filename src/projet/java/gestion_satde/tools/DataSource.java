package gestion_satde.tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataSource {
    private Connection con;
    private static DataSource instance;
    private String url = "jdbc:mysql://localhost:3306/esprit";
    private String user = "root";
    private String password = "";

    private DataSource(){
        try {
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to DB !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static DataSource getInstance(){
        if(instance == null){
            instance = new DataSource();
        }
        return instance;
    }
    public Connection getConnection(){
        return this.con;
    }

}
