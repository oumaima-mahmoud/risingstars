package services;

import entite.Publicite;
import tools.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PubliciteService implements IService<Publicite> {

    private Connection cnx;

    public PubliciteService() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Publicite publicite) {
        String query = "INSERT INTO publicite (titre, description, media_url, date, statut, id_offre) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, publicite.getTitre());
            ps.setString(2, publicite.getDescription());
            ps.setString(3, publicite.getMedia_url());
            ps.setDate(4, Date.valueOf(publicite.getDate())); // If 'date' is LocalDate
            ps.setString(5, publicite.getStatut());
            ps.setInt(6, publicite.getId_offre()); // Correctly assign id_offre
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifier(Publicite publicite) {
        String query = "UPDATE publicite SET titre = ?, description = ?, media_url = ?, date = ?, statut = ?, id_offre = ? WHERE id_publicite = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, publicite.getTitre());
            ps.setString(2, publicite.getDescription());
            ps.setString(3, publicite.getMedia_url());
            ps.setDate(4, Date.valueOf(publicite.getDate())); // If 'date' is LocalDate
            ps.setString(5, publicite.getStatut());
            ps.setInt(6, publicite.getId_offre()); // Ensure id_offre is correctly passed
            ps.setInt(7, publicite.getId_publicite());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(int id) {
        String query = "DELETE FROM publicite WHERE id_publicite = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Publicite getOne(Publicite publicite) {
        String query = "SELECT * FROM publicite WHERE id_publicite = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, publicite.getId_publicite());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Publicite(
                            rs.getInt("id_publicite"),
                            rs.getString("titre"),
                            rs.getString("description"),
                            rs.getString("media_url"),
                            rs.getString("date"),
                            rs.getString("statut"),
                            rs.getInt("id_offre")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Publicite> getAll(Publicite publicite) {
        String query = "SELECT * FROM publicite";
        List<Publicite> publicites = new ArrayList<>();
        try (PreparedStatement ps = cnx.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Publicite p = new Publicite(
                        rs.getInt("id_publicite"),
                        rs.getString("titre"),
                        rs.getString("description"),
                        rs.getString("media_url"),
                        rs.getString("date"),
                        rs.getString("statut"),
                        rs.getInt("id_offre")
                );
                publicites.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return publicites;
    }

    // Adding the getById method
    public Publicite getById(int id) {
        String query = "SELECT * FROM publicite WHERE id_publicite = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Publicite(
                            rs.getInt("id_publicite"),
                            rs.getString("titre"),
                            rs.getString("description"),
                            rs.getString("media_url"),
                            rs.getString("date"),
                            rs.getString("statut"),
                            rs.getInt("id_offre")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Optionally, you could add a method to retrieve publicites by advertiser ID (id_offre)
    public List<Publicite> getByAnnonceur(int id_annonceur) {
        String query = "SELECT * FROM publicite WHERE id_offre = ?";
        List<Publicite> publicites = new ArrayList<>();
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id_annonceur);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Publicite p = new Publicite(
                            rs.getInt("id_publicite"),
                            rs.getString("titre"),
                            rs.getString("description"),
                            rs.getString("media_url"),
                            rs.getString("date"),
                            rs.getString("statut"),
                            rs.getInt("id_offre")
                    );
                    publicites.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return publicites;
    }
}
