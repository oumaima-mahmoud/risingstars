package entities;

public class Utilisateur {
    private int idUser;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private Role role;
    private String numeroTelephone;
    private StatutAbonnement statutAbonnement;
    private TypeAbonnement typeAbonnement;
    private int pointsFidelite;

    // Updated constructor for matching parameters
    public Utilisateur(int idUser, String nom, String prenom, String email, String motDePasse, Role role,
                       String numeroTelephone, StatutAbonnement statutAbonnement, TypeAbonnement typeAbonnement, int pointsFidelite) {
        this.idUser = idUser;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
        this.numeroTelephone = numeroTelephone;
        this.statutAbonnement = statutAbonnement;
        this.typeAbonnement = typeAbonnement;
        this.pointsFidelite = pointsFidelite;
    }

    // Getters and setters for all fields
    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public String getNumeroTelephone() { return numeroTelephone; }
    public void setNumeroTelephone(String numeroTelephone) { this.numeroTelephone = numeroTelephone; }
    public StatutAbonnement getStatutAbonnement() { return statutAbonnement; }
    public void setStatutAbonnement(StatutAbonnement statutAbonnement) { this.statutAbonnement = statutAbonnement; }
    public TypeAbonnement getTypeAbonnement() { return typeAbonnement; }
    public void setTypeAbonnement(TypeAbonnement typeAbonnement) { this.typeAbonnement = typeAbonnement; }
    public int getPointsFidelite() { return pointsFidelite; }
    public void setPointsFidelite(int pointsFidelite) { this.pointsFidelite = pointsFidelite; }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "idUser=" + idUser +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", statutAbonnement=" + statutAbonnement +
                ", pointsFidelite=" + pointsFidelite +
                '}';
    }
}
