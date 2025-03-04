package entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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

    // Method to return the display name as a StringProperty for table binding
    public StringProperty getDisplayNameProperty() {
        return new SimpleStringProperty(displayName);
    }
}
