package Services;

import entities.Utilisateur;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UtilisateurServiceTest {

    private static UtilisateurService utilisateurService;

    @BeforeAll
    public static void setup() {
        utilisateurService = new UtilisateurService();
    }

    @Test
    public void testCRUDUtilisateur() {
        // 1. Test CREATE
        Utilisateur user = new Utilisateur(0, "Test", "User", "test@crud.com", "password");
        utilisateurService.ajouter(user);
        assertTrue(user.getIdUser() > 0, "L'ID doit être généré automatiquement");

        // 2. Test READ (getOne)
        Utilisateur fetchedUser = utilisateurService.getOne(user.getIdUser());
        assertNotNull(fetchedUser, "L'utilisateur doit être récupéré");
        assertEquals("Test", fetchedUser.getNom(), "Le nom doit correspondre");

        // 3. Test UPDATE
        user.setNom("UpdatedName");
        utilisateurService.modifier(user);
        Utilisateur updatedUser = utilisateurService.getOne(user.getIdUser());
        assertEquals("UpdatedName", updatedUser.getNom(), "Le nom doit être mis à jour");

        // 4. Test DELETE
        utilisateurService.supprimer(user.getIdUser());
        assertNull(utilisateurService.getOne(user.getIdUser()), "L'utilisateur doit être supprimé");
    }
}