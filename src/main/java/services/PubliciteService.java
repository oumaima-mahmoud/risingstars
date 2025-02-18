
package services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entite.Publicite;
import tools.DataSource;

public class PubliciteService implements IService<Publicite> {

    private Connection cnx;

    public PubliciteService() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Publicite publicite) {
        String query = "INSERT INTO publicite (titre, description, media_url, date, statut, id_annonceur) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, publicite.getTitre());
            ps.setString(2, publicite.getDescription());
            ps.setString(3, publicite.getMedia_url());
            ps.setString(4, publicite.getDate());
            ps.setString(5, publicite.getStatut());
            ps.setInt(6, publicite.getId_annonceur());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifier(Publicite publicite) {
        String query = "UPDATE publicite SET titre = ?, description = ?, media_url = ?, date = ?, statut = ?, id_annonceur = ? WHERE id_publicite = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, publicite.getTitre());
            ps.setString(2, publicite.getDescription());
            ps.setString(3, publicite.getMedia_url());
            ps.setString(4, publicite.getDate());
            ps.setString(5, publicite.getStatut());
            ps.setInt(6, publicite.getId_annonceur());
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
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Publicite(
                        rs.getInt("id_publicite"),
                        rs.getString("titre"),
                        rs.getString("description"),
                        rs.getString("media_url"),
                        rs.getString("date"),
                        rs.getString("statut"),
                        rs.getInt("id_annonceur")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

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
                        rs.getString("date"),
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
}