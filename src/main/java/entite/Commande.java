package entite;

import javafx.beans.property.*;

import java.sql.Date;

public class Commande {
    private final IntegerProperty idCommande;
    private final IntegerProperty quantite;
    private final StringProperty dateCommande;
    private final DoubleProperty prix;
    private final IntegerProperty idPanier;

    // ✅ Constructeur sans paramètres
    public Commande() {
        this.idCommande = new SimpleIntegerProperty();
        this.quantite = new SimpleIntegerProperty();
        this.dateCommande = new SimpleStringProperty();
        this.prix = new SimpleDoubleProperty();
        this.idPanier = new SimpleIntegerProperty();
    }

    // ✅ Constructeur avec paramètres (sans idCommande)
    public Commande(int quantite, Date dateCommande, double prix, int idPanier) {
        this();
        setQuantite(quantite);
        setDateCommande(dateCommande);
        setPrix(prix);
        setIdPanier(idPanier);
    }

    // ✅ Constructeur avec idCommande
    public Commande(int idCommande, int quantite, Date dateCommande, double prix, int idPanier) {
        this(quantite, dateCommande, prix, idPanier);
        setIdCommande(idCommande);
    }

    // ✅ Getters & Setters avec JavaFX Property

    public int getIdCommande() {
        return idCommande.get();
    }

    public void setIdCommande(int idCommande) {
        this.idCommande.set(idCommande);
    }

    public IntegerProperty idCommandeProperty() {
        return idCommande;
    }

    public int getQuantite() {
        return quantite.get();
    }

    public void setQuantite(int quantite) {
        this.quantite.set(quantite);
    }

    public IntegerProperty quantiteProperty() {
        return quantite;
    }

    public String getDateCommande() {
        return dateCommande.get();
    }

    public void setDateCommande(Date dateCommande) {
        // Convertir la date en format String
        this.dateCommande.set(dateCommande.toString());  // ou un autre format si nécessaire
    }

    public StringProperty dateCommandeProperty() {
        return dateCommande;
    }

    public double getPrix() {
        return prix.get();
    }

    public void setPrix(double prix) {
        this.prix.set(prix);
    }

    public DoubleProperty prixProperty() {
        return prix;
    }

    public int getIdPanier() {
        return idPanier.get();
    }

    public void setIdPanier(int idPanier) {
        this.idPanier.set(idPanier);
    }

    public IntegerProperty idPanierProperty() {
        return idPanier;
    }



    @Override
    public String toString() {
        return "Commande{" +
                "idCommande=" + getIdCommande() +
                ", quantite=" + getQuantite() +
                ", dateCommande=" + getDateCommande() +
                ", prix=" + getPrix() +
                ", idPanier=" + getIdPanier() +
                '}';
    }

    @Override
    public int hashCode() {
        return idCommande.get();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Commande other = (Commande) obj;
        return idCommande.get() == other.getIdCommande();
    }
}
