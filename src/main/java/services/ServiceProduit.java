package services;

import entite.Produit;
import tools.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceProduit implements IServiceProduit{
    private Connection cnx;

    public ServiceProduit() {
        this.cnx = DataSource.getInstance().getConnection();
    }






    @Override
    public List<Produit> getAllProduits() {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT * FROM produit";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                produits.add(new Produit(
                        rs.getInt("idproduit"),
                        rs.getString("nom"),
                        rs.getFloat("prixproduit"),
                        rs.getInt("stock"),
                        rs.getString("categorie"),
                        rs.getString("image"),
                        rs.getInt("id_user")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produits;
    }
    public void supprimerProduit(int idProduit) {
        String sql = "DELETE FROM produit WHERE idproduit = ?";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setInt(1, idProduit);
            pst.executeUpdate();
            System.out.println("✅ Produit supprimé avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void ajouterProduit(Produit p) {
        String sql = "INSERT INTO produit(nom, prixproduit, stock, categorie, image, idUser) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setString(1, p.getNom());
            pst.setFloat(2, p.getPrixProduit());
            pst.setInt(3, p.getStock());
            pst.setString(4, p.getCategorie());
            pst.setString(5, p.getImage());
            pst.setInt(6, p.getIdUser()); // ✅ Ajoute l'id de l'utilisateur
            pst.executeUpdate();
            System.out.println("✅ Produit ajouté avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifierProduit(Produit p) {
        String sql = "UPDATE produit SET nom = ?, prixproduit = ?, stock = ?, categorie = ?, image = ? WHERE idproduit = ?";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setString(1, p.getNom());
            pst.setFloat(2, p.getPrixProduit());
            pst.setInt(3, p.getStock());
            pst.setString(4, p.getCategorie());
            pst.setString(5, p.getImage());
            pst.setInt(6, p.getIdProduit()); // WHERE idproduit = ?
            pst.executeUpdate();
            System.out.println("✅ Produit modifié avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
