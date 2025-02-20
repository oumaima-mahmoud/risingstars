package gestion_satde.services;
import gestion_satde.entities.Stade;
import gestion_satde.entities.evenement;
import gestion_satde.tools.DataSource;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;


public class serviceEvenement implements Iservice <evenement> {
    Connection cnx ;
    PreparedStatement pst;
    public serviceEvenement(){
        this.cnx= DataSource.getInstance().getConnection();
    }
    public void ajouter(evenement e){
        String req = "INSERT INTO evenement (nom, type, date,id_stade,organisateur,nombre_participant) VALUES (?, ?, ?,?,?,?)";

        try  {
            pst= cnx.prepareStatement(req);
            pst.setString(1, e.getNom());
            pst.setString(2, e.getType());
            pst.setDate(3, java.sql.Date.valueOf(e.getDate())); // Convert LocalDate to java.sql.Date
            pst.setInt(4, e.getId_stade());
            pst.setString(5, e.getOraganisateur());
            pst.setDouble(6, e.getNombre_participant());

            pst.executeUpdate();
            System.out.println("evenement ajouté avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout du evenement : " + ex.getMessage());
        }
    }

    @Override
    public void modifier(evenement e, int id) {
        String req = "UPDATE evenement set nom=?, type=?, date=? ,id_stade=? ,organisateur=? ,nombre_participant=? where id_evenement=? ";

        try  {
            pst= cnx.prepareStatement(req);
            pst.setString(1, e.getNom());
            pst.setString(2, e.getType());
            pst.setDate(3, java.sql.Date.valueOf(e.getDate())); // Convert LocalDate to java.sql.Date
            pst.setInt(4, e.getId_stade());
            pst.setString(5, e.getOraganisateur());
            pst.setDouble(6, e.getNombre_participant());
            pst.setInt(7, id);

            pst.executeUpdate();
            System.out.println("evenement updated avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la modification du evenement : " + ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM evenement WHERE id_evenement=?";
        try {
            pst= cnx.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("evenement supprimer avec succès !");
        }
        catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression du evenement : " + ex.getMessage());
        }


    }

    @Override
    public evenement getOne(int id) {
        String req = "SELECT * FROM evenement WHERE id=?";
        evenement e = null;

        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    e = new evenement();
                    e.setId(rs.getInt("id"));
                    e.setNom(rs.getString("nom"));
                    e.setType(rs.getString("type"));  // Assuming 'capacite' is the correct column name
                    Date sqlDate = rs.getDate("date");
                    //e.setDate(rs.getDate("date"));
                    e.setOraganisateur(rs.getString("organisateur"));
                    e.setNombre_participant(rs.getDouble("nombre_participant"));
                    e.setId_stade(rs.getInt("id_stade"));

                }
            }
        } catch (SQLException ex) {
            System.out.println("Error fetching data: " + ex.getMessage());
        }

        return e;
    }

    @Override
    public List<evenement> getAll() {
        String req = "SELECT * FROM evenement";  // No dynamic input, so no need for placeholders
        List<evenement> evenements = new ArrayList<>();

        try
        {
            pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                evenement e = new evenement();
                e.setId(rs.getInt("id_evenement"));
                e.setNom(rs.getString("nom"));
                e.setType(rs.getString("type"));  // Assuming 'capacite' is the correct column name
                Date sqlDate = rs.getDate("date");
                e.setId_stade(rs.getInt("id_stade"));
                e.setOraganisateur(rs.getString("organisateur"));
                e.setNombre_participant(rs.getDouble("nombre_participant"));


                evenements.add(e);
            }
        } catch (SQLException ex) {
            System.out.println("Error fetching data: " + ex.getMessage());
        }

        return evenements;
    }
}
