/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import entite.Reclamation;
import tools.DataSource;

/**
 *
 * @author abdelazizmezri
 */
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifier(Reclamation reclamation) {
        String query = "UPDATE reclamation SET user_id = ?, type = ?, description = ?, objet = ?, etat = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, reclamation.getUserId());
            ps.setString(2, reclamation.getType());
            ps.setString(3, reclamation.getDescription());
            ps.setString(4, reclamation.getObjet());
            ps.setString(5, reclamation.getEtat());
            ps.setInt(6, reclamation.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(int id) {
        String query = "DELETE FROM reclamation WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reclamation> getAll(Reclamation reclamation) {
        List<Reclamation> reclamations = new ArrayList<>();
        try {
            String query = "SELECT * FROM reclamation";
            PreparedStatement pst = cnx.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                // Assurez-vous de récupérer l'ID de la réclamation
                Reclamation rec = new Reclamation(
                        rs.getInt("user_id"),   // userId
                        rs.getString("type"),    // type
                        rs.getString("description"), // description
                        rs.getString("objet"),   // objet
                        rs.getString("etat")     // etat
                );
                rec.setId(rs.getInt("id"));  // Assurez-vous que l'ID est bien récupéré ici
                rec.setDateReclamation(rs.getString("date_reclamation"));
                reclamations.add(rec);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des réclamations : " + ex.getMessage());
        }
        return reclamations;
    }

}
