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
        String query = "INSERT INTO offre (montant, conditions, date_proposition, contact, id_sponsor, id_publicite) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setDouble(1, offre.getMontant());
            ps.setString(2, offre.getConditions());
            ps.setString(3, offre.getDate_proposition());
            ps.setString(4, offre.getContact());
            ps.setInt(5, offre.getId_sponsor());
            ps.setInt(6, offre.getId_publicite());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifier(Offre offre) {
        String query = "UPDATE offre SET montant = ?, conditions = ?, date_proposition = ?, contact = ?, id_sponsor = ?, id_publicite = ? WHERE id_offre = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setDouble(1, offre.getMontant());
            ps.setString(2, offre.getConditions());
            ps.setString(3, offre.getDate_proposition());
            ps.setString(4, offre.getContact());
            ps.setInt(5, offre.getId_sponsor());
            ps.setInt(6, offre.getId_publicite());
            ps.setInt(7, offre.getId_offre());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
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
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Offre(
                        rs.getInt("id_offre"),
                        rs.getDouble("montant"),
                        rs.getString("conditions"),
                        rs.getString("date_proposition"),
                        rs.getString("contact"),
                        rs.getInt("id_sponsor"),
                        rs.getInt("id_publicite")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Offre> getAll() {
        String query = "SELECT * FROM offre";
        List<Offre> offres = new ArrayList<>();
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Offre offre = new Offre(
                        rs.getInt("id_offre"),
                        rs.getDouble("montant"),
                        rs.getString("conditions"),
                        rs.getString("date_proposition"),
                        rs.getString("contact"),
                        rs.getInt("id_sponsor"),
                        rs.getInt("id_publicite")
                );
                offres.add(offre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offres;
    }
}