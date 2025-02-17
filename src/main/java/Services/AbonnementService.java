package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Abonnement;
import entities.StatutAbonnement; // Assurez-vous d'importer l'enum StatutAbonnement
import tools.DataSource;

public class AbonnementService implements IService<Abonnement> {
    private Connection cnx;

    public AbonnementService() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Abonnement abonnement) {
        String query = "INSERT INTO abonnement (type_abonnement, date_debut, date_fin, tarif, statut, point_fidelite, id_user) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, abonnement.getTypeAbonnement());
            ps.setDate(2, new java.sql.Date(abonnement.getDateDebut().getTime()));
            ps.setDate(3, new java.sql.Date(abonnement.getDateFin().getTime()));
            ps.setDouble(4, abonnement.getTarif());
            ps.setString(5, abonnement.getStatut().name()); // Utilisation de name() pour obtenir la valeur de l'enum
            ps.setInt(6, abonnement.getPointFidelite());
            ps.setInt(7, abonnement.getIdUser());
            ps.executeUpdate();
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
            ps.setString(5, abonnement.getStatut().name()); // Utilisation de name() pour obtenir la valeur de l'enum
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
    public Abonnement getOne(Abonnement abonnement) {
        return null;
    }

    @Override
    public List<Abonnement> getAll(Abonnement abonnement) {
        return List.of();
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
                        StatutAbonnement.valueOf(rs.getString("statut")), // Conversion de String à StatutAbonnement
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
        try {
            String query = "SELECT * FROM abonnement";
            PreparedStatement pst = cnx.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Abonnement abn = new Abonnement(
                        rs.getInt("id_abonnement"),
                        rs.getString("type_abonnement"),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
                        rs.getDouble("tarif"),
                        StatutAbonnement.valueOf(rs.getString("statut")), // Conversion de String à StatutAbonnement
                        rs.getInt("point_fidelite"),
                        rs.getInt("id_user")
                );
                abonnements.add(abn);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des abonnements : " + ex.getMessage());
        }
        return abonnements;
    }
}