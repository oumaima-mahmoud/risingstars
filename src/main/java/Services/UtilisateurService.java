package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Services.IService;
import entities.Utilisateur;
import tools.DataSource;

public class UtilisateurService implements IService<Utilisateur> {
    private Connection cnx;

    public UtilisateurService() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Utilisateur utilisateur) {
        String query = "INSERT INTO utilisateur (nom, prenom, email, motDePasse) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, utilisateur.getNom());
            ps.setString(2, utilisateur.getPrenom());
            ps.setString(3, utilisateur.getEmail());
            ps.setString(4, utilisateur.getMotDePasse());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifier(Utilisateur utilisateur) {
        String query = "UPDATE utilisateur SET nom = ?, prenom = ?, email = ?, motDePasse = ? WHERE idUser = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, utilisateur.getNom());
            ps.setString(2, utilisateur.getPrenom());
            ps.setString(3, utilisateur.getEmail());
            ps.setString(4, utilisateur.getMotDePasse());
            ps.setInt(5, utilisateur.getIdUser());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(int id) {
        String query = "DELETE FROM utilisateur WHERE idUser = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Utilisateur getOne(Utilisateur utilisateur) {
        String query = "SELECT * FROM utilisateur WHERE idUser = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, utilisateur.getIdUser());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Utilisateur(
                        rs.getInt("idUser"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("motDePasse")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Utilisateur> getAll(Utilisateur utilisateur) {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        try {
            String query = "SELECT * FROM utilisateur";
            PreparedStatement pst = cnx.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Utilisateur user = new Utilisateur(
                        rs.getInt("idUser"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("motDePasse")
                );
                utilisateurs.add(user);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des utilisateurs : " + ex.getMessage());
        }
        return utilisateurs;
    }
}