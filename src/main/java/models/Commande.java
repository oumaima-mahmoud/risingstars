package models;

import java.sql.Date;

public class Commande {
    private int idCommande;
    private int quantite;
    private Date dateCommande;
    private double prix;
    private int idPanier;

    public Commande() {

    }

    public Commande(int quantite, Date dateCommande, double prix, int idPanier) {
        this.quantite = quantite;
        this.dateCommande = dateCommande;
        this.prix = prix;
        this.idPanier = idPanier;
    }

    public Commande(int idCommande, int quantite, Date dateCommande, double prix, int idPanier) {
        this.idCommande = idCommande;
        this.quantite = quantite;
        this.dateCommande = dateCommande;
        this.prix = prix;
        this.idPanier = idPanier;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(int idPanier) {
        this.idPanier = idPanier;
    }

    @Override
    public String toString() {
        return "Commande{" + "idCommande" + idCommande + ",quantite" + quantite + ",prix" + prix + ",idPanier" + idPanier + '}';
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
        final Commande other = (Commande) obj;
        if (this.idCommande != other.idCommande) {
            return false;
        }
        return true;
    }
}
