package org.example;

import Services.UtilisateurService;
import entities.Role;
import entities.StatutAbonnement;
import entities.TypeAbonnement;
import entities.Utilisateur;

public class Main {
    public static void main(String[] args) {
        UtilisateurService userService = new UtilisateurService();

        // Clean the table before testing
        // Remove this line if you don't want to clear the table before each test
        // userService.viderTable();

        // Create a unique email for testing
        String emailUnique = "test" + System.currentTimeMillis() + "@example.com";

        // CREATE user
        Utilisateur nouvelUser = new Utilisateur(0, "Jean", "Dupont", emailUnique, "mdpSecret", Role.SPECTATEUR, "0123456789", StatutAbonnement.ACTIF, TypeAbonnement.PREMIUM, 100);
        userService.ajouter(nouvelUser);
        System.out.println("Utilisateur created with ID: " + nouvelUser.getIdUser());

        // READ user
        Utilisateur userFromDB = userService.getOne(nouvelUser.getIdUser());
        if(userFromDB != null) {
            System.out.println("Utilisateur found: " + userFromDB.getEmail());

            // UPDATE user
            userFromDB.setEmail("nouveau_" + emailUnique);
            userService.modifier(userFromDB);
            System.out.println("Updated email: " + userService.getOne(userFromDB.getIdUser()).getEmail());

            // DELETE user
            userService.supprimer(userFromDB.getIdUser());
            System.out.println("User deleted: " +
                    (userService.getOne(userFromDB.getIdUser()) == null ? "Yes" : "No"));
        } else {
            System.out.println("Error: User not found after creation");
        }
    }
}
