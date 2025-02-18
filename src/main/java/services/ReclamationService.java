package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import entite.Reclamation;
import tools.DataSource;

public class ReclamationService implements IService<Reclamation> {
    private Connection cnx;

    public ReclamationService() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Reclamation reclamation) {
        String query = "INSERT INTO reclamation (user_id, type, description, objet, etat) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, reclamation.getUserId());
            ps.setString(2, reclamation.getType());
            ps.setString(3, reclamation.getDescription());
            ps.setString(4, reclamation.getObjet());
            ps.setString(5, reclamation.getEtat());
            ps.executeUpdate();
            System.out.println("Reclamation ajoutée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    @Override
    public void modifier(Reclamation reclamation) {
        String query = "UPDATE reclamation SET type = ?, description = ?, objet = ?, etat = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, reclamation.getType());
            ps.setString(2, reclamation.getDescription());
            ps.setString(3, reclamation.getObjet());
            ps.setString(4, reclamation.getEtat());
            ps.setInt(5, reclamation.getId());
            ps.executeUpdate();
            System.out.println("Reclamation modifiée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification : " + e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String query = "DELETE FROM reclamation WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Reclamation supprimée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    @Override
    public Reclamation getOne(Reclamation reclamation) {
        String query = "SELECT * FROM reclamation WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, reclamation.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Reclamation(
                        rs.getInt("user_id"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getString("objet"),
                        rs.getString("etat")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Reclamation> getAll(Reclamation reclamation) {
        List<Reclamation> reclamations = new ArrayList<>();
        try {
            String query = "SELECT * FROM reclamation";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Reclamation rec = new Reclamation(
                        rs.getInt("user_id"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getString("objet"),
                        rs.getString("etat")
                );
                rec.setId(rs.getInt("id"));
                rec.setDateReclamation(rs.getString("date_reclamation"));
                reclamations.add(rec);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des réclamations : " + ex.getMessage());
        }
        return reclamations;
    }

    public ObservableList<Reclamation> afficherReclamation() {
        ObservableList<Reclamation> recList = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM reclamation";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Reclamation rec = new Reclamation(
                        rs.getInt("user_id"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getString("objet"),
                        rs.getString("etat")
                );
                rec.setId(rs.getInt("id"));
                rec.setDateReclamation(rs.getString("date_reclamation"));
                recList.add(rec);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage : " + e.getMessage());
        }
        return recList;
    }
}
