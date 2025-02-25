package Services;

import entities.Reservation;
import tools.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private Connection connection;

    public ReservationService() {
        this.connection = DataSource.getInstance().getConnection();
    }

    // 🟢 Ajouter une réservation
    public void ajouterReservation(Reservation reservation) throws SQLException {
        String query = "INSERT INTO reservation (user_id, place, date_reservation, statut, prix_total) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, reservation.getUserId());
        ps.setString(2, reservation.getPlace());
        ps.setDate(3, new java.sql.Date(reservation.getDateReservation().getTime()));
        ps.setString(4, reservation.getStatut());
        ps.setDouble(5, reservation.getPrixTotal());
        ps.executeUpdate();
    }

    // 🔴 Supprimer une réservation
    public void supprimerReservation(int id) throws SQLException {
        String query = "DELETE FROM reservation WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    // 🟡 Modifier une réservation
    public void modifierReservation(Reservation reservation) throws SQLException {
        String query = "UPDATE reservation SET place = ?, date_reservation = ?, statut = ?, prix_total = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, reservation.getPlace());
        ps.setDate(2, new java.sql.Date(reservation.getDateReservation().getTime()));
        ps.setString(3, reservation.getStatut());
        ps.setDouble(4, reservation.getPrixTotal());
        ps.setInt(5, reservation.getId());
        ps.executeUpdate();
    }

    // 🔍 Rechercher une réservation
    public Reservation chercherReservation(int id) throws SQLException {
        String query = "SELECT * FROM reservation WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Reservation(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("place"),
                    rs.getDate("date_reservation"),
                    rs.getString("statut"),
                    rs.getDouble("prix_total")
            );
        }
        return null;
    }

    // 📜 Récupérer toutes les réservations
    public List<Reservation> getAllReservations() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservation";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            reservations.add(new Reservation(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("place"),
                    rs.getDate("date_reservation"),
                    rs.getString("statut"),
                    rs.getDouble("prix_total")
            ));
        }
        return reservations;
    }

    // ✅ Métier avancé : Confirmer automatiquement une réservation
    public void confirmerReservation(int id) throws SQLException {
        String query = "UPDATE reservation SET statut = 'Confirmée' WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}
