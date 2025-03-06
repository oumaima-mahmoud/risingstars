package tools;

import entite.Role;
import entite.Utilisateur;

public class SessionManager {
    private static Utilisateur currentUser;

    public static void setCurrentUser(Utilisateur user) {
        currentUser = user;
    }

    public static Utilisateur getCurrentUser() {
        return currentUser;
    }

    public static int getCurrentUserId() {
        return currentUser != null ? currentUser.getIdUser() : -1;
    }

    public static boolean isAdmin() {
        return currentUser != null && currentUser.getRole() == Role.ADMIN;
    }

    public static void logout() {
        currentUser = null;
    }
}
