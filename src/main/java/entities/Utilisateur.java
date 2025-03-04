package entities;

import javafx.beans.property.*;

public class Utilisateur {
    private final IntegerProperty idUser;
    private final StringProperty nom;
    private final StringProperty prenom;
    private final StringProperty email;
    private final StringProperty motDePasse;
    private final ObjectProperty<Role> role;
    private final StringProperty numeroTelephone;
    private final ObjectProperty<StatutAbonnement> statutAbonnement;
    private final ObjectProperty<TypeAbonnement> typeAbonnement;
    private final IntegerProperty pointsFidelite;

    // Constructor
    public Utilisateur(int idUser, String nom, String prenom, String email, String motDePasse, Role role,
                       String numeroTelephone, StatutAbonnement statutAbonnement, TypeAbonnement typeAbonnement, int pointsFidelite) {
        this.idUser = new SimpleIntegerProperty(idUser);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.email = new SimpleStringProperty(email);
        this.motDePasse = new SimpleStringProperty(motDePasse);
        this.role = new SimpleObjectProperty<>(role);
        this.numeroTelephone = new SimpleStringProperty(numeroTelephone);
        this.statutAbonnement = new SimpleObjectProperty<>(statutAbonnement);
        this.typeAbonnement = new SimpleObjectProperty<>(typeAbonnement);
        this.pointsFidelite = new SimpleIntegerProperty(pointsFidelite);
    }

    // Getters and setters (using Properties)
    public IntegerProperty idUserProperty() { return idUser; }
    public int getIdUser() { return idUser.get(); }
    public void setIdUser(int idUser) { this.idUser.set(idUser); }

    public StringProperty nomProperty() { return nom; }
    public String getNom() { return nom.get(); }
    public void setNom(String nom) { this.nom.set(nom); }

    public StringProperty prenomProperty() { return prenom; }
    public String getPrenom() { return prenom.get(); }
    public void setPrenom(String prenom) { this.prenom.set(prenom); }

    public StringProperty emailProperty() { return email; }
    public String getEmail() { return email.get(); }
    public void setEmail(String email) { this.email.set(email); }

    public StringProperty motDePasseProperty() { return motDePasse; }
    public String getMotDePasse() { return motDePasse.get(); }
    public void setMotDePasse(String motDePasse) { this.motDePasse.set(motDePasse); }

    public ObjectProperty<Role> roleProperty() { return role; }
    public Role getRole() { return role.get(); }
    public void setRole(Role role) { this.role.set(role); }

    public StringProperty numeroTelephoneProperty() { return numeroTelephone; }
    public String getNumeroTelephone() { return numeroTelephone.get(); }
    public void setNumeroTelephone(String numeroTelephone) { this.numeroTelephone.set(numeroTelephone); }

    public ObjectProperty<StatutAbonnement> statutAbonnementProperty() { return statutAbonnement; }
    public StatutAbonnement getStatutAbonnement() { return statutAbonnement.get(); }
    public void setStatutAbonnement(StatutAbonnement statutAbonnement) { this.statutAbonnement.set(statutAbonnement); }

    public ObjectProperty<TypeAbonnement> typeAbonnementProperty() { return typeAbonnement; }
    public TypeAbonnement getTypeAbonnement() { return typeAbonnement.get(); }
    public void setTypeAbonnement(TypeAbonnement typeAbonnement) { this.typeAbonnement.set(typeAbonnement); }

    public IntegerProperty pointsFideliteProperty() { return pointsFidelite; }
    public int getPointsFidelite() { return pointsFidelite.get(); }
    public void setPointsFidelite(int pointsFidelite) { this.pointsFidelite.set(pointsFidelite); }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "idUser=" + idUser.get() +
                ", nom='" + nom.get() + '\'' +
                ", prenom='" + prenom.get() + '\'' +
                ", email='" + email.get() + '\'' +
                ", role=" + role.get() +
                ", statutAbonnement=" + statutAbonnement.get() +
                ", pointsFidelite=" + pointsFidelite.get() +
                '}';
    }
}
