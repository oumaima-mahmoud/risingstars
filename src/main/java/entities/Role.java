package entities;

public enum Role {
    GESTIONNAIRE("Gestionnaire de stade"),
    SPECTATEUR("Spectateur"),
    SPONSOR("Sponsor"),
    ANNONCEUR("Annonceur");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
