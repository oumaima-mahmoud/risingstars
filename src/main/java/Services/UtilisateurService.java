package Services;

import entities.Role;
import entities.StatutAbonnement;
import entities.TypeAbonnement;
import entities.Utilisateur;
import tools.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurService implements IService<Utilisateur> {
    private final Connection cnx = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Utilisateur utilisateur) throws SQLException {
        String query = "INSERT INTO utilisateur (nom, prenom, email, motdepasse, role, numeroTelephone, statutAbonnement, typeAbonnement, pointsFidelite) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, utilisateur.getNom());
            ps.setString(2, utilisateur.getPrenom());
            ps.setString(3, utilisateur.getEmail());
            ps.setString(4, utilisateur.getMotDePasse());
            ps.setString(5, utilisateur.getRole().name());
            ps.setString(6, utilisateur.getNumeroTelephone());
            ps.setString(7, utilisateur.getStatutAbonnement().name());
            ps.setString(8, utilisateur.getTypeAbonnement().name());
            ps.setInt(9, utilisateur.getPointsFidelite());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    utilisateur.setIdUser(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public void modifier(Utilisateur utilisateur) throws SQLException {
        String query = "UPDATE utilisateur SET nom=?, prenom=?, email=?, motdepasse=?, role=?, numeroTelephone=?, statutAbonnement=?, typeAbonnement=?, pointsFidelite=? WHERE id_user=?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, utilisateur.getNom());
            ps.setString(2, utilisateur.getPrenom());
            ps.setString(3, utilisateur.getEmail());
            ps.setString(4, utilisateur.getMotDePasse());
            ps.setString(5, utilisateur.getRole().name());
            ps.setString(6, utilisateur.getNumeroTelephone());
            ps.setString(7, utilisateur.getStatutAbonnement().name());
            ps.setString(8, utilisateur.getTypeAbonnement().name());
            ps.setInt(9, utilisateur.getPointsFidelite());
            ps.setInt(10, utilisateur.getIdUser());
            ps.executeUpdate();
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String query = "DELETE FROM utilisateur WHERE id_user=?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Utilisateur getOne(int id) throws SQLException {
        String query = "SELECT * FROM utilisateur WHERE id_user=?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Utilisateur(
                        rs.getInt("id_user"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("motdepasse"),
                        Role.fromString(rs.getString("role")),
                        rs.getString("numeroTelephone"),
                        StatutAbonnement.valueOf(rs.getString("statutAbonnement").toUpperCase()),
                        TypeAbonnement.fromString(rs.getString("typeAbonnement")),
                        rs.getInt("pointsFidelite")
                );
            }
        }
        return null;
    }

    public Utilisateur getUserByEmail(String email) throws SQLException {
        String query = "SELECT * FROM utilisateur WHERE email = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Utilisateur(
                        rs.getInt("id_user"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("motdepasse"),
                        Role.fromString(rs.getString("role")),
                        rs.getString("numeroTelephone"),
                        StatutAbonnement.valueOf(rs.getString("statutAbonnement").toUpperCase()),
                        TypeAbonnement.fromString(rs.getString("typeAbonnement")),
                        rs.getInt("pointsFidelite")
                );
            }
        }
        return null;
    }

    @Override
    public List<Utilisateur> getAll() throws SQLException {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String query = "SELECT * FROM utilisateur";
        try (PreparedStatement ps = cnx.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                utilisateurs.add(new Utilisateur(
                        rs.getInt("id_user"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("motdepasse"),
                        Role.fromString(rs.getString("role")),
                        rs.getString("numeroTelephone"),
                        StatutAbonnement.valueOf(rs.getString("statutAbonnement").toUpperCase()),
                        TypeAbonnement.fromString(rs.getString("typeAbonnement")),
                        rs.getInt("pointsFidelite")
                ));
            }
        }
        return utilisateurs;
    }

    public boolean checkIfUserExists(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM utilisateur WHERE email = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    public boolean authenticate(String email, String password, String role) throws SQLException {
        String query = "SELECT id_user FROM utilisateur WHERE email = ? AND motdepasse = ? AND role = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, role);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public List<Utilisateur> searchUsersByName(String searchText) throws SQLException {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String query = "SELECT * FROM utilisateur WHERE nom LIKE ? OR prenom LIKE ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, "%" + searchText + "%");
            ps.setString(2, "%" + searchText + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                utilisateurs.add(new Utilisateur(
                        rs.getInt("id_user"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("motdepasse"),
                        Role.fromString(rs.getString("role")),
                        rs.getString("numeroTelephone"),
                        StatutAbonnement.valueOf(rs.getString("statutAbonnement").toUpperCase()),
                        TypeAbonnement.fromString(rs.getString("typeAbonnement")),
                        rs.getInt("pointsFidelite")
                ));
            }
        }
        return utilisateurs;
    }
}
