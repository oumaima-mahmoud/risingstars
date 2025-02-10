package tn.edu.esprit.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tn.edu.esprit.entities.Panier;
import tn.edu.esprit.tools.DataSource;

public class ServicePanier {
    private Connection cnx;

    public ServicePanier() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    public void ajouter(Panier t) {
        try {
            String req = "INSERT INTO panier (dateCreation, total, etat) VALUES ('"
                    + t.getDateCreation() + "', '" + t.getTotal() + "', '" + t.getEtat() + "')";
            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifier(Panier t) {
        // Implémentation future
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void supprimer(int idPanier) {
        // Implémentation future
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Panier> getAll() {
        String req = "SELECT * FROM panier";
        List<Panier> paniers = new ArrayList<>();

        try {
            Statement stm = this.cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);

            while (rs.next()) {
                Panier p = new Panier();
                p.setIdPanier(rs.getInt("idPanier"));
                p.setDateCreation(rs.getDate("dateCreation"));
                p.setTotal(rs.getDouble("total"));
                p.setEtat(rs.getString("etat"));

                paniers.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return paniers;
    }
}