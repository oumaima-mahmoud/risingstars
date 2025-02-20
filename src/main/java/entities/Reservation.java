package entities;

import java.sql.Date;

public class Reservation {
    private int id;
    private int place;
    private int utilisateurID;
    private Date dateReservation;
    private StatutRes statut;
    private float prixTotal;

    // Constructeur
    public Reservation(int id, int place, int utilisateurID, Date dateReservation, StatutRes statut, float prixTotal) {
        this.id = id;
        this.place = place;
        this.utilisateurID = utilisateurID;
        this.dateReservation = dateReservation;
        this.statut = statut;
        this.prixTotal = prixTotal;
    }

    public Reservation(int id, Ticket ticket, Parking parkingSelectionne, StatutRes statutRes, Date date) {
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getUtilisateurID() {
        return utilisateurID;
    }

    public void setUtilisateurID(int utilisateurID) {
        this.utilisateurID = utilisateurID;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public StatutRes getStatut() {
        return statut;
    }

    public void setStatut(StatutRes statut) {
        this.statut = statut;
    }

    public float getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(float prixTotal) {
        this.prixTotal = prixTotal;
    }

    public int getUtilisationID() {
        return this.utilisateurID;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", place=" + place +
                ", utilisateurID=" + utilisateurID +
                ", dateReservation=" + dateReservation +
                ", statut=" + statut +
                ", prixTotal=" + prixTotal +
                '}';
    }
}