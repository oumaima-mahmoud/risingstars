package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import entite.Reclamation;
import tools.DataSource;
import java.util.Map;
import java.util.HashMap;

public class ReclamationService implements IService<Reclamation> {
    private Connection cnx;
    private HuggingFaceAPI huggingFaceAPI;

    public ReclamationService() {
        this.cnx = DataSource.getInstance().getConnection();
        this.huggingFaceAPI = new HuggingFaceAPI();


    }


    @Override
    public void ajouter(Reclamation reclamation) {
        // Analyze sentiment of the reclamation description
        String sentiment = huggingFaceAPI.analyzeSentiment(reclamation.getDescription());
        String autoResponse = huggingFaceAPI.generateAutoResponse(sentiment);

        // Print the auto-response (or handle it as needed)
        System.out.println("Auto-Response: " + autoResponse);

        // Save the reclamation to the database (unchanged)
        String query = "INSERT INTO reclamation (user_id, type, description, objet, etat, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, reclamation.getUserId());
            ps.setString(2, reclamation.getType());
            ps.setString(3, reclamation.getDescription());
            ps.setString(4, reclamation.getObjet());
            ps.setString(5, reclamation.getEtat());
            ps.setString(6, reclamation.getPhoneNumber());
            ps.executeUpdate();
            System.out.println("Reclamation ajoutée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }


    @Override
    public void modifier(Reclamation reclamation) {

        // Update the reclamation in the database
        String query = "UPDATE reclamation SET type = ?, description = ?, objet = ?, etat = ?, phone_number = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, reclamation.getType());
            ps.setString(2, reclamation.getDescription());
            ps.setString(3, reclamation.getObjet());
            ps.setString(4, reclamation.getEtat());
            ps.setString(5, reclamation.getPhoneNumber());
            ps.setInt(6, reclamation.getId());
            ps.executeUpdate();
            System.out.println("Reclamation modifiée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification : " + e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String query = "DELETE FROM reclamation WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Reclamation supprimée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    @Override
    public Reclamation getOne(Reclamation reclamation) {
        String query = "SELECT * FROM reclamation WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, reclamation.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Reclamation(
                        rs.getInt("user_id"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getString("objet"),
                        rs.getString("etat"),
                        rs.getString("phone_number")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Reclamation> getAll(Reclamation reclamation) {
        List<Reclamation> reclamations = new ArrayList<>();
        try {
            String query = "SELECT * FROM reclamation";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Reclamation rec = new Reclamation(
                        rs.getInt("user_id"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getString("objet"),
                        rs.getString("etat"),
                        rs.getString("phone_number")
                );
                rec.setId(rs.getInt("id"));
                rec.setDateReclamation(rs.getString("date_reclamation"));
                reclamations.add(rec);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des réclamations : " + ex.getMessage());
        }
        return reclamations;
    }


    // New method for search and filtering
    public List<Reclamation> searchReclamations(String keyword, String type, String etat) {
        List<Reclamation> reclamations = new ArrayList<>();
        // Base query
        String query = "SELECT * FROM reclamation WHERE 1=1";

        // Add filters dynamically based on input
        if (type != null && !type.isEmpty()) {
            query += " AND type LIKE ?";
        }
        if (etat != null && !etat.isEmpty()) {
            query += " AND etat LIKE ?";
        }
        if (keyword != null && !keyword.isEmpty()) {
            query += " AND (description LIKE ? OR objet LIKE ?)";
        }

        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            int parameterIndex = 1;

            // Set parameters for type
            if (type != null && !type.isEmpty()) {
                ps.setString(parameterIndex++, "%" + type + "%");
            }

            // Set parameters for etat
            if (etat != null && !etat.isEmpty()) {
                ps.setString(parameterIndex++, "%" + etat + "%");
            }

            // Set parameters for keyword
            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(parameterIndex++, "%" + keyword + "%");
                ps.setString(parameterIndex++, "%" + keyword + "%");
            }

            // Execute the query
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reclamation rec = new Reclamation(
                        rs.getInt("user_id"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getString("objet"),
                        rs.getString("etat"),
                        rs.getString("phone_number") // New field
                );
                rec.setId(rs.getInt("id"));
                rec.setDateReclamation(rs.getString("date_reclamation"));
                reclamations.add(rec);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche : " + e.getMessage());
        }
        return reclamations;
    }

    // New method for pagination
    public List<Reclamation> getReclamationsPaginated(int offset, int limit) {
        List<Reclamation> reclamations = new ArrayList<>();
        String query = "SELECT * FROM reclamation LIMIT ? OFFSET ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reclamation rec = new Reclamation(
                        rs.getInt("user_id"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getString("objet"),
                        rs.getString("etat"),
                        rs.getString("phone_number") // New field
                );
                rec.setId(rs.getInt("id"));
                rec.setDateReclamation(rs.getString("date_reclamation"));
                reclamations.add(rec);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la pagination : " + e.getMessage());
        }
        return reclamations;
    }
    public Map<String, Integer> getReclamationsByEtat() {
        Map<String, Integer> reclamationsByEtat = new HashMap<>();
        String query = "SELECT etat, COUNT(*) as count FROM reclamation GROUP BY etat";
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                String etat = rs.getString("etat");
                int count = rs.getInt("count");
                reclamationsByEtat.put(etat, count);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des réclamations par état : " + e.getMessage());
        }
        return reclamationsByEtat;
    }

    public ObservableList<Reclamation> afficherReclamation() {
        ObservableList<Reclamation> recList = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM reclamation";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Reclamation rec = new Reclamation(
                        rs.getInt("user_id"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getString("objet"),
                        rs.getString("etat"),
                        rs.getString("phone_number") // New field
                );
                rec.setId(rs.getInt("id"));
                rec.setDateReclamation(rs.getString("date_reclamation"));
                recList.add(rec);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage : " + e.getMessage());
        }
        return recList;

    }
    public List<String> getAllReclamationTypes() {
        List<String> types = new ArrayList<>();
        String query = "SELECT DISTINCT type FROM reclamation";
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                types.add(rs.getString("type"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching reclamation types: " + e.getMessage());
        }
        return types;
    }
    public List<String> getAllReclamationStates() {
        List<String> states = new ArrayList<>();
        String query = "SELECT DISTINCT etat FROM reclamation";
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                states.add(rs.getString("etat"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching reclamation states: " + e.getMessage());
        }
        return states;
    }

    public List<Reclamation> getReclamationsByUserId(int userId) {
        List<Reclamation> reclamations = new ArrayList<>();
        String query = "SELECT * FROM reclamation WHERE user_id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reclamation rec = new Reclamation(
                        rs.getInt("user_id"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getString("objet"),
                        rs.getString("etat"),
                        rs.getString("phone_number")
                );
                rec.setId(rs.getInt("id"));
                rec.setDateReclamation(rs.getString("date_reclamation"));
                reclamations.add(rec);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching reclamations by user ID: " + e.getMessage());
        }
        return reclamations;
    }
    public List<Reclamation> getReclamationsByState(String state) {
        List<Reclamation> reclamations = new ArrayList<>();
        String query = "SELECT * FROM reclamation WHERE etat = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, state);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reclamation rec = new Reclamation(
                        rs.getInt("user_id"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getString("objet"),
                        rs.getString("etat"),
                        rs.getString("phone_number")
                );
                rec.setId(rs.getInt("id"));
                rec.setDateReclamation(rs.getString("date_reclamation"));
                reclamations.add(rec);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching reclamations by state: " + e.getMessage());
        }
        return reclamations;
    }

}