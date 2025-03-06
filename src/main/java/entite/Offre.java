package entite;

public class Offre {
    private int id_offre;
    private int id_user;
    private double montant;
    private String conditions;
    private String date_proposition;
    private String contact;

    // Constructor used when retrieving an Offre from the database
    public Offre(int id_offre, int id_user, double montant, String conditions, String date_proposition, String contact) {
        this.id_offre = id_offre;
        this.id_user = id_user;
        this.montant = montant;
        this.conditions = conditions;
        this.date_proposition = date_proposition;
        this.contact = contact;
    }

    // Constructor used when adding a new Offre (without id_offre since it's auto-generated)
    public Offre( int id_user, double montant, String conditions, String date_proposition, String contact) {
        this.id_user = id_user;
        this.montant = montant;
        this.conditions = conditions;
        this.date_proposition = date_proposition;
        this.contact = contact;
    }

    // Getters and Setters
    public int getId_offre() {
        return id_offre;
    }

    public void setId_offre(int id_offre) {
        this.id_offre = id_offre;
    }



    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
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

    public String getDate_proposition() {
        return date_proposition;
    }

    public void setDate_proposition(String date_proposition) {
        this.date_proposition = date_proposition;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    // toString method
    @Override
    public String toString() {
        return "Offre{" +
                "id_offre=" + id_offre +
                ", id_user=" + id_user +
                ", montant=" + montant +
                ", conditions='" + conditions + '\'' +
                ", date_proposition='" + date_proposition + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
