package entite;

import java.time.LocalDateTime;

public class Parking {
    private int id;
    private int reservationId; // Foreign key reference to Reservation
    private int nbrPlace;
    private String statut; // "libre", "reserve", "occupe"
    private LocalDateTime dateReservation;
    private LocalDateTime heureDebut;
    private LocalDateTime heureFin;
    private float prix;

    // Constructor for retrieving from DB
    public Parking(int id, int reservationId, int nbrPlace, String statut, LocalDateTime dateReservation,
                   LocalDateTime heureDebut, LocalDateTime heureFin, float prix) {
        this.id = id;
        this.reservationId = reservationId;
        this.nbrPlace = nbrPlace;
        this.statut = statut;
        this.dateReservation = dateReservation;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.prix = prix;
    }
    public Parking() {
    }
    // Constructor without ID (for new entry)
    public Parking(int reservationId, int nbrPlace, String statut, LocalDateTime dateReservation,
                   LocalDateTime heureDebut, LocalDateTime heureFin, float prix) {
        this.reservationId = reservationId;
        this.nbrPlace = nbrPlace;
        this.statut = statut;
        this.dateReservation = dateReservation;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.prix = prix;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getReservationId() { return reservationId; }
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }

    public int getNbrPlace() { return nbrPlace; }
    public void setNbrPlace(int nbrPlace) { this.nbrPlace = nbrPlace; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public LocalDateTime getDateReservation() { return dateReservation; }
    public void setDateReservation(LocalDateTime dateReservation) { this.dateReservation = dateReservation; }

    public LocalDateTime getHeureDebut() { return heureDebut; }
    public void setHeureDebut(LocalDateTime heureDebut) { this.heureDebut = heureDebut; }

    public LocalDateTime getHeureFin() { return heureFin; }
    public void setHeureFin(LocalDateTime heureFin) { this.heureFin = heureFin; }

    public float getPrix() { return prix; }
    public void setPrix(float prix) { this.prix = prix; }

    @Override
    public String toString() {
        return "Parking{" +
                "id=" + id +
                ", reservationId=" + reservationId +
                ", nbrPlace=" + nbrPlace +
                ", statut='" + statut + '\'' +
                ", dateReservation=" + dateReservation +
                ", heureDebut=" + heureDebut +
                ", heureFin=" + heureFin +
                ", prix=" + prix +
                '}';
    }
}
