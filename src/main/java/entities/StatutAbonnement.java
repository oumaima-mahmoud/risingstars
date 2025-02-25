package entities;

public enum StatutAbonnement {
    ACTIF("Actif"),
    SUSPENDU("Suspendu"),
    EXPIRE("Expiré");

    private final String displayName;

    StatutAbonnement(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
