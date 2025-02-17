package entities;

public enum StatutAbonnement {
    ACTIF("Actif"),
    SUSPENDU("Suspendu"),
    EXPIRE("Expiré");

    private final String statut;

    StatutAbonnement(String statut) {
        this.statut = statut;
    }

    public String getStatut() {
        return statut;
    }

    @Override
    public String toString() {
        return statut;
    }
}