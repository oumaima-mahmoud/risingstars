package tn.edu.esprit.entities;

import java.util.Date;

public class Panier {
    private int idPanier;
    private Date dateCreation;
    private double total;
    private String etat;

    public Panier(){

    }

    public Panier(Date dateCreation, double total, String etat){
        this.dateCreation = dateCreation;
        this.total = total;
        this.etat = etat;
    }

    public Panier(int idPanier, Date dateCreation, double total, String etat){
        this.idPanier = idPanier;
        this.dateCreation = dateCreation;
        this.etat = etat;
        this.total = total;
    }

    public int getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(int idPanier) {
        this.idPanier = idPanier;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public String toString(){
        return "Panier{" + "idPanier" + idPanier + ", dateCreation" + dateCreation + ", total" + total + ", etat" + etat + "}";
    }

    @Override
    public int hashCode(){
        int hash = 7;
        hash = 17 * hash + this.idPanier;
        return hash;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if (obj == null){
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Panier other = (Panier) obj;
        if (this.idPanier != other.idPanier) {
            return false;
        }
        return true;
    }
}
