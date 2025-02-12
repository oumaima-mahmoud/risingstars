package gestion_satde.services;
import gestion_satde.entities.Stade;
import gestion_satde.tools.DataSource;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;


public class serviceStade implements Iservice <Stade> {
    Connection cnx ;
    PreparedStatement pst;
    public serviceStade(){
        this.cnx= DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Stade t) {
        String req = "INSERT INTO stade (nom, capacite, image) VALUES (?, ?, ?)";

        try  {
            pst= cnx.prepareStatement(req);
            pst.setString(1, t.getNom());
            pst.setDouble(2, t.getcapacite());
            pst.setString(3, t.getImage());

            pst.executeUpdate();
            System.out.println("Stade ajouté avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout du stade : " + ex.getMessage());
        }
    }


    @Override
    public void modifier(Stade t,int id) {
        String req = "UPDATE stade SET nom=?, capacite = ?, image = ? WHERE id = ?";

        try  {
            pst= cnx.prepareStatement(req);
            pst.setString(1, t.getNom());
            pst.setDouble(2, t.getcapacite());
            pst.setString(3, t.getImage());
            pst.setInt(4, id);

            pst.executeUpdate();
            System.out.println("Stade updated");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'update du stade : " + ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM stade WHERE id = ?";
        try{
            pst=cnx.prepareStatement(req);
            pst.setInt(1,id);
            pst.executeUpdate();
            System.out.println("suppresion avec succes");
        }catch(SQLException ex){
            throw new RuntimeException("Erreur lors de l'supprimer : " + ex.getMessage());
        }

    }

    @Override
    public Stade getOne(int id) {
        String req = "SELECT * FROM stade WHERE id=?";
        Stade p = null;

        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    p = new Stade();
                    p.setId(rs.getInt("id"));
                    p.setNom(rs.getString("nom"));
                    p.setcapacite(rs.getFloat("capacite"));  // Assuming 'capacite' is the correct column name
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error fetching data: " + ex.getMessage());
        }

        return p;  // Returns null if not found
    }


    @Override
    public List<Stade> getAll() {
        String req = "SELECT * FROM stade";  // No dynamic input, so no need for placeholders
        List<Stade> Stades = new ArrayList<>();

        try
         {
            pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Stade p = new Stade();
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom"));
                p.setcapacite(rs.getFloat("capacite"));  // Assuming 'capacite' is the correct column name

                Stades.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("Error fetching data: " + ex.getMessage());
        }

        return Stades;
    }


}
