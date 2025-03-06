package services;

import entite.*;
import tools.DataSource; // Ensure this class is correctly implemented in your project.
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TicketService implements IService<Ticket> {
    private Connection cnx;

    public TicketService() {
        this.cnx = DataSource.getInstance().getConnection();
        if (this.cnx == null) {
            System.out.println("❌ Database connection is NULL!");
        } else {
            System.out.println("✅ Database connected.");
        }
    }

    @Override
    public void ajouter(Ticket ticket) {
        String query = "INSERT INTO ticket (date, num_place, code_barre, prix, id_stade) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setTimestamp(1, Timestamp.valueOf(ticket.getDate()));
            ps.setInt(2, ticket.getNumPlace());
            ps.setString(3, ticket.getCodeBarre());
            ps.setFloat(4, ticket.getPrix());
            ps.setInt(5, ticket.getIdStade());
            ps.executeUpdate();
            System.out.println("Ticket ajouté !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    @Override
    public void modifier(Ticket ticket) {
        String query = "UPDATE ticket SET date = ?, num_place = ?, code_barre = ?, prix = ?, id_stade = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setTimestamp(1, Timestamp.valueOf(ticket.getDate()));
            ps.setInt(2, ticket.getNumPlace());
            ps.setString(3, ticket.getCodeBarre());
            ps.setFloat(4, ticket.getPrix());
            ps.setInt(5, ticket.getIdStade());
            ps.setInt(6, ticket.getId());
            ps.executeUpdate();
            System.out.println("Ticket modifié !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification : " + e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String query = "DELETE FROM ticket WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Ticket supprimé !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
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
                        rs.getTimestamp("date").toLocalDateTime(),
                        rs.getInt("num_place"),
                        rs.getString("code_barre"),
                        rs.getFloat("prix"),
                        rs.getInt("id_stade")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Ticket> getAll(Ticket ignoredTicket) {
        List<Ticket> tickets = new ArrayList<>();
        try {
            String query = "SELECT * FROM ticket";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                tickets.add(new Ticket(
                        rs.getInt("id"),
                        rs.getTimestamp("date").toLocalDateTime(),
                        rs.getInt("num_place"),
                        rs.getString("code_barre"),
                        rs.getFloat("prix"),
                        rs.getInt("id_stade")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des tickets : " + e.getMessage());
        }
        return tickets;
    }

    public List<Ticket> searchTickets(String keyword) {
        List<Ticket> tickets = new ArrayList<>();
        String query = "SELECT * FROM ticket WHERE id LIKE ? OR code_barre LIKE ? OR num_place LIKE ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ps.setString(3, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tickets.add(new Ticket(
                        rs.getInt("id"),
                        rs.getTimestamp("date").toLocalDateTime(),
                        rs.getInt("num_place"),
                        rs.getString("code_barre"),
                        rs.getFloat("prix"),
                        rs.getInt("id_stade")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche des tickets : " + e.getMessage());
        }
        return tickets;
    }
    public List<Stade> getAllStade(Stade ignoredStade) {
        List<Stade> stades = new ArrayList<>();
        try {
            String query = "SELECT * FROM stade";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                stades.add(new Stade(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getDouble("capacite"),
                        rs.getString("image")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des tickets : " + e.getMessage());
        }
        return stades;
    }
}
