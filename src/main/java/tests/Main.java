package tests;

import models.Panier;
import services.ServicePanier;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        ServicePanier sp = new ServicePanier();

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
    }
}
