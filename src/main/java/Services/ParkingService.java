package Services;

import entities.Parking;
import tools.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParkingService {
    private Connection connection;

    public ParkingService() {
        this.connection = DataSource.getInstance().getConnection();
    }

    // 🟢 Ajouter une place de parking
    public void ajouterParking(Parking parking) throws SQLException {
        String query = "INSERT INTO parking (reservation_id, nbr_place, statut, date_reservation, heure_debut, heure_fin, prix) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, parking.getReservationId());
        ps.setInt(2, parking.getNbrPlace());
        ps.setString(3, parking.getStatut());
        ps.setDate(4, new java.sql.Date(parking.getDateReservation().getTime()));
        ps.setString(5, parking.getHeureDebut());
        ps.setString(6, parking.getHeureFin());
        ps.setDouble(7, parking.getPrix());
        ps.executeUpdate();
    }

    // ✅ Métier avancé : Vérifier la disponibilité des places de parking
    public boolean verifierDisponibilite(int reservationId) throws SQLException {
        String query = "SELECT COUNT(*) AS total FROM parking WHERE reservation_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, reservationId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("total") > 0;
        }
        return false;
    }
}
