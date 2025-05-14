package entite;

public class Produit {
    private int idProduit;
    private String nom;
    private float prixProduit;
    private int stock;
    private String categorie;
    private String image;
    private int idUser;


    // Constructeur vide
    public Produit() {
    }
    public Produit(int idProduit, String nom, float prixProduit, int stock, String categorie, String image, int idUser) {
        this.idProduit = idProduit;
        this.nom = nom;
        this.prixProduit = prixProduit;
        this.stock = stock;
        this.categorie = categorie;
        this.image = image;
        this.idUser = idUser;
    }


    // Getters et Setters
    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getPrixProduit() {
        return prixProduit;
    }

    public void setPrixProduit(float prixProduit) {
        this.prixProduit = prixProduit;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    // toString (optionnel, utile pour le debug)
    @Override
    public String toString() {
        return "Produit{" +
                "idProduit=" + idProduit +
                ", nom='" + nom + '\'' +
                ", prixProduit=" + prixProduit +
                ", stock=" + stock +
                ", categorie='" + categorie + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
