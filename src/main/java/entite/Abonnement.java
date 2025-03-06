package entite;

import java.util.Date;

public class Abonnement {
    private int idAbonnement;
    private TypeAbonnement typeAbonnement;
    private Date dateDebut;
    private Date dateFin;
    private String tarif;
    private StatutAbonnement statut;
    private int pointFidelite;
    private int idUser;

    public Abonnement(int idAbonnement, TypeAbonnement typeAbonnement, Date dateDebut,
                      Date dateFin, String tarif, StatutAbonnement statut,
                      int pointFidelite, int idUser) {
        this.idAbonnement = idAbonnement;
        this.typeAbonnement = typeAbonnement;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.tarif = tarif;
        this.statut = statut;
        this.pointFidelite = pointFidelite;
        this.idUser = idUser;
    }

    // Getters & Setters
    public int getIdAbonnement() { return idAbonnement; }
    public void setIdAbonnement(int idAbonnement) { this.idAbonnement = idAbonnement; }
    public TypeAbonnement getTypeAbonnement() { return typeAbonnement; }
    public void setTypeAbonnement(TypeAbonnement typeAbonnement) { this.typeAbonnement = typeAbonnement; }
    public Date getDateDebut() { return dateDebut; }
    public void setDateDebut(Date dateDebut) { this.dateDebut = dateDebut; }
    public Date getDateFin() { return dateFin; }
    public void setDateFin(Date dateFin) { this.dateFin = dateFin; }
    public String getTarif() { return tarif; }
    public void setTarif(String tarif) { this.tarif = tarif; }
    public StatutAbonnement getStatut() { return statut; }
    public void setStatut(StatutAbonnement statut) { this.statut = statut; }
    public int getPointFidelite() { return pointFidelite; }  // Ensure this method is present
    public void setPointFidelite(int pointFidelite) { this.pointFidelite = pointFidelite; }
    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }

    @Override
    public String toString() {
        return "Abonnement{" +
                "idAbonnement=" + idAbonnement +
                ", typeAbonnement=" + typeAbonnement +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", tarif='" + tarif + '\'' +
                ", statut=" + statut +
                ", pointFidelite=" + pointFidelite +
                ", idUser=" + idUser +
                '}';
    }
}
