package entite;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Publicite {

    private int id_publicite;
    private String titre;
    private String description;
    private String media_url;
    private LocalDate date;
    private String statut; // "en attente", "validée", "refusée"
    private int id_annonceur;

    // Constructeur pour créer une nouvelle publicité
    public Publicite(int id_publicite, String titre, String description, String media_url, LocalDate date, String statut, int id_annonceur) {
        this.id_publicite = id_publicite;
        this.titre = titre;
        this.description = description;
        this.media_url = media_url;
        this.date = date;
        this.statut = statut;
        this.id_annonceur = id_annonceur;
    }

    // Constructeur avec date sous forme de String (ex: depuis la base de données)
    public Publicite(int id_publicite, String titre, String description, String media_url, String date, String statut, int id_annonceur) {
        this.id_publicite = id_publicite;
        this.titre = titre;
        this.description = description;
        this.media_url = media_url;
        this.statut = statut;
        this.id_annonceur = id_annonceur;

        // Gestion sécurisée de la conversion String -> LocalDate
        try {
            this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            this.date = null; // Valeur par défaut en cas d'erreur de conversion
        }
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDateString() {
        return date != null ? date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    // Méthode toString
    @Override
    public String toString() {
        return "Publicite{" +
                "id_publicite=" + id_publicite +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", media_url='" + media_url + '\'' +
                ", date=" + getDateString() +
                ", statut='" + statut + '\'' +
                ", id_annonceur=" + id_annonceur +
                '}';
    }
}
