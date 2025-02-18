package entite;

public class Publicite {

    private int id_publicite;
    private String titre;
    private String description;
    private String media_url;
    private String  date;
    private String statut;//"en attente" "validee" "refusee"
    private int id_annonceur;

    // Constructeur
    public Publicite(int idPublicite, String string, String titre , String description , String media_url, String statut, int id_annonceur) {
        this.titre = titre;
        this.description = description;
        this.media_url = media_url;
        this.statut = statut;
        this.id_annonceur = id_annonceur;
    }

    // Getters et Setters
    public int getId_publicite() {
        return id_publicite;
    }

    public void setId_publicite(int id_publicite) {
        this.id_publicite = id_publicite;
    }

    public int getId_annonceur() {
        return id_annonceur;
    }

    public void setId_annonceur(int id_annonceur) {
        this.id_annonceur = id_annonceur;
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

    // Méthode toString pour afficher les informations de la réclamation
    @Override
    public String toString() {
        return "publicite{" +
                "id=" + id_publicite +
                ", id_annonceur=" + id_annonceur +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", media_url='" + media_url + '\'' +
                ", date='" + date + '\'' +
                ", statut='" + statut + '\'' +
                '}';
    }
}
