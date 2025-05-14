package services;

import entite.Produit;

import java.util.List;

public interface IServiceProduit {
//    void ajouterProduit(Produit produit);
//    void modifierProduit(Produit produit);
//    void supprimerProduit(int idProduit);
//    Produit getProduitById(int idProduit);
void supprimerProduit(int idProduit);

    List<Produit> getAllProduits();
}
