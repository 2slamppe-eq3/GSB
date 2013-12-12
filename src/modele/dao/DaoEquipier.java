package modele.dao;

import modele.metier.*;
import modele.jdbc.Jdbc;
import java.sql.*;
import java.util.*;

/**
 * Classe DAO pour la classe Equipier
 *
 * @version 22 novembre 2013
 * @author nbourgeois
 */
public class DaoEquipier implements DaoInterface<Equipier, Integer> {

    /**
     * Non implémenté
     */
    @Override
    public int create(Equipier unEquipier) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Lire un enregistrement d'après son identifiant
     *
     * @param identifiant métier de l'objet recherché
     * @return objet métier trouvé, ou null sinon
     * @throws Exception
     */
    @Override
    public Equipier getOne(Integer idEquipier) throws DaoException {
        Equipier result = null;
        ResultSet rs = null;
        // préparer la requête
        String requete = "SELECT * FROM EQUIPIER WHERE ID_EQUIPIER=?";
        try {
            PreparedStatement ps = Jdbc.getInstance().getConnexion().prepareStatement(requete);
            ps.setInt(1, idEquipier);
            rs = ps.executeQuery();
            if (rs.next()) {
                result = chargerUnEnregistrement(rs);
            }
        } catch (SQLException ex) {
            throw new modele.dao.DaoException("DaoEquipier::getOne : erreur requete SELECT : " + ex.getMessage());
        }
        return (result);
    }

    /**
     * getAll
     *
     * @return ArrayList de l'ensemble des occurences d'equipiers de la table
     * EQUIPIER
     */
    @Override
    public ArrayList<Equipier> getAll() throws DaoException {
        ArrayList<Equipier> result = new ArrayList<Equipier>();
        ResultSet rs;
        // préparer la requête
        String requete = "SELECT * FROM EQUIPIER";
        try {
            PreparedStatement ps = Jdbc.getInstance().getConnexion().prepareStatement(requete);
            rs = ps.executeQuery();
            // Charger les enregistrements dans la collection
            while (rs.next()) {
                Equipier unEquipier = chargerUnEnregistrement(rs);
                result.add(unEquipier);
            }
        } catch (SQLException ex) {
            throw new modele.dao.DaoException("DaoEquipier::getAll : erreur requete SELECT : " + ex.getMessage());
        }
        return result;
    }

    /**
     * Non implémenté
     */
    @Override
    public int update(Integer idMetier, Equipier objetMetier) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Non implémenté
     */
    @Override
    public int delete(Integer idMetier) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //----------------------------------------------------------------------
    //  Méthodes privées
    //----------------------------------------------------------------------
    /**
     * chargerUnEnregistrementEquipier Instancie un objet equipier avec les
     * valeurs lues dans la base de données La jointure avec la table PRESENCE
     * n'est pas effectuée
     *
     * @param rs enregistrement de la table Equipier courant
     * @return un objet Equipier, dont la liste des "présences" n'est pas
     * renseignée
     * @throws DaoException
     */
    private Equipier chargerUnEnregistrement(ResultSet rs) throws DaoException {
        try {
            Equipier equipier = new Equipier();
            equipier.setId(rs.getInt("ID_EQUIPIER"));
            equipier.setNom(rs.getString("NOM"));
            equipier.setPrenom(rs.getString("PRENOM"));
            if (rs.getInt("VOLONTAIRE_DIMANCHE") == 1) {
                equipier.setDimanche(true);
            } else {
                equipier.setDimanche(false);
            }
            return equipier;
        } catch (SQLException ex) {
            throw new DaoException("DaoEquipier - chargerUnEnregistrement : pb JDBC\n" + ex.getMessage());
        }
    }
}
