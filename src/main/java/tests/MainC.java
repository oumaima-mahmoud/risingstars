package tests;

import models.Commande;
import services.ServiceCommande;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

public class MainC {
    public static void main(String[] args) {
        ServiceCommande serviceCommande = new ServiceCommande();

        // Ajouter une commande (uniquement si c'est un ajout)
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse("2025-06-21");
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            Commande commande = new Commande(5, sqlDate, 250.0, 1);
            serviceCommande.ajouterCommande(commande); // Ajout d'une commande
            System.out.println("Commande ajoutée !");
        } catch (SQLException | ParseException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }

        // Afficher toutes les commandes
        try {
            System.out.println("Liste des commandes : ");
            List<Commande> commandes = serviceCommande.afficherCommandes();
            for (Commande c : commandes) {
                System.out.println(c);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des commandes : " + e.getMessage());
        }
        // Modifier une commande existante
        try {
            java.sql.Date sqlDateNow = new java.sql.Date(System.currentTimeMillis());
            Commande commandeModifiee = new Commande(19, 17, sqlDateNow, 2000.0, 27);
            serviceCommande.modifierCommande(commandeModifiee); // Modification de la commande avec id 14
            System.out.println("Commande modifiée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification : " + e.getMessage());
        }
        // Supprimer une commande
        try {
            serviceCommande.supprimerCommande(21);
            System.out.println("Commande supprimée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }

    }
}
