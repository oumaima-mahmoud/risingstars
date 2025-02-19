package Services;

import entities.Utilisateur;
import tools.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurService implements IService<Utilisateur> {
    private final Connection cnx = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Utilisateur utilisateur) {
        String query = "INSERT INTO utilisateur (nom, prenom, email, motdepasse) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, utilisateur.getNom());
            ps.setString(2, utilisateur.getPrenom());
            ps.setString(3, utilisateur.getEmail());
            ps.setString(4, utilisateur.getMotDePasse());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    utilisateur.setIdUser(rs.getInt(1)); // Correspond à l'auto-increment
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    @Override
    public void modifier(Utilisateur utilisateur) {
        String query = "UPDATE utilisateur SET nom=?, prenom=?, email=?, motdepasse=? WHERE id_user=?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, utilisateur.getNom());
            ps.setString(2, utilisateur.getPrenom());
            ps.setString(3, utilisateur.getEmail());
            ps.setString(4, utilisateur.getMotDePasse());
            ps.setInt(5, utilisateur.getIdUser());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification : " + e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String query = "DELETE FROM utilisateur WHERE id_user=?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    @Override
    public Utilisateur getOne(int id) {
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
                        rs.getString("motdepasse")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Utilisateur> getAll() {
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
                        rs.getString("motdepasse")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération : " + e.getMessage());
        }
        return utilisateurs;
    }

    // Méthode pour vider la table (utile pour les tests)
    public void viderTable() {
        String query = "DELETE FROM utilisateur";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors du vidage : " + e.getMessage());
        }
    }

    public boolean authenticate(String email, String password, String role) {
        String query = "SELECT id_user FROM utilisateur WHERE email = ? AND motdepasse = ? AND role = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, role);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Retourne true si un utilisateur correspondant est trouvé
            }
        } catch (SQLException e) {
            System.err.println("Erreur d'authentification : " + e.getMessage());
            return false;
        }
    }

    public String getAbonnementsByUserId(int userId) {
    }
}