package services;

import entite.Reponse;
import tools.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReponseService {

    private Connection cnx;

    public ReponseService() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    public void ajouterReponse(Reponse reponse) {
        String query = "INSERT INTO reponse (id_reclamation, contenu, date_reponse, type, fichier_joint, priorite) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setInt(1, reponse.getIdReclamation());
            stmt.setString(2, reponse.getContenu());
            stmt.setString(3, reponse.getDateReponse());
            stmt.setString(4, reponse.getType());
            stmt.setString(5, reponse.getFichierJoint());
            stmt.setInt(6, reponse.getPriorite());
            stmt.executeUpdate();
            System.out.println("Réponse ajoutée avec succès !");

            // Fetch the phone number associated with the reclamation
            String phoneNumber = getPhoneNumberByReclamationId(reponse.getIdReclamation());
            if (phoneNumber != null) {
                // Send SMS notification
                String smsBody = "Une nouvelle réponse a été ajoutée à votre réclamation:\n" +
                        "Contenu: " + reponse.getContenu() + "\n" +
                        "Date: " + reponse.getDateReponse() + "\n" +
                        "Type: " + reponse.getType();
                TwilioService.sendSMS(phoneNumber, smsBody);
            } else {
                System.out.println("Aucun numéro de téléphone trouvé pour cette réclamation.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String getPhoneNumberByReclamationId(int reclamationId) {
        String phoneNumber = null;
        String query = "SELECT phone_number FROM reclamation WHERE id = ?"; // Adjust the column name if necessary
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setInt(1, reclamationId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    phoneNumber = rs.getString("phone_number");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phoneNumber;
    }
    public List<Reponse> getAllReponses() {
        List<Reponse> responses = new ArrayList<>();
        String query = "SELECT * FROM reponse";
        try (Statement stmt = cnx.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Reponse reponse = new Reponse(
                        rs.getInt("id"),
                        rs.getInt("id_reclamation"),
                        rs.getString("contenu"),
                        rs.getString("date_reponse"),
                        rs.getString("type"),
                        rs.getString("fichier_joint"),
                        rs.getInt("priorite")
                );
                responses.add(reponse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return responses;
    }

    public void supprimerReponse(int id) {
        String query = "DELETE FROM reponse WHERE id = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifierReponse(Reponse reponse) {
        String query = "UPDATE reponse SET contenu = ?, date_reponse = ?, type = ?, fichier_joint = ?, priorite = ? WHERE id = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setString(1, reponse.getContenu());
            stmt.setString(2, reponse.getDateReponse());
            stmt.setString(3, reponse.getType());
            stmt.setString(4, reponse.getFichierJoint());
            stmt.setInt(5, reponse.getPriorite());
            stmt.setInt(6, reponse.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Reponse> getReponsesByReclamationId(int reclamationId) {
        List<Reponse> responses = new ArrayList<>();
        String query = "SELECT * FROM reponse WHERE id_reclamation = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setInt(1, reclamationId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Reponse reponse = new Reponse(
                            rs.getInt("id"),
                            rs.getInt("id_reclamation"),
                            rs.getString("contenu"),
                            rs.getString("date_reponse"),
                            rs.getString("type"),
                            rs.getString("fichier_joint"),
                            rs.getInt("priorite")
                    );
                    responses.add(reponse);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return responses;
    }
    public void ajouterAutoReponse(int idReclamation, String contenu, String type) {
        String query = "INSERT INTO reponse (id_reclamation, contenu, date_reponse, type, fichier_joint, priorite) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setInt(1, idReclamation);
            stmt.setString(2, contenu);
            stmt.setString(3, new java.sql.Timestamp(System.currentTimeMillis()).toString()); // Current timestamp
            stmt.setString(4, type);
            stmt.setString(5, ""); // No attachment for auto-response
            stmt.setInt(6, 1); // Default priority for auto-response
            stmt.executeUpdate();
            System.out.println("Auto-réponse ajoutée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}