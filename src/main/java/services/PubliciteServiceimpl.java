package services;

import entite.Offre;
import entite.Publicite;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public  class PubliciteServiceimpl extends PubliciteService {

    private Connection conn;

    // Constructor qui initialise la connexion à la base de données
    public PubliciteServiceimpl() {
        this.conn = conn;
    }

    @Override
    public void ajouterPublicite(String titre, String description, String mediaUrl, Date date, String statut, int idAnnonceur) {
        String sql = "INSERT INTO Publicite (titre, description, media_url, date, statut, id_annonceur) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titre);
            stmt.setString(2, description);
            stmt.setString(3, mediaUrl);
            stmt.setDate(4, new Date(date.getTime())); // Conversion Date en java.sql.Date
            stmt.setString(5, statut);
            stmt.setInt(6, idAnnonceur);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Publicite> getAllPublicites() {
        List<Publicite> publicites = new ArrayList<>();
        String sql = "SELECT * FROM Publicite";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Créer un objet Publicite et l'ajouter à la liste
                Publicite pub = new Publicite(rs.getInt("id_publicite"),
                        rs.getString("titre"),
                        rs.getString("description"),
                        rs.getString("media_url"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("statut"),
                        rs.getInt("id_annonceur"));
                publicites.add(pub);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return publicites;
    }

    @Override
    public Offre getOne(int id) {
        return null;
    }

    // Implémenter d'autres méthodes CRUD (Update, Delete) si nécessaire
}
