package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Ticket;
import tools.DataSource;


public class TicketService implements IService<Ticket> {
    private Connection cnx;

    public TicketService() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Ticket ticket) {
        String query = "INSERT INTO ticket (reservationID, date, numPlace, codeBarre, prix) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, ticket.getReservationID());
            ps.setDate(2, new java.sql.Date(ticket.getDate().getTime()));
            ps.setString(3, ticket.getNumPlace());
            ps.setString(4, ticket.getCodeBarre());
            ps.setInt(5, ticket.getPrix());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifier(Ticket ticket) {
        String query = "UPDATE ticket SET reservationID = ?, date = ?, numPlace = ?, codeBarre = ?, prix = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, ticket.getReservationID());
            ps.setDate(2, new java.sql.Date(ticket.getDate().getTime()));
            ps.setString(3, ticket.getNumPlace());
            ps.setString(4, ticket.getCodeBarre());
            ps.setInt(5, ticket.getPrix());
            ps.setInt(6, ticket.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(int id) {
        String query = "DELETE FROM ticket WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Ticket getOne(Ticket ticket) {
        String query = "SELECT * FROM ticket WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, ticket.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Ticket(
                        rs.getInt("id"),
                        rs.getInt("reservationID"),
                        rs.getDate("date"),
                        rs.getString("numPlace"),
                        rs.getString("codeBarre"),
                        rs.getInt("prix")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ticket> getAll(Ticket ticket) {
        List<Ticket> tickets = new ArrayList<>();
        try {
            String query = "SELECT * FROM ticket";
            PreparedStatement pst = cnx.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Ticket tic = new Ticket(
                        rs.getInt("id"),
                        rs.getInt("reservationID"),
                        rs.getDate("date"),
                        rs.getString("numPlace"),
                        rs.getString("codeBarre"),
                        rs.getInt("prix")
                );
                tickets.add(tic);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des tickets : " + ex.getMessage());
        }
        return tickets;
    }
}