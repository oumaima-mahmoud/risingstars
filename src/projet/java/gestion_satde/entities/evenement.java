package gestion_satde.entities;

import java.util.Date;

public class evenement {
    private int id;
    private String nom;
    private String type;
    private Date date;
    private String oraganisateur;
    private String nombre_participant;
    public evenement(){

    }

    public evenement(String nom, String type, Date date, String oraganisateur, String nombre_participant) {
        this.nom = nom;
        this.type = type;
        this.date = date;
        this.oraganisateur = oraganisateur;
        this.nombre_participant = nombre_participant;
    }

    public evenement(int id, String nom, String prenom , Date date, String oraganisateur, String nombre_participant,String type) {
        this.id = id;
        this.nom = nom;
        this.oraganisateur = oraganisateur;
        this.nombre_participant = nombre_participant;
        this.date = date;
        this.type = type;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOraganisateur() {
        return oraganisateur;
    }

    public void setOraganisateur(String oraganisateur) {
        this.oraganisateur = oraganisateur;
    }

    public String getNombre_participant() {
        return nombre_participant;
    }

    public void setNombre_participant(String nombre_participant) {
        this.nombre_participant = nombre_participant;
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
        hash = 17 * hash + this.id;
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
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
