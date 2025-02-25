package Services;

import entities.Ticket;
import tools.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketService {
    private Connection connection;

    public TicketService() {
        this.connection = DataSource.getInstance().getConnection();
    }

    // 🟢 Ajouter un ticket
    public void ajouterTicket(Ticket ticket) throws SQLException {
        String query = "INSERT INTO ticket (reservation_id, date, numero_de_place, code_barre, prix) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, ticket.getReservationId());
        ps.setDate(2, new java.sql.Date(ticket.getDate().getTime()));
        ps.setInt(3, ticket.getNumeroDePlace());
        ps.setString(4, ticket.getCodeBarre());
        ps.setDouble(5, ticket.getPrix());
        ps.executeUpdate();
    }

    // ✅ Métier avancé : Générer un code-barres unique
    public String genererCodeBarre(int reservationId, int numeroPlace) {
        return "TICKET-" + reservationId + "-" + numeroPlace + "-" + System.currentTimeMillis();
    }
}
