package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import entite.Publicite;
import tools.DataSource;

public abstract class PubliciteService implements IService<Publicite> {

    private Connection cnx;

    public PubliciteService() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public boolean ajouter(Publicite publicite) {
        String query = "INSERT INTO publicite (titre, description, media_url, date, statut, id_annonceur) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, publicite.getTitre());
            ps.setString(2, publicite.getDescription());
            ps.setString(3, publicite.getMedia_url());
            ps.setDate(4, java.sql.Date.valueOf(publicite.getDate()));  // Conversion correcte de LocalDate
            ps.setString(5, publicite.getStatut());
            ps.setInt(6, publicite.getId_annonceur());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void modifier(Publicite publicite) {
        String query = "UPDATE publicite SET titre = ?, description = ?, media_url = ?, date = ?, statut = ?, id_annonceur = ? WHERE id_publicite = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, publicite.getTitre());
            ps.setString(2, publicite.getDescription());
            ps.setString(3, publicite.getMedia_url());
            ps.setDate(4, java.sql.Date.valueOf(publicite.getDate()));  // Correction ici aussi
            ps.setString(5, publicite.getStatut());
            ps.setInt(6, publicite.getId_annonceur());
            ps.setInt(7, publicite.getId_publicite());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean supprimer(Publicite id) {
        String query = "DELETE FROM publicite WHERE id_publicite = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Publicite getOne(Publicite publicite) {
        String query = "SELECT * FROM publicite WHERE id_publicite = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, publicite.getId_publicite());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Publicite(
                        rs.getInt("id_publicite"),
                        rs.getString("titre"),
                        rs.getString("description"),
                        rs.getString("media_url"),
                        rs.getDate("date").toLocalDate(),  // Conversion correcte
                        rs.getString("statut"),
                        rs.getInt("id_annonceur")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Publicite> getAll() {
        String query = "SELECT * FROM publicite";
        List<Publicite> publicites = new ArrayList<>();
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Publicite publicite = new Publicite(
                        rs.getInt("id_publicite"),
                        rs.getString("titre"),
                        rs.getString("description"),
                        rs.getString("media_url"),
                        rs.getDate("date").toLocalDate(),  // Conversion correcte
                        rs.getString("statut"),
                        rs.getInt("id_annonceur")
                );
                publicites.add(publicite);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return publicites;
    }

    public abstract void ajouterPublicite(String titre, String description, String mediaUrl, Date date, String statut, int idAnnonceur);

    public abstract List<Publicite> getAllPublicites();
}
