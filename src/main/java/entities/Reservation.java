package entities;

import java.util.Date;

public class Reservation {
    private int id;
    private int userId;
    private String place;
    private Date dateReservation;
    private StatutReservation statut;
    private double prixTotal;

    public Reservation() {
    }

    public Reservation(int id, int userId, String place, Date dateReservation, String statut, double prixTotal) {
        this.id = id;
        this.userId = userId;
        this.place = place;
        this.dateReservation = dateReservation;
        this.statut = StatutReservation.valueOf(statut);
        this.prixTotal = prixTotal;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }

    public Date getDateReservation() { return dateReservation; }
    public void setDateReservation(Date dateReservation) { this.dateReservation = dateReservation; }

    public StatutReservation getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = StatutReservation.valueOf(statut); }

    public double getPrixTotal() { return prixTotal; }
    public void setPrixTotal(double prixTotal) { this.prixTotal = prixTotal; }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", userId=" + userId +
                ", place='" + place + '\'' +
                ", dateReservation=" + dateReservation +
                ", statut='" + statut + '\'' +
                ", prixTotal=" + prixTotal +
                '}';
    }
}
