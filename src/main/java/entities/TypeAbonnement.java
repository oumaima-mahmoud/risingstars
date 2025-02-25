package entities;

public enum TypeAbonnement {
    STANDARD("Standard"),
    PREMIUM("Premium"),
    VIP("VIP");

    private final String displayName;

    TypeAbonnement(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
