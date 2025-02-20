package Services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import entities.Parking;
import entities.StatutRes;
import tools.DataSource;


public class ParkingService implements IService<Parking> {
    private Connection cnx;

    public ParkingService() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Parking parking) {
        String query = "INSERT INTO parking (reservationID, intPlace, statut, dateReser, heureDebut, heureFin, prix) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, parking.getReservationID());
            ps.setInt(2, parking.getIntPlace());
            ps.setString(3, parking.getStatut().toString());
            ps.setDate(4, new java.sql.Date(parking.getDateReser().getTime()));
            ps.setDate(5, new java.sql.Date(parking.getHeureDebut().getTime()));
            ps.setDate(6, new java.sql.Date(parking.getHeureFin().getTime()));
            ps.setInt(7, parking.getPrix());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifier(Parking parking) {
        String query = "UPDATE parking SET reservationID = ?, intPlace = ?, statut = ?, dateReser = ?, heureDebut = ?, heureFin = ?, prix = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, parking.getReservationID());
            ps.setInt(2, parking.getIntPlace());
            ps.setString(3, parking.getStatut().toString());
            ps.setDate(4, new java.sql.Date(parking.getDateReser().getTime()));
            ps.setDate(5, new java.sql.Date(parking.getHeureDebut().getTime()));
            ps.setDate(6, new java.sql.Date(parking.getHeureFin().getTime()));
            ps.setInt(7, parking.getPrix());
            ps.setInt(8, parking.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(int id) {
        String query = "DELETE FROM parking WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
                        rs.getInt("reservationID"),
                        rs.getInt("intPlace"),
                        StatutRes.valueOf(rs.getString("statut")),
                        rs.getDate("dateReser"),
                        rs.getDate("heureDebut"),
                        rs.getDate("heureFin"),
                        rs.getInt("prix")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Parking> getAll(Parking parking) {
        List<Parking> parkings = new ArrayList<>();
        try {
            String query = "SELECT * FROM parking";
            PreparedStatement pst = cnx.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Parking park = new Parking(
                        rs.getInt("id"),
                        rs.getInt("reservationID"),
                        rs.getInt("intPlace"),
                        StatutRes.valueOf(rs.getString("statut")),
                        rs.getDate("dateReser"),
                        rs.getDate("heureDebut"),
                        rs.getDate("heureFin"),
                        rs.getInt("prix")
                );
                parkings.add(park);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des parkings : " + ex.getMessage());
        }
        return parkings;
    }

    { public List<Parking> trouverPlacesDisponibles(Date Date dateDebut = new Date;
        dateDebut, Date Object dateFin = new Date();
        dateFin) {
        List<Parking> placesDisponibles = new ArrayList<>();


        return placesDisponibles;
    }

