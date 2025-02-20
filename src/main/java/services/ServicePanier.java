package services;

import models.Panier;
import utils.MyDabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ServicePanier {
    private Connection cnx;

    public ServicePanier() {
        this.cnx = MyDabase.getInstance().getConnection();
    }

    // Méthode pour ajouter un panier
    public void ajouter(Panier t) throws SQLException {
        String req = "INSERT INTO panier (dateCreation, total, etat) VALUES (?, ?, ?)";
        try (PreparedStatement stm = cnx.prepareStatement(req)) {
            // Utilisation directe de java.util.Date sans conversion
            stm.setObject(1, t.getDateCreation());  // Utilisation de setObject au lieu de setDate
            stm.setDouble(2, t.getTotal());
            stm.setString(3, t.getEtat());
            stm.executeUpdate();
            System.out.println("Panier ajouté avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout : " + ex.getMessage());
            throw ex;
        }
    }

    // Méthode pour modifier un panier
    public void modifier(Panier t) throws SQLException {
        String checkReq = "SELECT COUNT(*) FROM panier WHERE idPanier = ?";
        try (PreparedStatement checkPst = cnx.prepareStatement(checkReq)) {
            checkPst.setInt(1, t.getIdPanier());
            try (ResultSet rs = checkPst.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    String req = "UPDATE panier SET dateCreation = ?, total = ?, etat = ? WHERE idPanier = ?";
                    try (PreparedStatement pst = cnx.prepareStatement(req)) {
                        // Utilisation directe de java.util.Date pour la mise à jour sans conversion
                        pst.setObject(1, t.getDateCreation());  // Utilisation de setObject pour éviter la conversion
                        pst.setDouble(2, t.getTotal());
                        pst.setString(3, t.getEtat());
                        pst.setInt(4, t.getIdPanier());
                        pst.executeUpdate();
                        System.out.println("Panier modifié avec succès !");
                    }
                } else {
                    System.out.println("Panier avec id " + t.getIdPanier() + " introuvable pour la modification.");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la modification : " + ex.getMessage());
            throw ex;
        }
    }

    // Méthode pour supprimer un panier
    public void supprimer(int idPanier) throws SQLException {
        String checkReq = "SELECT COUNT(*) FROM panier WHERE idPanier = ?";
        try (PreparedStatement checkPst = cnx.prepareStatement(checkReq)) {
            checkPst.setInt(1, idPanier);
            try (ResultSet rs = checkPst.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    String req = "DELETE FROM panier WHERE idPanier = ?";
                    try (PreparedStatement pst = cnx.prepareStatement(req)) {
                        pst.setInt(1, idPanier);
                        pst.executeUpdate();
                        System.out.println("Panier supprimé avec succès !");
                    }
                } else {
                    System.out.println("Panier avec id " + idPanier + " introuvable pour la suppression.");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression : " + ex.getMessage());
            throw ex;
        }
    }

    // Méthode pour récupérer tous les paniers
    public ObservableList<Panier> getAll() throws SQLException {
        ObservableList<Panier> paniers = FXCollections.observableArrayList();
        String req = "SELECT * FROM panier";
        try (Statement stm = cnx.createStatement(); ResultSet rs = stm.executeQuery(req)) {
            while (rs.next()) {
                Panier p = new Panier();
                p.setIdPanier(rs.getInt("idPanier"));
                p.setDateCreation(rs.getObject("dateCreation", Date.class));  // Utilisation directe avec setObject
                p.setTotal(rs.getDouble("total"));
                p.setEtat(rs.getString("etat"));
                paniers.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des paniers : " + ex.getMessage());
            throw ex;
        }
        return paniers;
    }
}
