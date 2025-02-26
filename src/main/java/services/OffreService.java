package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entite.Offre;
import tools.DataSource;

public class OffreService implements IService<Offre> {

    private Connection cnx;

    public OffreService() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Offre offre) {
        String query = "INSERT INTO offre (montant, conditions, date_proposition, contact, id_user) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setDouble(1, offre.getMontant());
            ps.setString(2, offre.getConditions());
            ps.setString(3, offre.getDate_proposition());
            ps.setString(4, offre.getContact());
            ps.setInt(5, offre.getId_user());
            ps.executeUpdate();  // This will execute the query and add the Offre

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifier(Offre updatedOffre) {
        String query = "UPDATE offre SET montant = ?, conditions = ?, contact = ?, date_proposition = ?, id_user = ? WHERE id_offre = ?";

        try (PreparedStatement ps = cnx.prepareStatement(query)) {

            ps.setDouble(1, updatedOffre.getMontant());
            ps.setString(2, updatedOffre.getConditions());
            ps.setString(3, updatedOffre.getContact());
            ps.setString(4, updatedOffre.getDate_proposition()); // Check for null
            ps.setInt(5, updatedOffre.getId_user());
            ps.setInt(6, updatedOffre.getId_offre()); // Where clause

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Or handle exception
        }
    }


    public void supprimer(int id) {
        String query = "DELETE FROM offre WHERE id_offre = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Offre getOne(Offre offre) {
        String query = "SELECT * FROM offre WHERE id_offre = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, offre.getId_offre());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Offre(
                            rs.getInt("id_offre"),
                            rs.getInt("id_user"),
                            rs.getDouble("montant"),
                            rs.getString("conditions"),
                            rs.getString("date_proposition"),
                            rs.getString("contact")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Offre> getAll(Offre offre) {
        String query = "SELECT * FROM offre";
        List<Offre> offres = new ArrayList<>();
        try (PreparedStatement ps = cnx.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Offre o = new Offre(
                        rs.getInt("id_offre"),
                        rs.getInt("id_user"),
                        rs.getDouble("montant"),
                        rs.getString("conditions"),
                        rs.getString("date_proposition"),
                        rs.getString("contact")
                );
                offres.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offres;
    }

    public void close() {
        if (cnx != null) {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
