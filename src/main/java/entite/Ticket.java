package entite;

import java.time.LocalDateTime;

public class Ticket {
    private int id;
    private LocalDateTime date;
    private int numPlace;
    private String codeBarre;
    private float prix;
    private int idStade; // Only stadium ID is needed now

    // Constructor for retrieving from DB
    public Ticket(int id, LocalDateTime date, int numPlace, String codeBarre, float prix, int idStade) {
        this.id = id;
        this.date = date;
        this.numPlace = numPlace;
        this.codeBarre = codeBarre;
        this.prix = prix;
        this.idStade = idStade;
    }

    // Constructor without ID (for new entry)
    public Ticket(LocalDateTime date, int numPlace, String codeBarre, float prix, int idStade) {
        this.date = date;
        this.numPlace = numPlace;
        this.codeBarre = codeBarre;
        this.prix = prix;
        this.idStade = idStade;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public int getNumPlace() { return numPlace; }
    public void setNumPlace(int numPlace) { this.numPlace = numPlace; }

    public String getCodeBarre() { return codeBarre; }
    public void setCodeBarre(String codeBarre) { this.codeBarre = codeBarre; }

    public float getPrix() { return prix; }
    public void setPrix(float prix) { this.prix = prix; }

    public int getIdStade() { return idStade; }
    public void setIdStade(int idStade) { this.idStade = idStade; }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", date=" + date +
                ", numPlace=" + numPlace +
                ", codeBarre='" + codeBarre + '\'' +
                ", prix=" + prix +
                ", idStade=" + idStade +
                '}';
    }
}
