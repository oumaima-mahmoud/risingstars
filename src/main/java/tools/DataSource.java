package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private Connection cnx;
    private static DataSource instance;

    private String url = "jdbc:mysql://localhost:3306/tunistade"; // URL de connexion à la base de données
    private String user = "root"; // Utilisateur de la base de données
    private String password = ""; // Mot de passe de l'utilisateur

    // Constructeur privé pour éviter plusieurs instances
    private DataSource(){
        try {
            cnx = DriverManager.getConnection(url, user, password); // Connexion à la base de données
            System.out.println("Connected to DB !");
        } catch (SQLException ex) {
            System.err.println("Connection failed: " + ex.getMessage());
        }
    }

    // Méthode pour obtenir l'instance unique de DataSource
    public static DataSource getInstance(){
        if(instance == null){
            instance = new DataSource();
        }
        return instance;
    }

    // Méthode pour obtenir la connexion à la base de données
    public Connection getConnection(){
        return this.cnx;
    }

    // Optional: Close connection method
    public void closeConnection() {
        try {
            if (cnx != null && !cnx.isClosed()) {
                cnx.close();
                System.out.println("Connection closed!");
            }
        } catch (SQLException ex) {
            System.err.println("Error closing connection: " + ex.getMessage());
        }
    }
}
