package services;

import entite.Reservation;
import tools.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements IService<Reservation> {
    private Connection cnx;

    public ReservationService() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Reservation reservation) {
        String query = "INSERT INTO reservation (place, id_user, date_reservation, statut, prixTotal, id_ticket, id_parking) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, reservation.getPlace());
            ps.setInt(2, reservation.getIdUser());
            ps.setDate(3, Date.valueOf(reservation.getDateReservation()));
            ps.setString(4, reservation.getStatut());
            ps.setFloat(5, reservation.getPrixTotal());
            ps.setInt(6, reservation.getIdTicket());
            // Correctly handle nullable parking field:
            ps.setObject(7, (reservation.getIdParking() == null || reservation.getIdParking() == 0) ? null : reservation.getIdParking());

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();

            if (generatedKeys.next()) {
                int reservationId = generatedKeys.getInt(1);
                reservation.setId(reservationId); // Update the reservation with its generated ID
                String qrData = "Réservation ID: " + reservationId +
                        "\nTicket: " + reservation.getIdTicket() +
                        "\nPrix: " + reservation.getPrixTotal() + "DT";
                String filePath = "qr_codes/reservation_" + reservationId + ".png";
                QRCodeGenerator.generateQRCode(qrData, filePath);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de la réservation: " + e.getMessage());
        }
    }

    @Override
    public void modifier(Reservation reservation) {
        String query = "UPDATE reservation SET place = ?, id_user = ?, date_reservation = ?, statut = ?, prixTotal = ?, id_ticket = ?, id_parking = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, reservation.getPlace());
            ps.setInt(2, reservation.getIdUser());
            ps.setDate(3, Date.valueOf(reservation.getDateReservation()));
            ps.setString(4, reservation.getStatut());
            ps.setFloat(5, reservation.getPrixTotal());
            ps.setInt(6, reservation.getIdTicket());
            ps.setInt(7, reservation.getIdParking() == null ? 0 : reservation.getIdParking());
            ps.setLong(8, reservation.getId());
            ps.executeUpdate();
            System.out.println("Réservation modifiée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification : " + e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String query = "DELETE FROM reservation WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Réservation supprimée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    @Override
    public Reservation getOne(Reservation reservation) {
        String query = "SELECT * FROM reservation WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setLong(1, reservation.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Reservation(
                        rs.getLong("id"),
                        rs.getInt("place"),
                        rs.getInt("id_user"),
                        rs.getDate("date_reservation").toLocalDate(),
                        rs.getString("statut"),
                        rs.getFloat("prixTotal"),
                        rs.getInt("id_ticket"),
                        rs.getInt("id_parking")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Reservation> getAll(Reservation reservation) {
        return null;
    }

    public List<Reservation> getAlll() {
        List<Reservation> reservations = new ArrayList<>();
        try {
            String query = "SELECT * FROM reservation";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                reservations.add(new Reservation(
                        rs.getLong("id"),
                        rs.getInt("place"),
                        rs.getInt("id_user"),
                        rs.getDate("date_reservation").toLocalDate(),
                        rs.getString("statut"),
                        rs.getFloat("prixTotal"),
                        rs.getInt("id_ticket"),
                        rs.getInt("id_parking")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des réservations : " + e.getMessage());
        }
        return reservations;
    }
}
