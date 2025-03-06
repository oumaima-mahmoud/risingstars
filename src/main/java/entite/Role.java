package entite;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public enum Role {
    GESTIONNAIRE("Gestionnaire"),
    SPECTATEUR("Spectateur"),
    SPONSOR("Sponsor"),
    ANNONCEUR("Annonceur"),
    ADMIN("Admin"),
    UNKNOWN("Rôle Inconnu");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public StringProperty getDisplayNameProperty() {
        return new SimpleStringProperty(displayName);
    }

    public static Role fromString(String roleString) {
        // Loop through all the enum values to find the matching display name
        for (Role role : Role.values()) {
            if (role.getDisplayName().equalsIgnoreCase(roleString)) {
                return role;
            }
        }
        // If no match, return UNKNOWN
        return UNKNOWN;
    }
}
