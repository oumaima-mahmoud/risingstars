package entities;

import java.util.Date;

public class Ticket {
    private int id;
    private int reservationId;
    private Date date;
    private int numeroDePlace;
    private String codeBarre;
    private double prix;

    public Ticket() {
    }

    public Ticket(int id, int reservationId, Date date, int numeroDePlace, String codeBarre, double prix) {
        this.id = id;
        this.reservationId = reservationId;
        this.date = date;
        this.numeroDePlace = numeroDePlace;
        this.codeBarre = codeBarre;
        this.prix = prix;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getReservationId() { return reservationId; }
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public int getNumeroDePlace() { return numeroDePlace; }
    public void setNumeroDePlace(int numeroDePlace) { this.numeroDePlace = numeroDePlace; }

    public String getCodeBarre() { return codeBarre; }
    public void setCodeBarre(String codeBarre) { this.codeBarre = codeBarre; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", reservationId=" + reservationId +
                ", date=" + date +
                ", numeroDePlace=" + numeroDePlace +
                ", codeBarre='" + codeBarre + '\'' +
                ", prix=" + prix +
                '}';
    }
}
