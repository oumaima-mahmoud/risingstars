package entite;

public class Publicite {

    private int id_publicite;
    private String titre;
    private String description;
    private String media_url;
    private String date; // This should represent a date, might consider using java.time.LocalDate
    private String statut; // "en attente", "validée", "refusée"
    private int id_offre; // Foreign key that relates to the "offre" table

    // Constructor for retrieving a Publicite from the database
    public Publicite(int id_publicite, String titre, String description, String media_url, String date, String statut, int id_offre) {
        this.id_publicite = id_publicite;
        this.titre = titre;
        this.description = description;
        this.media_url = media_url;
        this.date = date;
        this.statut = statut;
        this.id_offre = id_offre;
    }

    // Constructor for adding a new Publicite (without id_publicite)
    public Publicite(String titre, String description, String media_url, String date, String statut, int id_offre) {
        this.titre = titre;
        this.description = description;
        this.media_url = media_url;
        this.date = date;
        this.statut = statut;
        this.id_offre = id_offre;
    }

    // Getters and Setters
    public int getId_publicite() {
        return id_publicite;
    }

    public void setId_publicite(int id_publicite) {
        this.id_publicite = id_publicite;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getId_offre() {
        return id_offre;
    }

    public void setId_offre(int id_offre) {
        this.id_offre = id_offre;
    }

    // toString method for easier debugging or logging
    @Override
    public String toString() {
        return "Publicite{" +
                "id_publicite=" + id_publicite +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", media_url='" + media_url + '\'' +
                ", date='" + date + '\'' +
                ", statut='" + statut + '\'' +
                ", id_offre=" + id_offre +
                '}';
    }
}
