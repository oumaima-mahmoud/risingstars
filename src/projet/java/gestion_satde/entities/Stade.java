package gestion_satde.entities;

public class Stade {
    private int id;
    private String nom;
    private float capacite;
    private String image;

    public Stade(){

    }

    public Stade(String nom, float capacite) {
        this.nom = nom;
        this.capacite = capacite;

    }

    public Stade(int id, String nom, float capacite,String image) {
        this.id = id;
        this.nom = nom;
        this.capacite = capacite;
        this.image=image;
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

    public float getcapacite() {
        return capacite;
    }

    public void setcapacite(float capacite) {
        this.capacite = capacite;
    }

    @Override
    public String toString() {
        return "Stade{" + "nom=" + nom + ", capacite=" + capacite + '}';
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
        final Stade other = (Stade) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
