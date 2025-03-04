package tests;

import models.Commande;
import models.Panier;
import services.ServiceCommande;
import services.ServicePanier;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ServicePanier sp = new ServicePanier();
        ServiceCommande serviceCommande = new ServiceCommande();
        // Ajouter un panier (uniquement si c'est un ajout)
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse("2025-06-21");
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            Panier panier = new Panier(sqlDate, 100.0, "En cours");
            sp.ajouter(panier); // Ajout d'un panier
        } catch (SQLException | ParseException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
        // Modifier un panier existant
        try {
            java.sql.Date sqlDateNow = new java.sql.Date(System.currentTimeMillis());
            Panier panierModifie = new Panier(14, sqlDateNow, 220.0, "Annulé");
            sp.modifier(panierModifie); // Modification du panier avec id 7
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification : " + e.getMessage());
        }
        // Supprimer un panier
        try {
            sp.supprimer(16); // Suppression du panier avec id 7
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }

        // Afficher tous les paniers
        try {
            System.out.println("Liste des paniers : ");
            System.out.println(sp.getAll());
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des paniers : " + e.getMessage());
        }

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
