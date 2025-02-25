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
    public void ajouter(Utilisateur utilisateur) {
        String query = "INSERT INTO utilisateur (nom, prenom, email, motdepasse, role, numeroTelephone, statutAbonnement, typeAbonnement, pointsFidelite) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, utilisateur.getNom());
            ps.setString(2, utilisateur.getPrenom());
            ps.setString(3, utilisateur.getEmail());
            ps.setString(4, utilisateur.getMotDePasse());
            ps.setString(5, utilisateur.getRole().name()); // Saving Role as a string (enum name)
            ps.setString(6, utilisateur.getNumeroTelephone());
            ps.setString(7, utilisateur.getStatutAbonnement().name()); // StatutAbonnement enum converted to string
            ps.setString(8, utilisateur.getTypeAbonnement().name()); // TypeAbonnement enum converted to string
            ps.setInt(9, utilisateur.getPointsFidelite());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    utilisateur.setIdUser(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during user creation: " + e.getMessage());
        }
    }

    @Override
    public void modifier(Utilisateur utilisateur) {
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
        } catch (SQLException e) {
            System.err.println("Error during user update: " + e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String query = "DELETE FROM utilisateur WHERE id_user=?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error during user deletion: " + e.getMessage());
        }
    }

    @Override
    public Utilisateur getOne(int id) {
        String query = "SELECT u.*, a.statut FROM utilisateur u LEFT JOIN abonnement a ON u.id_user = a.id_user WHERE u.id_user=?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String roleString = rs.getString("role");
                Role role = Role.valueOf(roleString.toUpperCase()); // Convert string role to Role enum

                // Fetching statut from the abonnement table
                String statutString = rs.getString("statut");
                StatutAbonnement statutAbonnement = StatutAbonnement.valueOf(statutString.toUpperCase()); // Convert statut string to enum

                String typeAbonnementString = rs.getString("typeAbonnement");
                TypeAbonnement typeAbonnement = TypeAbonnement.valueOf(typeAbonnementString.toUpperCase()); // Convert typeAbonnement string to enum

                return new Utilisateur(
                        rs.getInt("id_user"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("motdepasse"),
                        role,
                        rs.getString("numeroTelephone"),
                        statutAbonnement,
                        typeAbonnement,
                        rs.getInt("pointsFidelite")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error during user retrieval: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Utilisateur> getAll() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String query = "SELECT u.*, a.statut FROM utilisateur u LEFT JOIN abonnement a ON u.id_user = a.id_user";
        try (PreparedStatement ps = cnx.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String roleString = rs.getString("role");
                Role role = Role.valueOf(roleString.toUpperCase());

                String statutString = rs.getString("statut");
                StatutAbonnement statutAbonnement = StatutAbonnement.valueOf(statutString.toUpperCase());

                String typeAbonnementString = rs.getString("typeAbonnement");
                TypeAbonnement typeAbonnement = TypeAbonnement.valueOf(typeAbonnementString.toUpperCase());

                utilisateurs.add(new Utilisateur(
                        rs.getInt("id_user"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("motdepasse"),
                        role,
                        rs.getString("numeroTelephone"),
                        statutAbonnement,
                        typeAbonnement,
                        rs.getInt("pointsFidelite")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error during user retrieval: " + e.getMessage());
        }
        return utilisateurs;
    }

    // Method to check if a user exists based on email
    public boolean checkIfUserExists(String email) {
        String query = "SELECT COUNT(*) FROM utilisateur WHERE email = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // If count > 0, the user exists
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking if user exists: " + e.getMessage());
        }
        return false;
    }

    // Authenticate user by email, password, and role
    public boolean authenticate(String email, String password, String role) {
        String query = "SELECT id_user FROM utilisateur WHERE email = ? AND motdepasse = ? AND role = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, role);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Authentication error: " + e.getMessage());
            return false;
        }
    }
}
