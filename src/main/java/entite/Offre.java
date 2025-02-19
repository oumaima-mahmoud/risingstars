package entite;

import java.time.LocalDateTime;

public class Offre {
    private int id_offre;
    private int id_publicite;
    private int id_sponsor;
    private double montant;
    private String conditions;
    private LocalDateTime date_proposition;
    private String contact;

    // Constructeur complet
    public Offre(int id_offre, int id_publicite, int id_sponsor, double montant, String conditions, LocalDateTime date_proposition, String contact) {
        this.id_offre = id_offre;
        this.id_publicite = id_publicite;
        this.id_sponsor = id_sponsor;
        this.montant = montant;
        this.conditions = conditions;
        this.date_proposition = date_proposition;
        this.contact = contact;
    }

    // Constructeur sans id_offre (utile pour l'insertion)
    public Offre(int id_publicite, int id_sponsor, double montant, String conditions, String contact) {
        this.id_publicite = id_publicite;
        this.id_sponsor = id_sponsor;
        this.montant = montant;
        this.conditions = conditions;
        this.contact = contact;
        this.date_proposition = LocalDateTime.now(); // Par défaut, la date de proposition est maintenant
    }

    // Constructeur vide (utile pour certaines manipulations)
    public Offre() {}

    public Offre(int id_offre, double montant, String conditions, LocalDateTime now, String contact, int i, Publicite idPublicite) {
    }

    // Getters et Setters
    public int getId_offre() {
        return id_offre;
    }

    public void setId_offre(int id_offre) {
        this.id_offre = id_offre;
    }

    public int getId_publicite() {
        return id_publicite;
    }

    public void setId_publicite(int id_publicite) {
        this.id_publicite = id_publicite;
    }

    public int getId_sponsor() {
        return id_sponsor;
    }

    public void setId_sponsor(int id_sponsor) {
        this.id_sponsor = id_sponsor;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public LocalDateTime getDate_proposition() {
        return date_proposition;
    }

    public void setDate_proposition(LocalDateTime date_proposition) {
        this.date_proposition = date_proposition;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    // Méthode toString pour afficher les informations de l'offre
    @Override
    public String toString() {
        return "Offre{" +
                "id_offre=" + id_offre +
                ", id_publicite=" + id_publicite +
                ", id_sponsor=" + id_sponsor +
                ", montant=" + montant +
                ", conditions='" + conditions + '\'' +
                ", contact='" + contact + '\'' +
                ", date_proposition=" + date_proposition +
                '}';
    }
}
