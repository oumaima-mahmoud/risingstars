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

    public static TypeAbonnement fromString(String typeAbonnementString) {
        try {
            return TypeAbonnement.valueOf(typeAbonnementString.toUpperCase());
        } catch (IllegalArgumentException e) {
            return STANDARD;
        }
    }
}