package entite;

import java.time.LocalDate;

public class Reservation {
    private long id;
    private int place;
    private int idUser; // User ID (foreign key)
    private LocalDate dateReservation;
    private String statut; // Possible values: "en attente", "validee", "refusee"
    private float prixTotal;
    private int idTicket; // Updated field from DB
    private Integer idParking; // Nullable field

    // Constructor for retrieving from DB (with ID)
    public Reservation(long id, int place, int idUser, LocalDate dateReservation,
                       String statut, float prixTotal, int idTicket, Integer idParking) {
        this.id = id;
        this.place = place;
        this.idUser = idUser;
        this.dateReservation = dateReservation;
        this.statut = statut;
        this.prixTotal = prixTotal;
        this.idTicket = idTicket;
        this.idParking = idParking;
    }

    // Constructor without ID (for new entry)
    public Reservation(int place, int idUser, LocalDate dateReservation,
                       String statut, float prixTotal, int idTicket, Integer idParking) {
        this.place = place;
        this.idUser = idUser;
        this.dateReservation = dateReservation;
        this.statut = statut;
        this.prixTotal = prixTotal;
        this.idTicket = idTicket;
        this.idParking = idParking;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public int getPlace() { return place; }
    public void setPlace(int place) { this.place = place; }

    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }

    public LocalDate getDateReservation() { return dateReservation; }
    public void setDateReservation(LocalDate dateReservation) { this.dateReservation = dateReservation; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public float getPrixTotal() { return prixTotal; }
    public void setPrixTotal(float prixTotal) { this.prixTotal = prixTotal; }

    public int getIdTicket() { return idTicket; }
    public void setIdTicket(int idTicket) { this.idTicket = idTicket; }

    public Integer getIdParking() { return idParking; }
    public void setIdParking(Integer idParking) { this.idParking = idParking; }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", place=" + place +
                ", idUser=" + idUser +
                ", dateReservation=" + dateReservation +
                ", statut='" + statut + '\'' +
                ", prixTotal=" + prixTotal +
                ", idTicket=" + idTicket +
                ", idParking=" + idParking +
                '}';
    }
}
