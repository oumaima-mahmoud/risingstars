package entite;

public class Stade {
    private int id_stade;
    private String nom;
    private Double capacite;
    private String image;
    private double latitude;
    private double longitude;

    public Stade(){

    }

    public Stade(String nom, double capacite, String image, double latitude, double longitude) {
        this.nom = nom;
        this.capacite = capacite;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public Stade(int id_stade, String nom, double capacite,String image) {
        this.id_stade = id_stade;
        this.nom = nom;
        this.capacite = capacite;
        this.image=image;
    }
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public int getId() {
        return id_stade;
    }

    public void setId(int id) {
        this.id_stade = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getcapacite() {
        return capacite;
    }

    public void setcapacite(double capacite) {
        this.capacite = capacite;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Stade{" + "nom=" + nom + ", capacite=" + capacite + ", image=" + image + ", latitude=" + latitude + ", longitude=" + longitude+ '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.id_stade;
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
        if (this.id_stade != other.id_stade) {
            return false;
        }
        return true;
    }

}
