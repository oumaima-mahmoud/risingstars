package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import tools.DataSource;
import entite.Reponse;

public class ReponseService implements IService<Reponse> {
    private Connection cnx;

    public ReponseService() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Reponse reponse) {
        String query = "INSERT INTO reponse (id_reclamation, contenu, date_reponse) VALUES (?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, reponse.getReclamationId());
            ps.setString(2, reponse.getContenu());
            ps.setString(3, reponse.getDateReponse());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifier(Reponse reponse) {
        String query = "UPDATE reponse SET contenu = ?, date_reponse = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, reponse.getContenu());
            ps.setString(2, reponse.getDateReponse());
            ps.setInt(3, reponse.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(int id) {
        String query = "DELETE FROM reponse WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reponse getOne(Reponse reponse) {
        String query = "SELECT * FROM reponse WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, reponse.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Reponse(
                        rs.getInt("id_reclamation"),
                        rs.getString("contenu"),
                        rs.getString("date_reponse")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reponse> getAll(Reponse reponse) {
        List<Reponse> reponses = new ArrayList<>();
        String query = "SELECT * FROM reponse";
        try (Statement stmt = cnx.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                reponses.add(new Reponse(
                        rs.getInt("id_reclamation"),
                        rs.getString("contenu"),
                        rs.getString("date_reponse")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reponses;
    }
}
