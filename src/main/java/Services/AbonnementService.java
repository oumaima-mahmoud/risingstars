package Services;

import entities.Abonnement;
import entities.StatutAbonnement;
import tools.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbonnementService implements IService<Abonnement> {
    private final Connection cnx = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Abonnement abonnement) {
        String query = "INSERT INTO abonnement (type_abonnement, date_debut, date_fin, tarif, statut, point_fidelite, id_user) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, abonnement.getTypeAbonnement());
            ps.setDate(2, new java.sql.Date(abonnement.getDateDebut().getTime()));
            ps.setDate(3, new java.sql.Date(abonnement.getDateFin().getTime()));
            ps.setDouble(4, abonnement.getTarif());
            ps.setString(5, abonnement.getStatut().name());
            ps.setInt(6, abonnement.getPointFidelite());
            ps.setInt(7, abonnement.getIdUser());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    abonnement.setIdAbonnement(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifier(Abonnement abonnement) {
        String query = "UPDATE abonnement SET type_abonnement=?, date_debut=?, date_fin=?, tarif=?, statut=?, point_fidelite=?, id_user=? WHERE id_abonnement=?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, abonnement.getTypeAbonnement());
            ps.setDate(2, new java.sql.Date(abonnement.getDateDebut().getTime()));
            ps.setDate(3, new java.sql.Date(abonnement.getDateFin().getTime()));
            ps.setDouble(4, abonnement.getTarif());
            ps.setString(5, abonnement.getStatut().name());
            ps.setInt(6, abonnement.getPointFidelite());
            ps.setInt(7, abonnement.getIdUser());
            ps.setInt(8, abonnement.getIdAbonnement());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(int id) {
        String query = "DELETE FROM abonnement WHERE id_abonnement=?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Abonnement getOne(int id) {
        String query = "SELECT * FROM abonnement WHERE id_abonnement=?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Abonnement(
                        rs.getInt("id_abonnement"),
                        rs.getString("type_abonnement"),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
                        rs.getDouble("tarif"),
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
    public List<Abonnement> getAll() {
        List<Abonnement> abonnements = new ArrayList<>();
        String query = "SELECT * FROM abonnement";
        try (PreparedStatement ps = cnx.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                abonnements.add(new Abonnement(
                        rs.getInt("id_abonnement"),
                        rs.getString("type_abonnement"),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
                        rs.getDouble("tarif"),
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