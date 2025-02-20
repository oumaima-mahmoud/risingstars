package gestion_satde.entities;
import java.time.LocalDate;

import java.util.Date;

public class evenement {
    private int id_evenement;
    private String nom;
    private String type;
    private LocalDate date;
    private String oraganisateur;
    private double nombre_participant;
    private int id_stade;
    public evenement(){

    }

    public evenement(String nom, String type, LocalDate date, String oraganisateur, double nombre_participant,int id_stade) {
        this.nom = nom;
        this.type = type;
        this.date = date;
        this.oraganisateur = oraganisateur;
        this.nombre_participant = nombre_participant;
        this.id_stade = id_stade;
    }

    public evenement(int id_evenement, String nom , LocalDate date, String oraganisateur, double nombre_participant,String type,int id_stade) {
        this.id_evenement = id_evenement;
        this.nom = nom;
        this.oraganisateur = oraganisateur;
        this.nombre_participant = nombre_participant;
        this.date = date;
        this.type = type;
        this.id_stade = id_stade;
    }


    public int getId() {
        return id_evenement;
    }

    public void setId(int id) {
        this.id_evenement = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return  date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getOraganisateur() {
        return oraganisateur;
    }

    public void setOraganisateur(String oraganisateur) {
        this.oraganisateur = oraganisateur;
    }

    public double getNombre_participant() {
        return nombre_participant;
    }

    public void setNombre_participant(double nombre_participant) {
        this.nombre_participant = nombre_participant;
    }
    public int getId_stade() {
        return id_stade;
    }
    public void setId_stade(int id_stade) {
        this.id_stade = id_stade;
    }

    @Override
    public String toString() {
        return "evenement{" +
                "nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", date=" + date +
                ", oraganisateur='" + oraganisateur + '\'' +
                ", nombre_participant='" + nombre_participant + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.id_evenement;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final evenement other = (evenement) obj;
        if (this.id_evenement != other.id_evenement) {
            return false;
        }
        return true;
    }

}
