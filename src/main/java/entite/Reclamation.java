package entite;
public class Reclamation {
    private int id;
    private int userId;
    private String type;
    private String description;
    private String objet;
    private String dateReclamation;
    private String etat; // "en_attente", "en_cours", "termine"

    // Constructeur
    public Reclamation(int userId, String type, String description, String objet, String etat) {
        this.userId = userId;
        this.type = type;
        this.description = description;
        this.objet = objet;
        this.etat = etat;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getDateReclamation() {
        return dateReclamation;
    }

    public void setDateReclamation(String dateReclamation) {
        this.dateReclamation = dateReclamation;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    // Méthode toString pour afficher les informations de la réclamation
    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", userId=" + userId +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", objet='" + objet + '\'' +
                ", dateReclamation='" + dateReclamation + '\'' +
                ", etat='" + etat + '\'' +
                '}';
    }
}

