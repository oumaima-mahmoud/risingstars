package org.example;

import Services.UtilisateurService;
import entities.Utilisateur;

public class Main {
    public static void main(String[] args) {
        UtilisateurService userService = new UtilisateurService();

        // Nettoyer la table avant le test
        userService.viderTable();

        // Création d'un email unique
        String emailUnique = "test" + System.currentTimeMillis() + "@example.com";

        // Test CREATE
        Utilisateur nouvelUser = new Utilisateur(0, "Jean", "Dupont", emailUnique, "mdpSecret");
        userService.ajouter(nouvelUser);
        System.out.println("✅ Utilisateur créé - ID: " + nouvelUser.getIdUser());

        // Test READ
        Utilisateur userFromDB = userService.getOne(nouvelUser.getIdUser());
        if(userFromDB != null) {
            System.out.println("📥 Utilisateur trouvé : " + userFromDB.getEmail());

            // Test UPDATE
            userFromDB.setEmail("nouveau_" + emailUnique);
            userService.modifier(userFromDB);
            System.out.println("🔄 Email mis à jour : " + userService.getOne(userFromDB.getIdUser()).getEmail());

            // Test DELETE
            userService.supprimer(userFromDB.getIdUser());
            System.out.println("❌ Suppression réussie ? " +
                    (userService.getOne(userFromDB.getIdUser()) == null ? "Oui" : "Non"));
        } else {
            System.out.println("❌ Erreur : Utilisateur non trouvé après création");
        }
    }
}