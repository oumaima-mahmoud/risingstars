package Services;

import entities.Abonnement;
import entities.StatutAbonnement;
import tools.DataSource;

import java.sql.*;
import java.util.List;

public class AbonnementService implements IService<Abonnement> {
    private final Connection cnx = DataSource.getInstance().getConnection();

    // Méthode pour obtenir un abonnement par ID utilisateur
    public Abonnement getAbonnementByUserId(int userId) {
        String query = "SELECT * FROM abonnement WHERE id_user=?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Abonnement(
                        rs.getInt("id_abonnement"),
                        rs.getString("type_abonnement"),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
                        rs.getString("tarif"), // Récupérer comme String
                        StatutAbonnement.valueOf(rs.getString("statut")),
                        rs.getInt("point_fidelite"),
                        rs.getInt("id_user")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Aucun abonnement trouvé
    }

    @Override
    public void ajouter(Abonnement abonnement) throws SQLException {

    }

    @Override
    public void modifier(Abonnement abonnement) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    @Override
    public Abonnement getOne(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Abonnement> getAll() throws SQLException {
        return List.of();
    }

    // Autres méthodes de service...
}
