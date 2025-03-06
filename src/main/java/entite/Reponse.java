package entite;

public class Reponse {
    private int id;
    private int idReclamation;
    private String contenu;
    private String dateReponse;
    private String type; // "Information", "Demande de précisions", "Résolution"
    private String fichierJoint; // Path to an attachment
    private int priorite; // Priority level (1 = Low, 2 = Medium, 3 = High)

    // Constructors
    public Reponse() {}

    public Reponse(int idReclamation, String contenu, String dateReponse, String type, String fichierJoint, int priorite) {
        this.idReclamation = idReclamation;
        this.contenu = contenu;
        this.dateReponse = dateReponse;
        this.type = type;
        this.fichierJoint = fichierJoint;
        this.priorite = priorite;
    }

    public Reponse(int id, int idReclamation, String contenu, String dateReponse, String type, String fichierJoint, int priorite) {
        this.id = id;
        this.idReclamation = idReclamation;
        this.contenu = contenu;
        this.dateReponse = dateReponse;
        this.type = type;
        this.fichierJoint = fichierJoint;
        this.priorite = priorite;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdReclamation() {
        return idReclamation;
    }

    public void setIdReclamation(int idReclamation) {
        this.idReclamation = idReclamation;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getDateReponse() {
        return dateReponse;
    }

    public void setDateReponse(String dateReponse) {
        this.dateReponse = dateReponse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFichierJoint() {
        return fichierJoint;
    }

    public void setFichierJoint(String fichierJoint) {
        this.fichierJoint = fichierJoint;
    }

    public int getPriorite() {
        return priorite;
    }

    public void setPriorite(int priorite) {
        this.priorite = priorite;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "id=" + id +
                ", idReclamation=" + idReclamation +
                ", contenu='" + contenu + '\'' +
                ", dateReponse='" + dateReponse + '\'' +
                ", type='" + type + '\'' +
                ", fichierJoint='" + fichierJoint + '\'' +
                ", priorite=" + priorite +
                '}';
    }
}
