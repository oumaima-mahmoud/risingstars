package services;

import models.Panier;
import utils.MyDabase;  // Assure-toi que c'est bien 'MyDatabase', et non 'MyDabase'
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.HashMap;
import java.util.Map;
import java.sql.DriverManager;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ServicePanier {
    private Connection cnx;
    private Map<String, Integer> paniersParEtat = new HashMap<>();

    public ServicePanier() {
        this.cnx = MyDabase.getInstance().getConnection(); // Utilisation de la connexion MyDatabase
    }

    // Méthode pour ajouter un panier
    public void ajouter(Panier t) throws SQLException {
        String req = "INSERT INTO panier (dateCreation, total, etat) VALUES (?, ?, ?)";
        try (PreparedStatement stm = cnx.prepareStatement(req)) {
            stm.setObject(1, t.getDateCreation());  // Utilisation de setObject pour la date
            stm.setDouble(2, t.getTotal());
            stm.setString(3, t.getEtat());
            stm.executeUpdate();
            System.out.println("Panier ajouté avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout : " + ex.getMessage());
            throw ex;
        }
    }

    // Méthode pour modifier un panier
    public void modifier(Panier t) throws SQLException {
        String checkReq = "SELECT COUNT(*) FROM panier WHERE idPanier = ?";
        try (PreparedStatement checkPst = cnx.prepareStatement(checkReq)) {
            checkPst.setInt(1, t.getIdPanier());
            try (ResultSet rs = checkPst.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    String req = "UPDATE panier SET dateCreation = ?, total = ?, etat = ? WHERE idPanier = ?";
                    try (PreparedStatement pst = cnx.prepareStatement(req)) {
                        pst.setObject(1, t.getDateCreation());  // Utilisation de setObject pour la date
                        pst.setDouble(2, t.getTotal());
                        pst.setString(3, t.getEtat());
                        pst.setInt(4, t.getIdPanier());
                        pst.executeUpdate();
                        System.out.println("Panier modifié avec succès !");
                    }
                } else {
                    System.out.println("Panier avec id " + t.getIdPanier() + " introuvable pour la modification.");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la modification : " + ex.getMessage());
            throw ex;
        }
    }

    // Méthode pour supprimer un panier
    public void supprimer(int idPanier) throws SQLException {
        String checkReq = "SELECT COUNT(*) FROM panier WHERE idPanier = ?";
        try (PreparedStatement checkPst = cnx.prepareStatement(checkReq)) {
            checkPst.setInt(1, idPanier);
            try (ResultSet rs = checkPst.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    String req = "DELETE FROM panier WHERE idPanier = ?";
                    try (PreparedStatement pst = cnx.prepareStatement(req)) {
                        pst.setInt(1, idPanier);
                        pst.executeUpdate();
                        System.out.println("Panier supprimé avec succès !");
                    }
                } else {
                    System.out.println("Panier avec id " + idPanier + " introuvable pour la suppression.");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression : " + ex.getMessage());
            throw ex;
        }
    }

    // Méthode pour récupérer tous les paniers
    public ObservableList<Panier> getAll() throws SQLException {
        ObservableList<Panier> paniers = FXCollections.observableArrayList();
        String req = "SELECT * FROM panier";
        try (Statement stm = cnx.createStatement(); ResultSet rs = stm.executeQuery(req)) {
            while (rs.next()) {
                Panier p = new Panier();
                p.setIdPanier(rs.getInt("idPanier"));
                p.setDateCreation(rs.getDate("dateCreation"));  // Récupération directe de la date
                p.setTotal(rs.getDouble("total"));
                p.setEtat(rs.getString("etat"));
                paniers.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des paniers : " + ex.getMessage());
            throw ex;
        }
        return paniers;
    }

    // Méthode pour trier les paniers par total
    public List<Panier> trierParTotal(boolean ascendant) throws SQLException {
        List<Panier> paniers = new ArrayList<>();
        String ordre = ascendant ? "ASC" : "DESC";
        String query = "SELECT * FROM panier ORDER BY total " + ordre;

        try (PreparedStatement stmt = cnx.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Panier panier = new Panier(
                        rs.getInt("idPanier"),
                        rs.getDate("dateCreation"),  // Correction ici pour la date
                        rs.getDouble("total"),
                        rs.getString("etat")
                );
                paniers.add(panier);
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors du tri des paniers : " + e.getMessage());
        }
        return paniers;
    }

    // Méthode pour rechercher par ID
    public List<Panier> rechercherParID(String searchTerm) throws SQLException {
        List<Panier> paniersTrouves = new ArrayList<>();
        String query = "SELECT * FROM panier WHERE idPanier = ?";  // Recherche par idPanier

        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setInt(1, Integer.parseInt(searchTerm));  // Convertir en entier
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Panier panier = new Panier(
                        rs.getInt("idPanier"),
                        rs.getDate("dateCreation"),
                        rs.getDouble("total"),
                        rs.getString("etat")
                );
                paniersTrouves.add(panier);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
            throw e; // Re-throw exception for handling in the controller
        } catch (NumberFormatException e) {
            System.err.println("Erreur de format d'ID : " + e.getMessage());
        }
        return paniersTrouves;
    }
    // Méthode pour obtenir les pourcentages des états des paniers
    public Map<String, Double> obtenirStatistiquePourcentageEtatPanier() {
        Map<String, Integer> statistiques = new HashMap<>();
        Map<String, Double> pourcentages = new HashMap<>();

        // Exemple de données statiques (états des paniers)
        String[] etatsPaniers = {"en cours", "Annulé", "validé"};

        // Comptage des états
        for (String etat : etatsPaniers) {
            statistiques.put(etat, statistiques.getOrDefault(etat, 0) + 1);
        }

        // Calcul des pourcentages
        int total = etatsPaniers.length;
        for (Map.Entry<String, Integer> entry : statistiques.entrySet()) {
            String etat = entry.getKey();
            int count = entry.getValue();
            double percentage = ((double) count / total) * 100;
            pourcentages.put(etat, percentage);
        }

        return pourcentages;
    }
    public void exportToPDF() {
        String pdfFilePath = "C:\\IntelliJ IDEA 2024.2.1\\Consommation\\fichier.pdf";

        try {
            Document document = new Document(PageSize.A4, 20, 20, 20, 20);
            PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
            document.open();

            // Titre
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);
            Paragraph title = new Paragraph("Liste des Paniers", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Création du tableau
            PdfPTable table = new PdfPTable(4); // Le nombre de colonnes doit être 4
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);

            // En-têtes du tableau
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
            BaseColor headerColor = new BaseColor(0, 0, 255);

            String[] headers = {"idPanier", "dateCreation", "etat", "total"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setBackgroundColor(headerColor);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            // Correction de la requête SQL si nécessaire
            String query = "SELECT idpanier, datecreation, etat, total FROM PANIER"; // Utilisation des bons noms de colonnes
            PreparedStatement ps = cnx.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            Font cellFont = new Font(Font.FontFamily.HELVETICA, 12);
            while (rs.next()) {
                table.addCell(new PdfPCell(new Phrase(String.valueOf(rs.getInt("idpanier")), cellFont))); // Utilisation de "idpanier"
                table.addCell(new PdfPCell(new Phrase(rs.getString("datecreation"), cellFont))); // Utilisation de "datecreation"
                table.addCell(new PdfPCell(new Phrase(rs.getString("etat"), cellFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(rs.getDouble("total")), cellFont)));
            }

            document.add(table);
            document.close();

            System.out.println("PDF généré avec succès !");

            // Ouvrir automatiquement le PDF
            java.awt.Desktop.getDesktop().open(new java.io.File(pdfFilePath));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
