package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import entite.Offre;
import tools.DataSource;

public abstract class OffreService implements IService<Offre> {

    private Connection cnx;

    public OffreService() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public boolean ajouter(Offre offre) {
        String query = "INSERT INTO offre (montant, conditions, date_proposition, contact, id_sponsor, id_publicite) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDouble(1, offre.getMontant());
            ps.setString(2, offre.getConditions());
            ps.setTimestamp(3, Timestamp.valueOf(offre.getDate_proposition()));  // Convertir LocalDateTime en Timestamp
            ps.setString(4, offre.getContact());
            ps.setInt(5, offre.getId_sponsor());
            ps.setInt(6, offre.getId_publicite());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    offre.setId_offre(generatedKeys.getInt(1));  // Récupérer l'ID auto-généré
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'offre : " + e.getMessage());
        }
        return false;
    }

    @Override
    public void modifier(Offre offre) {
        String query = "UPDATE offre SET montant = ?, conditions = ?, date_proposition = ?, contact = ?, id_sponsor = ?, id_publicite = ? WHERE id_offre = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setDouble(1, offre.getMontant());
            ps.setString(2, offre.getConditions());
            ps.setTimestamp(3, Timestamp.valueOf(offre.getDate_proposition()));  // Conversion LocalDateTime -> Timestamp
            ps.setString(4, offre.getContact());
            ps.setInt(5, offre.getId_sponsor());
            ps.setInt(6, offre.getId_publicite());
            ps.setInt(7, offre.getId_offre());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification de l'offre : " + e.getMessage());
        }
    }

    public boolean supprimer(Offre id) {
        String query = "DELETE FROM offre WHERE id_offre = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'offre : " + e.getMessage());
        }
        return false;
    }

    @Override
    public Offre getOne(int id) {  // Changer pour prendre un ID au lieu d'un objet Offre
        String query = "SELECT * FROM offre WHERE id_offre = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Offre(
                        rs.getInt("id_offre"),
                        rs.getInt("id_publicite"),
                        rs.getInt("id_sponsor"),
                        rs.getDouble("montant"),
                        rs.getString("conditions"),
                        rs.getTimestamp("date_proposition").toLocalDateTime(),  // Conversion Timestamp -> LocalDateTime
                        rs.getString("contact")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'offre : " + e.getMessage());
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
                        rs.getInt("id_publicite"),
                        rs.getInt("id_sponsor"),
                        rs.getDouble("montant"),
                        rs.getString("conditions"),
                        rs.getTimestamp("date_proposition").toLocalDateTime(),  // Conversion Timestamp -> LocalDateTime
                        rs.getString("contact")
                );
                offres.add(offre);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des offres : " + e.getMessage());
        }
        return offres;
    }
}
