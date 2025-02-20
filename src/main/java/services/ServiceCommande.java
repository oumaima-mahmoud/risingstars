package services;

import models.Commande;
import utils.MyDabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCommande {
    private Connection cnx;

    public ServiceCommande() {
        cnx = MyDabase.getInstance().getConnection();
    }

    // Vérifier si la commande existe
    public boolean verifierCommandeExistante(int idCommande) throws SQLException {
        String req = "SELECT COUNT(*) FROM commande WHERE idCommande = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setInt(1, idCommande);  // Placer l'ID de la commande dans la requête SQL
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;  // Si le count est supérieur à 0, la commande existe
            }
        }
        return false;  // La commande n'existe pas
    }

    public void ajouterCommande(Commande c) throws SQLException {
        String req = "INSERT INTO commande (quantite, dateCommande, prix, idPanier) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setInt(1, c.getQuantite());
            pst.setDate(2, c.getDateCommande());
            pst.setDouble(3, c.getPrix());

            // Vérifier si idPanier est NULL
            if (c.getIdPanier() == 0) {
                pst.setNull(4, Types.INTEGER);
            } else {
                pst.setInt(4, c.getIdPanier());
            }

            pst.executeUpdate();
            System.out.println("Commande ajoutée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la commande : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void modifierCommande(Commande c) throws SQLException {
        // Vérifier si le nouvel idPanier existe dans la table 'panier'
        String checkPanierReq = "SELECT COUNT(*) FROM panier WHERE idPanier = ?";
        try (PreparedStatement pstCheck = cnx.prepareStatement(checkPanierReq)) {
            pstCheck.setInt(1, c.getIdPanier());  // Passer le nouvel idPanier
            ResultSet rsCheck = pstCheck.executeQuery();
            rsCheck.next(); // Aller à la première ligne du résultat
            int count = rsCheck.getInt(1); // Compte des résultats (si > 0, le panier existe)

            // Si le panier existe, procéder à la mise à jour de la commande
            if (count > 0) {
                String req = "UPDATE commande SET quantite = ?, dateCommande = ?, prix = ?, idPanier = ? WHERE idCommande = ?";
                try (PreparedStatement pst = cnx.prepareStatement(req)) {
                    pst.setInt(1, c.getQuantite());
                    pst.setDate(2, c.getDateCommande());
                    pst.setDouble(3, c.getPrix());

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
                } catch (SQLException e) {
                    System.out.println("Erreur lors de la modification de la commande : " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                // Si le panier n'existe pas, afficher un message d'erreur
                System.out.println("Erreur : Le nouvel idPanier n'existe pas dans la table panier !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la vérification de l'idPanier : " + e.getMessage());
            e.printStackTrace();
        }
    }

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

    public List<Commande> afficherCommandes() throws SQLException {
        List<Commande> commandes = new ArrayList<>();
        String req = "SELECT * FROM commande";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Commande c = new Commande(
                        rs.getInt("idCommande"),
                        rs.getInt("quantite"),
                        rs.getDate("dateCommande"),
                        rs.getDouble("prix"),
                        rs.getInt("idPanier") // Récupère idPanier même s'il est NULL
                );
                commandes.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage des commandes : " + e.getMessage());
            e.printStackTrace();
        }
        return commandes;
    }
}
