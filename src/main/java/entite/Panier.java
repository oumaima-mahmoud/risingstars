package entite;

import javafx.beans.property.*;

import java.util.Date;
import java.util.List;

public class Panier {

    private int idPanier;
    private int idproduit;  // Ajout de la clé étrangère idProduit
    private int id_user;     // Ajout de la clé étrangère idUser
    private StringProperty dateCreation;  // Utilisation de StringProperty pour la date
    private DoubleProperty total;  // Utilisation de DoubleProperty pour le total
    private StringProperty etat;  // Utilisation de StringProperty pour l'état
    private List<Commande> commandes;

    // Constructeur sans paramètres
    public Panier() {
        this.dateCreation = new SimpleStringProperty();
        this.total = new SimpleDoubleProperty();
        this.etat = new SimpleStringProperty();
        this.idproduit = idproduit;
        this.id_user =id_user;
    }

    // Constructeur avec paramètres
    public Panier(Date dateCreation, double total, String etat, int idproduit, int id_user) {
        this();
        setDateCreation(dateCreation);
        setTotal(total);
        setEtat(etat);
        setIdproduit(idproduit);  // Initialiser idProduit
        setIdUser(id_user);        // Initialiser idUser
    }

    // Constructeur complet
    public Panier(int idPanier, Date dateCreation, double total, String etat, int idproduit, int idUser) {
        this(dateCreation, total, etat, idproduit, idUser);
        this.idPanier = idPanier;
    }

    // Getter et Setter pour idPanier
    public int getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(int idPanier) {
        this.idPanier = idPanier;
    }

    // Getter et Setter pour idProduit
    public int getIdproduit() {
        return idproduit;
    }

    public void setIdproduit(int idproduit) {
        this.idproduit = idproduit;
    }

    // Getter et Setter pour idUser
    public int getIdUser() {
        return id_user;
    }

    public void setIdUser(int idUser) {
        this.id_user = idUser;
    }

    // Getter et Setter pour dateCreation
    public String getDateCreation() {
        return dateCreation.get();
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation.set(dateCreation.toString());  // Conversion de Date à String
    }

    public StringProperty dateCreationProperty() {
        return dateCreation;
    }

    // Getter et Setter pour total
    public double getTotal() {
        return total.get();
    }

    public void setTotal(double total) {
        this.total.set(total);
    }

    public DoubleProperty totalProperty() {
        return total;
    }

    // Getter et Setter pour etat
    public String getEtat() {
        return etat.get();
    }

    public void setEtat(String etat) {
        this.etat.set(etat);
    }

    public StringProperty etatProperty() {
        return etat;
    }

    // Getter et Setter pour commandes
    public List<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "idPanier=" + idPanier +
                ", dateCreation=" + dateCreation +
                ", total=" + total +
                ", etat='" + etat + '\'' +
                ", idproduit=" + idproduit +
                ", idUser=" + id_user +
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.idPanier;
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
        final Panier other = (Panier) obj;
        return this.idPanier == other.idPanier;
    }
}
