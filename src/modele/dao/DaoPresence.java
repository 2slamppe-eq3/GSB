package modele.dao;

import java.sql.*;
import java.util.Collection;
import modele.jdbc.Jdbc;
import modele.metier.*;

/**
 * Classe DAO pour la classe Equipier
 *
 * @version 22 novembre 2013
 * @author nbourgeois
 */
public class DaoPresence implements DaoInterface<Presence, Integer> {

    @Override
    public int create(Presence objetMetier) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param idEquipier indentifiant de l'équipier auquel la présence est
     * ajouté
     * @param unePresence présence à ajouter
     * @return nombre d'enregistrements insérés (rowCount) : 1 ou 0
     * @throws Exception
     */
    public int create(int idEquipier, Presence unePresence) throws DaoException {
        int result = 0;
        String requete = "INSERT INTO PRESENCE (ID_EQUIPIER, JOUR, CODE) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = Jdbc.getInstance().getConnexion().prepareStatement(requete);
            ps.setInt(1, idEquipier);
            ps.setDate(2, unePresence.getJour().toSqlDate());
            ps.setString(3, String.valueOf((unePresence.getEtatPresence()).getCode()));
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("DaoPresence - create : pb JDBC\n" + ex.getMessage());
        }
        return result;
    }

    @Override
    public Presence getOne(Integer idMetier) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Presence> getAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Charger tous les enregistrements relatifs à un équipoer
     *
     * @param idEquipier identifiant de l'équipier
     * @return collection des présences concernant cet équipier
     * @throws Exception
     */
    public ListePresences getListePresences(Integer idEquipier) throws DaoException {
        ListePresences result = new ListePresences();
        String requete = "SELECT * FROM PRESENCE "
                + " INNER JOIN CODE_ETAT ON PRESENCE.CODE= CODE_ETAT.CODE"
                + " WHERE ID_EQUIPIER=? ";
        try {
            PreparedStatement ps = Jdbc.getInstance().getConnexion().prepareStatement(requete);
            ps.setInt(1, idEquipier);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Presence unePresence = chargerUnEnregistrement(rs);
                result.put(unePresence.getJour(), unePresence);
            }

        } catch (Exception ex) {
            throw new DaoException("DAO - getListePresences pour un équipier : pb JDBC\n" + ex.getMessage());
        }
        return result;
    }

    /**
     * Charger tous les enregistrements
     *
     * @return collection des présences concernant cet équipier
     * @throws Exception
     */
   public ListePresences getListePresences() throws DaoException {
        ListePresences result = new ListePresences();
        String requete = "SELECT * FROM PRESENCE "
                + " INNER JOIN CODE_ETAT ON PRESENCE.CODE= CODE_ETAT.CODE";
        try {
            PreparedStatement ps = Jdbc.getInstance().getConnexion().prepareStatement(requete);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Presence unePresence = chargerUnEnregistrement(rs);
                result.put(unePresence.getJour(), unePresence);
            }

        } catch (Exception ex) {
            throw new DaoException("DAO - getListePresences : pb JDBC\n" + ex.getMessage());
        }
        return result;
    }
    
    
    @Override
    public int update(Integer idEquipier, Presence unePresence) throws DaoException {
        int result = 0;
        String requete = "UPDATE PRESENCE SET CODE=?  WHERE ID_EQUIPIER=? AND JOUR=? ";
        try {
            PreparedStatement ps = Jdbc.getInstance().getConnexion().prepareStatement(requete);
            ps.setString(1, String.valueOf((unePresence.getEtatPresence()).getCode()));
            ps.setInt(2, idEquipier);
            ps.setDate(3, unePresence.getJour().toSqlDate());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("DaoPresence - update : pb JDBC\n" + ex.getMessage());
        }
        return result;
    }

    @Override
    public int delete(Integer idMetier) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //----------------------------------------------------------------------
    //  Méthodes privées
    //----------------------------------------------------------------------
    /**
     * chargerUnEnregistrement Instancie un objet Presence avec les valeurs lues
     * dans la base de données Le ResultSet rs contient les colonnes issues de
     * la jointure avec la table CODE_ETAT
     *
     * @param rs enregistrement courant issu de la jointure entre les tables
     * PRESENCE et CODE_ETAT
     * @return un objet Presence, avec son objet CodeEtat
     * @throws DaoException
     */
    private Presence chargerUnEnregistrement(ResultSet rs) throws DaoException {
        try {
            Presence presence = new Presence();
            presence.setJour(new Jour(rs.getDate("JOUR").getTime())); // il faut convertir la sql.Date en Jour
//            char code = rs.getString("PRESENCE.CODE").charAt(0);
//            String lib = rs.getString("CODE_ETAT.LIBELLE");
            char code = rs.getString("CODE").charAt(0);
            String lib = rs.getString("LIBELLE");
            presence.setEtatPresence(new CodeEtat(code, lib));
            return presence;
        } catch (SQLException ex) {
            throw new DaoException("DaoPresence - chargerUnEnregistrement : pb JDBC\n" + ex.getMessage());
        }

    }
}
