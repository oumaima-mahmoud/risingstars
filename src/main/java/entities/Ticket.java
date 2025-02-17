package entities;

import java.util.Date;

public class Ticket {
    private int id;
    private int reservationID;
    private Date date;
    private String numPlace;
    private String codeBarre;
    private int prix;

    // Constructeur
    public Ticket(int id, int reservationID, Date date, String numPlace, String codeBarre, int prix) {
        this.id = id;
        this.reservationID = reservationID;
        this.date = date;
        this.numPlace = numPlace;
        this.codeBarre = codeBarre;
        this.prix = prix;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNumPlace() {
        return numPlace;
    }

    public void setNumPlace(String numPlace) {
        this.numPlace = numPlace;
    }

    public String getCodeBarre() {
        return codeBarre;
    }

    public void setCodeBarre(String codeBarre) {
        this.codeBarre = codeBarre;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    // Méthode toString pour afficher les détails du ticket
    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", reservationID=" + reservationID +
                ", date=" + date +
                ", numPlace='" + numPlace + '\'' +
                ", codeBarre='" + codeBarre + '\'' +
                ", prix=" + prix +
                '}';
    }
}