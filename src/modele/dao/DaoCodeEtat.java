package modele.dao;

import java.sql.*;
import java.util.ArrayList;
import modele.jdbc.Jdbc;
import modele.metier.CodeEtat;

/**
 * Classe DAO pour la classe Equipier
 *
 * @version 22 novembre 2013
 * @author nbourgeois
 */
public class DaoCodeEtat implements DaoInterface<CodeEtat, Character> {

    @Override
    public int create(CodeEtat objetMetier) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CodeEtat getOne(Character idMetier) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<CodeEtat> getAll() throws DaoException   {
        ArrayList<CodeEtat> result = new ArrayList<CodeEtat>();
        ResultSet rs = null;
        // préparer la requête
        String requete =  "SELECT * FROM CODE_ETAT";
        try {
        PreparedStatement ps = Jdbc.getInstance().getConnexion().prepareStatement(requete);
            rs = ps.executeQuery();
       // Charger les enregistrements dans la collection
        while (rs.next()) {
            CodeEtat unCodeEtat = new CodeEtat(rs.getString("CODE").charAt(0), rs.getString("LIBELLE"));
           result.add(unCodeEtat);
        }
        } catch (SQLException ex) {
            throw new modele.dao.DaoException("DaoCodeEtat - getAll : erreur JDBC : " + ex.getMessage());
        }
         return result;
    }

    @Override
    public int update(Character idMetier, CodeEtat objetMetier) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(Character idMetier) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
