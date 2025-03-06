package services;

import entite.Abonnement;
import entite.StatutAbonnement;
import entite.TypeAbonnement;
import tools.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbonnementService implements IService2<Abonnement> {
    private final Connection cnx = DataSource.getInstance().getConnection();

    public Abonnement getAbonnementByUserId(int userId) {
        String query = "SELECT * FROM abonnement WHERE id_user=?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Abonnement(
                        rs.getInt("id_abonnement"),
                        TypeAbonnement.valueOf(rs.getString("type_abonnement")),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
                        rs.getString("tarif"),
                        StatutAbonnement.valueOf(rs.getString("statut")),
                        rs.getInt("point_fidelite"),
                        rs.getInt("id_user")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void ajouter(Abonnement abonnement) throws SQLException {
        String query = "INSERT INTO abonnement (type_abonnement, date_debut, date_fin, tarif, statut, point_fidelite, id_user) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, abonnement.getTypeAbonnement().name());
            ps.setDate(2, new Date(abonnement.getDateDebut().getTime()));
            ps.setDate(3, new Date(abonnement.getDateFin().getTime()));
            ps.setString(4, abonnement.getTarif());
            ps.setString(5, abonnement.getStatut().name());
            ps.setInt(6, abonnement.getPointFidelite());
            ps.setInt(7, abonnement.getIdUser());
            ps.executeUpdate();
        }
    }

    @Override
    public void modifier(Abonnement abonnement) throws SQLException {
        String query = "UPDATE abonnement SET type_abonnement=?, date_debut=?, date_fin=?, tarif=?, statut=?, point_fidelite=? WHERE id_abonnement=?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, abonnement.getTypeAbonnement().name());
            ps.setDate(2, new Date(abonnement.getDateDebut().getTime()));
            ps.setDate(3, new Date(abonnement.getDateFin().getTime()));
            ps.setString(4, abonnement.getTarif());
            ps.setString(5, abonnement.getStatut().name());
            ps.setInt(6, abonnement.getPointFidelite());
            ps.setInt(7, abonnement.getIdAbonnement());
            ps.executeUpdate();
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String query = "DELETE FROM abonnement WHERE id_abonnement=?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Abonnement getOne(int id) throws SQLException {
        return null; // Implement if needed
    }

    @Override
    public List<Abonnement> getAll() throws SQLException {
        List<Abonnement> abonnements = new ArrayList<>();
        String query = "SELECT * FROM abonnement";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                abonnements.add(new Abonnement(
                        rs.getInt("id_abonnement"),
                        TypeAbonnement.valueOf(rs.getString("type_abonnement")),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
                        rs.getString("tarif"),
                        StatutAbonnement.valueOf(rs.getString("statut")),
                        rs.getInt("point_fidelite"),
                        rs.getInt("id_user")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return abonnements;
    }
}
