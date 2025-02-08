package entite;

public class Reponse {
    private int id;
    private int reclamationId;
    private String contenu;
    private String dateReponse;

    // Constructeur
    public Reponse(int reclamationId, String contenu, String dateReponse) {
        this.reclamationId = reclamationId;
        this.contenu = contenu;
        this.dateReponse = dateReponse;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReclamationId() {
        return reclamationId;
    }

    public void setReclamationId(int reclamationId) {
        this.reclamationId = reclamationId;
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

    // Méthode toString pour afficher les informations de la réponse
    @Override
    public String toString() {
        return "Reponse{" +
                "id=" + id +
                ", reclamationId=" + reclamationId +
                ", contenu='" + contenu + '\'' +
                ", dateReponse='" + dateReponse + '\'' +
                '}';
    }
}

