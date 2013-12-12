/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.dao.DaoEquipier;
import modele.dao.DaoException;
import modele.dao.DaoPresence;
import modele.jdbc.FabriqueJdbc;
import modele.jdbc.Jdbc;
import modele.metier.CodeEtat;
import modele.metier.Equipier;
import modele.metier.Jour;
import modele.metier.ListePresences;
import modele.metier.Presence;

/**
 *
 * @author btssio
 */
public class TestDao {

    static DaoPresence daoPresence = new DaoPresence();
    static DaoEquipier daoEquipier = new DaoEquipier();

    // test de lecture des enregistrements de la table PRESENCE pour un équipier donné
    public static boolean testDaoLirePresences() throws DaoException {
        boolean ok = true;
        int idEquipier = 2;
        ListePresences desPresences = daoPresence.getListePresences(idEquipier);
        System.out.println("Présences de l'équipier dont la clef est : " + idEquipier);
        for (Entry<Jour, Presence> entree : desPresences.entrySet()) {
            Presence p = entree.getValue();
            System.out.println(p);
        }
        return ok;
    }

    // test de lecture d'un enregistrement de la table EQUIPIER
    public static boolean testDaoLireEquipier() throws DaoException {
        boolean ok = true;
        int idEquipier = 3;
        Equipier unEquipier = daoEquipier.getOne(idEquipier);
        ListePresences desPresences = daoPresence.getListePresences(idEquipier);
        unEquipier.setLesPresences(desPresences);
        System.out.println("Equipier dont la clef est : " + idEquipier + "\n" + unEquipier.versChaine());
        return ok;
    }

    // test d'ajout puis de modification d'un enregistrement de la table PRESENCE
    public static boolean testDaoMajPresence() throws DaoException {
        boolean ok = true;
        try {
            int idEquipier = 4;
            Jour date1 = new Jour("2012-11-01");
            Presence presence1 = new Presence(date1, new CodeEtat('M', "Maladie"));
            int nb = daoPresence.create(idEquipier, presence1);
            ok = ok && (nb != 0);
            presence1.setEtatPresence(new CodeEtat('F', "Formation"));
            nb = daoPresence.update(idEquipier, presence1);
            ok = ok && (nb != 0);
        } catch (ParseException ex) {
            Logger.getLogger(TestDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ok;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException, DaoException {
        System.out.println("Tests unitaires DAO");
        FabriqueJdbc.creer("ambulancesJdbc.properties");
        Jdbc.getInstance().connecter();


        System.out.println("\nTest DAO Lire Presences");
        if (testDaoLirePresences()) {
            System.out.println("+++ Réussite");
        } else {
            System.out.println("--- Echec");
        }
        System.out.println("\nTest DAO Lire Equipier");
        if (testDaoLireEquipier()) {
            System.out.println("+++ Réussite");
        } else {
            System.out.println("--- Echec");
        }
        System.out.println("\nTest DAO Maj Presence");
        if (testDaoMajPresence()) {
            System.out.println("+++ Réussite");
        } else {
            System.out.println("--- Echec");
        }

        System.out.println("\nDéconnexion");
        Jdbc.getInstance().deconnecter();

    }
}
