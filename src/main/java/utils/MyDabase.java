package utils;
import java.sql.*;

public class MyDabase {
    private Connection cnx;
    private static MyDabase instance;

    final String url = "jdbc:mariadb://127.0.0.1:3306/tunistade?useSSL=false&serverTimezone=UTC";
    final String user = "root";
    final String password = "";

    private MyDabase(){
        try {
            cnx = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to DB !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static MyDabase getInstance(){
        if(instance == null){
            instance = new MyDabase();
        }
        return instance;
    }

    public Connection getConnection(){
        return this.cnx;
    }
}

