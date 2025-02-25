package entities;

import java.util.Date;

public class Parking {
    private int id;
    private int reservationId;
    private int nbrPlace;
    private StatutParking statut;
    private Date dateReservation;
    private String heureDebut;
    private String heureFin;
    private double prix;

    public Parking() {
    }

    public Parking(int id, int reservationId, int nbrPlace, String statut, Date dateReservation, String heureDebut, String heureFin, double prix) {
        this.id = id;
        this.reservationId = reservationId;
        this.nbrPlace = nbrPlace;
        this.statut = StatutParking.valueOf(statut);
        this.dateReservation = dateReservation;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.prix = prix;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getReservationId() { return reservationId; }
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }

    public int getNbrPlace() { return nbrPlace; }
    public void setNbrPlace(int nbrPlace) { this.nbrPlace = nbrPlace; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = StatutParking.valueOf(statut); }

    public Date getDateReservation() { return dateReservation; }
    public void setDateReservation(Date dateReservation) { this.dateReservation = dateReservation; }

    public String getHeureDebut() { return heureDebut; }
    public void setHeureDebut(String heureDebut) { this.heureDebut = heureDebut; }

    public String getHeureFin() { return heureFin; }
    public void setHeureFin(String heureFin) { this.heureFin = heureFin; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    @Override
    public String toString() {
        return "Parking{" +
                "id=" + id +
                ", reservationId=" + reservationId +
                ", nbrPlace=" + nbrPlace +
                ", statut='" + statut + '\'' +
                ", dateReservation=" + dateReservation +
                ", heureDebut='" + heureDebut + '\'' +
                ", heureFin='" + heureFin + '\'' +
                ", prix=" + prix +
                '}';
    }
}
