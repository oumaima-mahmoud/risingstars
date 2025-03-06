package services;

import entite.Commande;

import java.text.SimpleDateFormat;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.awt.Desktop;
import java.io.File;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import tools.DataSource;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ServiceCommande {
    private Connection cnx;

    public ServiceCommande() {
        cnx = DataSource.getInstance().getConnection();
    }

    // Vérifier si la commande existe
    public boolean verifierCommandeExistante(int idCommande) throws SQLException {
        String req = "SELECT COUNT(*) FROM commande WHERE idCommande = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setInt(1, idCommande);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;  // La commande existe si le count est supérieur à 0
                }
            }
        }
        return false;  // La commande n'existe pas
    }

    // Ajouter une commande
    public void ajouterCommande(Commande c) throws SQLException {
        String req = "INSERT INTO commande (quantite, dateCommande, prix, idPanier) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setInt(1, c.getQuantite());

            // Convertir la chaîne de date en java.sql.Date
            if (c.getDateCommande() != null && !c.getDateCommande().isEmpty()) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedDate = format.parse(c.getDateCommande());
                pst.setDate(2, new Date(parsedDate.getTime()));
            } else {
                pst.setNull(2, Types.DATE);  // Si la date est vide, on insère NULL
            }

            pst.setDouble(3, c.getPrix());

            // Vérifier si idPanier est NULL
            if (c.getIdPanier() == 0) {
                pst.setNull(4, Types.INTEGER);
            } else {
                pst.setInt(4, c.getIdPanier());
            }

            pst.executeUpdate();
            System.out.println("Commande ajoutée !");
        } catch (SQLException | java.text.ParseException e) {
            System.out.println("Erreur lors de l'ajout de la commande : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Modifier une commande
    public void modifierCommande(Commande c) throws SQLException {
        // Vérifier si le nouvel idPanier existe dans la table 'panier'
        String checkPanierReq = "SELECT COUNT(*) FROM panier WHERE idPanier = ?";
        try (PreparedStatement pstCheck = cnx.prepareStatement(checkPanierReq)) {
            pstCheck.setInt(1, c.getIdPanier());  // Passer le nouvel idPanier
            try (ResultSet rsCheck = pstCheck.executeQuery()) {
                if (rsCheck.next() && rsCheck.getInt(1) > 0) {  // Le panier existe
                    String req = "UPDATE commande SET quantite = ?, dateCommande = ?, prix = ?, idPanier = ? WHERE idCommande = ?";
                    try (PreparedStatement pst = cnx.prepareStatement(req)) {
                        pst.setInt(1, c.getQuantite());

                        // Convertir la chaîne de date en java.sql.Date
                        if (c.getDateCommande() != null && !c.getDateCommande().isEmpty()) {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            java.util.Date parsedDate = format.parse(c.getDateCommande());
                            pst.setDate(2, new Date(parsedDate.getTime()));
                        } else {
                            pst.setNull(2, Types.DATE);  // Si la date est vide, on insère NULL
                        }

                        pst.setDouble(3, c.getPrix());

                        // Vérifier si idPanier est NULL
                        if (c.getIdPanier() == 0) {
                            pst.setNull(4, Types.INTEGER);
                        } else {
                            pst.setInt(4, c.getIdPanier());
                        }

                        pst.setInt(5, c.getIdCommande());
                        int rowsUpdated = pst.executeUpdate();

                        if (rowsUpdated > 0) {
                            System.out.println("Commande modifiée !");
                        } else {
                            System.out.println("Aucune commande trouvée avec cet ID !");
                        }
                    } catch (SQLException | java.text.ParseException e) {
                        System.out.println("Erreur lors de la modification de la commande : " + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Erreur : Le nouvel idPanier n'existe pas dans la table panier !");
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la vérification de l'idPanier : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // Supprimer une commande
    public void supprimerCommande(int idCommande) throws SQLException {
        // Vérifier si la commande existe avant de la supprimer
        if (!verifierCommandeExistante(idCommande)) {
            System.out.println("Erreur : La commande avec cet ID n'existe pas !");
            return;
        }

        String req = "DELETE FROM commande WHERE idCommande = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setInt(1, idCommande);
            int rowsDeleted = pst.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Commande supprimée !");
            } else {
                System.out.println("Aucune commande trouvée avec cet ID !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la commande : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Afficher toutes les commandes
    public List<Commande> afficherCommandes() throws SQLException {
        List<Commande> commandes = new ArrayList<>();
        String req = "SELECT * FROM commande";
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                // Récupérer les données de la commande
                int idCommande = rs.getInt("idCommande");
                int quantite = rs.getInt("quantite");
                Date dateCommande = rs.getDate("dateCommande");
                double prix = rs.getDouble("prix");

                // Gérer la colonne idPanier qui peut être NULL
                Integer idPanier = rs.getObject("idPanier") != null ? rs.getInt("idPanier") : null;

                // Créer une nouvelle instance de Commande
                Commande c = new Commande(idCommande, quantite, dateCommande, prix, idPanier);

                // Ajouter la commande à la liste
                commandes.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage des commandes : " + e.getMessage());
            e.printStackTrace();
        }
        return commandes;
    }

    // Rechercher une commande par ID
    public List<Commande> rechercherParID(String searchTerm) throws SQLException {
        List<Commande> commandesTrouves = new ArrayList<>();
        if (searchTerm != null && !searchTerm.isEmpty()) {
            String query = "SELECT * FROM commande WHERE idCommande = ?";
            try (PreparedStatement stmt = cnx.prepareStatement(query)) {
                try {
                    int searchId = Integer.parseInt(searchTerm.trim());
                    stmt.setInt(1, searchId);

                    try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            Commande commande = new Commande(
                                    rs.getInt("idCommande"),
                                    rs.getInt("quantite"),
                                    rs.getDate("dateCommande"),
                                    rs.getDouble("prix"),
                                    rs.getInt("idPanier")
                            );
                            commandesTrouves.add(commande);
                        }
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Erreur de format d'ID : " + e.getMessage());
                }
            } catch (SQLException e) {
                System.err.println("Erreur SQL : " + e.getMessage());
                throw e;  // Re-lancer l'exception pour gestion ultérieure dans le contrôleur
            }
        } else {
            System.err.println("Le terme de recherche est vide.");
        }
        return commandesTrouves;
    }

    // Méthode pour trier les commandes par prix
    public List<Commande> trierParPrix(boolean ascendant) throws SQLException {
        List<Commande> commandes = new ArrayList<>();
        String ordre = ascendant ? "ASC" : "DESC";
        String query = "SELECT * FROM commande ORDER BY prix " + ordre;

        try (PreparedStatement stmt = cnx.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Commande commande = new Commande(
                        rs.getInt("idCommande"),
                        rs.getInt("quantite"),
                        rs.getDate("dateCommande"),
                        rs.getDouble("prix"),
                        rs.getInt("idPanier")
                );
                commandes.add(commande);
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors du tri des commandes : " + e.getMessage());
        }
        return commandes;
    }
    // Méthode pour obtenir les pourcentages des quantités de commandes
    public Map<String, Double> obtenirStatistiquePourcentageQuantiteCommande() {
        Map<String, Integer> statistiques = new HashMap<>();
        Map<String, Double> pourcentages = new HashMap<>();

        // Exemple de données statiques (quantités de commandes)
        int[] quantitesCommandes = {5, 10, 15, 5, 20, 10}; // Quantités d'articles dans les commandes

        // Comptage des quantités
        for (int quantite : quantitesCommandes) {
            String quantiteKey = String.valueOf(quantite); // On crée une clé sous forme de chaîne pour la quantité
            statistiques.put(quantiteKey, statistiques.getOrDefault(quantiteKey, 0) + 1);
        }

        // Calcul des pourcentages
        int total = quantitesCommandes.length;
        for (Map.Entry<String, Integer> entry : statistiques.entrySet()) {
            String quantite = entry.getKey();
            int count = entry.getValue();
            double percentage = ((double) count / total) * 100;
            pourcentages.put(quantite, percentage);
        }

        return pourcentages;
    }

    // Fonction pour catégoriser les commandes selon la quantité
    private String categoriserQuantite(int quantite) {
        if (quantite < 5) {
            return "Petite quantité (<5)";
        } else if (quantite <= 10) {
            return "Quantité moyenne (5-10)";
        } else {
            return "Grande quantité (>10)";
        }
    }
    public void exportToPDF() {
        String pdfFilePath = "C:\\IntelliJ IDEA 2024.2.1\\Consommation\\commandes.pdf";

        try {
            // Création du document PDF avec taille A4 et marges
            Document document = new Document(PageSize.A4, 20, 20, 20, 20);
            PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
            document.open();

            // Titre du PDF
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);
            Paragraph title = new Paragraph("Liste des Commandes", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Création du tableau
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);

            // En-têtes du tableau
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
            BaseColor headerColor = new BaseColor(0, 0, 255);

            String[] headers = {"idCommande", "dateCommande", "quantite", "prix", "idPanier"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setBackgroundColor(headerColor);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            // Requête SQL pour récupérer les données des commandes
            String query = "SELECT idcommande, datecommande, quantite, prix,idPanier FROM COMMANDE";
            PreparedStatement ps = cnx.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            // Police pour les cellules du tableau
            Font cellFont = new Font(Font.FontFamily.HELVETICA, 12);
            while (rs.next()) {
                // Remplir les lignes du tableau avec les données des commandes
                table.addCell(new PdfPCell(new Phrase(String.valueOf(rs.getInt("idcommande")), cellFont))); // idCommande
                table.addCell(new PdfPCell(new Phrase(rs.getString("datecommande"), cellFont))); // dateCommande
                table.addCell(new PdfPCell(new Phrase(String.valueOf(rs.getInt("quantite")), cellFont))); // quantite
                table.addCell(new PdfPCell(new Phrase(rs.getString("prix"), cellFont))); // statut
                table.addCell(new PdfPCell(new Phrase(String.valueOf(rs.getDouble("idPanier")), cellFont))); // total
            }

            // Ajouter le tableau au document
            document.add(table);

            // Fermer le document
            document.close();

            System.out.println("PDF généré avec succès !");

            // Ouvrir automatiquement le PDF
            Desktop.getDesktop().open(new File(pdfFilePath));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
