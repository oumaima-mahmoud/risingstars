package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Reservation;
import entities.StatutRes;
import tools.DataSource;

public class ReservationService implements IService<Reservation> {
    private Connection cnx;

    public ReservationService() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Reservation reservation) {
        String query = "INSERT INTO reservation (place, utilisationID, dateReservation, statut, prixTotal) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, reservation.getPlace());
            ps.setInt(2, reservation.getUtilisationID());
            ps.setDate(3, new java.sql.Date(reservation.getDateReservation().getTime()));
            ps.setString(4, reservation.getStatut().toString());
            ps.setFloat(5, reservation.getPrixTotal());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifier(Reservation reservation) {
        String query = "UPDATE reservation SET place = ?, utilisationID = ?, dateReservation = ?, statut = ?, prixTotal = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, reservation.getPlace());
            ps.setInt(2, reservation.getUtilisationID());
            ps.setDate(3, new java.sql.Date(reservation.getDateReservation().getTime()));
            ps.setString(4, reservation.getStatut().toString());
            ps.setFloat(5, reservation.getPrixTotal());
            ps.setInt(6, reservation.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(int id) {
        String query = "DELETE FROM reservation WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reservation getOne(Reservation reservation) {
        String query = "SELECT * FROM reservation WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, reservation.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Reservation(
                        rs.getInt("id"),
                        rs.getInt("place"),
                        rs.getInt("utilisationID"),
                        rs.getDate("dateReservation"),
                        StatutRes.valueOf(rs.getString("statut")),
                        rs.getFloat("prixTotal")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reservation> getAll(Reservation reservation) {
        List<Reservation> reservations = new ArrayList<>();
        try {
            String query = "SELECT * FROM reservation";
            PreparedStatement pst = cnx.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Reservation res = new Reservation(
                        rs.getInt("id"),
                        rs.getInt("place"),
                        rs.getInt("utilisationID"),
                        rs.getDate("dateReservation"),
                        StatutRes.valueOf(rs.getString("statut")),
                        rs.getFloat("prixTotal")
                );
                reservations.add(res);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des réservations : " + ex.getMessage());
        }
        return reservations;
    }
}