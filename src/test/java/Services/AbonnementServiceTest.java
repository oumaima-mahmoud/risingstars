package Services;

import entities.Abonnement;
import entities.StatutAbonnement;
import entities.Utilisateur;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class AbonnementServiceTest {

    private static AbonnementService abonnementService;
    private static UtilisateurService utilisateurService;

    @BeforeAll
    public static void setup() {
        abonnementService = new AbonnementService();
        utilisateurService = new UtilisateurService();
    }

    @Test
    public void testCRUDAbonnement() {
        // Créer un utilisateur pour l'abonnement
        Utilisateur user = new Utilisateur(0, "UserAbn", "Test", "abonnement@test.com", "pass");
        utilisateurService.ajouter(user);

        // 1. Test CREATE
        Abonnement abn = new Abonnement(
                0,
                "Premium",
                new Date(),
                new Date(System.currentTimeMillis() + 86400000), // +1 jour
                29.99,
                StatutAbonnement.ACTIF,
                100,
                user.getIdUser()
        );
        abonnementService.ajouter(abn);
        assertTrue(abn.getIdAbonnement() > 0, "ID Abonnement doit être généré");

        // 2. Test READ
        Abonnement fetchedAbn = abonnementService.getOne(abn.getIdAbonnement());
        assertEquals("Premium", fetchedAbn.getTypeAbonnement(), "Le type doit correspondre");

        // 3. Test UPDATE
        abn.setTarif(39.99);
        abonnementService.modifier(abn);
        Abonnement updatedAbn = abonnementService.getOne(abn.getIdAbonnement());
        assertEquals(39.99, updatedAbn.getTarif(), "Le tarif doit être mis à jour");

        // 4. Test DELETE
        abonnementService.supprimer(abn.getIdAbonnement());
        assertNull(abonnementService.getOne(abn.getIdAbonnement()), "Abonnement doit être supprimé");

        // Nettoyage
        utilisateurService.supprimer(user.getIdUser());
    }
}