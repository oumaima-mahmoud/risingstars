package services;

import entite.Parking;
import tools.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ParkingService implements IService<Parking> {
    private Connection cnx;

    public ParkingService() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Parking parking) {
        String query = "INSERT INTO parking (reservation_id, nbr_place, statut, date_reservation, heure_debut, heure_fin, prix) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, parking.getReservationId());
            ps.setInt(2, parking.getNbrPlace());
            ps.setString(3, parking.getStatut());
            ps.setTimestamp(4, Timestamp.valueOf(parking.getDateReservation()));
            ps.setTimestamp(5, Timestamp.valueOf(parking.getHeureDebut()));
            ps.setTimestamp(6, Timestamp.valueOf(parking.getHeureFin()));
            ps.setFloat(7, parking.getPrix());
            ps.executeUpdate();
            System.out.println("Parking ajouté !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    @Override
    public void modifier(Parking parking) {
        String query = "UPDATE parking SET reservation_id = ?, nbr_place = ?, statut = ?, date_reservation = ?, heure_debut = ?, heure_fin = ?, prix = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, parking.getReservationId());
            ps.setInt(2, parking.getNbrPlace());
            ps.setString(3, parking.getStatut());
            ps.setTimestamp(4, Timestamp.valueOf(parking.getDateReservation()));
            ps.setTimestamp(5, Timestamp.valueOf(parking.getHeureDebut()));
            ps.setTimestamp(6, Timestamp.valueOf(parking.getHeureFin()));
            ps.setFloat(7, parking.getPrix());
            ps.setInt(8, parking.getId());
            ps.executeUpdate();
            System.out.println("Parking modifié !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification : " + e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String query = "DELETE FROM parking WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Parking supprimé !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    @Override
    public Parking getOne(Parking parking) {
        String query = "SELECT * FROM parking WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, parking.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Parking(
                        rs.getInt("id"),
                        rs.getInt("reservation_id"),
                        rs.getInt("nbr_place"),
                        rs.getString("statut"),
                        rs.getTimestamp("date_reservation").toLocalDateTime(),
                        rs.getTimestamp("heure_debut").toLocalDateTime(),
                        rs.getTimestamp("heure_fin").toLocalDateTime(),
                        rs.getFloat("prix")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Parking> getAll(Parking parking) {
        List<Parking> parkings = new ArrayList<>();
        try {
            String query = "SELECT * FROM parking";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                parkings.add(new Parking(
                        rs.getInt("id"),
                        rs.getInt("reservation_id"),
                        rs.getInt("nbr_place"),
                        rs.getString("statut"),
                        rs.getTimestamp("date_reservation").toLocalDateTime(),
                        rs.getTimestamp("heure_debut").toLocalDateTime(),
                        rs.getTimestamp("heure_fin").toLocalDateTime(),
                        rs.getFloat("prix")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des parkings : " + e.getMessage());
        }
        return parkings;
    }
    public List<Parking> filterByStatus(String status) {
        List<Parking> parkings = new ArrayList<>();
        try {
            String query = "SELECT * FROM parking WHERE statut LIKE ?";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setString(1, status.equals("Tous") ? "%" : status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                parkings.add(new Parking(
                        rs.getInt("id"),
                        rs.getInt("nbr_place"),
                        rs.getString("statut"),
                        rs.getTimestamp("date_reservation").toLocalDateTime(),
                        rs.getTimestamp("heure_debut").toLocalDateTime(),
                        rs.getTimestamp("heure_fin").toLocalDateTime(),
                        rs.getFloat("prix")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du filtrage des parkings : " + e.getMessage());
        }
        return parkings;
    }

    public void reserveParking(Parking parking) {
        String query = "INSERT INTO parking (nbr_place, statut, date_reservation, heure_debut, heure_fin, prix) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, parking.getNbrPlace());
            ps.setString(2, parking.getStatut());
            ps.setTimestamp(3, Timestamp.valueOf(parking.getDateReservation()));
            ps.setTimestamp(4, Timestamp.valueOf(parking.getHeureDebut()));
            ps.setTimestamp(5, Timestamp.valueOf(parking.getHeureFin()));
            ps.setFloat(6, parking.getPrix());
            ps.executeUpdate();
            System.out.println("Parking réservé !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la réservation : " + e.getMessage());
        }
    }

}
