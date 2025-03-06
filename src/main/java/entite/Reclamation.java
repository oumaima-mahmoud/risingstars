package entite;

public class Reclamation {
    private int id;
    private int userId;
    private String type;
    private String description;
    private String objet;
    private String dateReclamation;
    private String etat; // "en_attente", "en_cours", "termine"
    private String phoneNumber; // New field

    // Constructor with parameters
    public Reclamation(int userId, String type, String description, String objet, String etat, String phoneNumber) {
        this.userId = userId;
        this.type = type;
        this.description = description;
        this.objet = objet;
        this.etat = etat;
        this.phoneNumber = phoneNumber;
    }

    // No-argument constructor (added)
    public Reclamation() {
        // Default values (optional)
        this.userId = 0;
        this.type = "";
        this.description = "";
        this.objet = "";
        this.etat = "en_attente";  // Default state
        this.dateReclamation = "";
        this.phoneNumber = ""; // Default phone number
    }

    // Getters and Setters
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

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
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}