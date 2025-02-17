package entities;

import java.util.Date;

public class Parking {
    private int id;
    private int reservationID;
    private int intPlace;
    private StatutRes statut;
    private Date dateReser;
    private Date heureDebut;
    private Date heureFin;
    private int prix;

    // Constructeur
    public Parking(int id, int reservationID, int intPlace, StatutRes statut, Date dateReser, Date heureDebut, Date heureFin, int prix) {
        this.id = id;
        this.reservationID = reservationID;
        this.intPlace = intPlace;
        this.statut = statut;
        this.dateReser = dateReser;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
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

    public int getIntPlace() {
        return intPlace;
    }

    public void setIntPlace(int intPlace) {
        this.intPlace = intPlace;
    }

    public StatutRes getStatut() {
        return statut;
    }

    public void setStatut(StatutRes statut) {
        this.statut = statut;
    }

    public Date getDateReser() {
        return dateReser;
    }

    public void setDateReser(Date dateReser) {
        this.dateReser = dateReser;
    }

    public Date getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Date heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Date getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Date heureFin) {
        this.heureFin = heureFin;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    // Méthode toString pour afficher les détails du parking
    @Override
    public String toString() {
        return "Parking{" +
                "id=" + id +
                ", reservationID=" + reservationID +
                ", intPlace=" + intPlace +
                ", statut=" + statut +
                ", dateReser=" + dateReser +
                ", heureDebut=" + heureDebut +
                ", heureFin=" + heureFin +
                ", prix=" + prix +
                '}';
    }
}