/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.metier.CodeEtat;
import modele.metier.Equipier;
import modele.metier.Jour;
import modele.metier.Presence;

/**
 *
 * @author btssio
 */
public class TestEquipier {

    public static boolean testInstancier() {
        boolean ok = true;
        // Instancier un équipier
        Equipier equipier1 = new Equipier(1, "HUGO", "Victor", false);
        // afficher son état
        System.out.println(equipier1.versChaine());
        // Vérifier son contenu à l'aide des accesseurs
        ok = (equipier1.getId() == 1) && (equipier1.getNom().equals("HUGO") && (equipier1.getPrenom().equals("Victor")) && !equipier1.isDimanche());
        return ok;
    }

    public static boolean testPresencesEquipier() {
        boolean ok = true;
        try {
            // JEU d'ESSAI
            // trois présences distinctes
            Jour date1 = new Jour("2012-11-01");
            Presence presence1 = new Presence(date1, new CodeEtat('P', "Permanence"));
            Jour date2 = new Jour("2012-12-02");
            Presence presence2 = new Presence(date2,  new CodeEtat('M', "Maladie"));
            Jour date3 = new Jour("2012-02-03");
            Presence presence3 = new Presence(date3,  new CodeEtat('V', "Vacances"));
            // une présence en doublon sur le jour pour tester l'ajout
            Jour date4 = new Jour("2012-11-01"); // date identique à la date n°1
            Presence presence4 = new Presence(date4,  new CodeEtat('T', "Travail"));
            // une date non présente pour tester la recherche
            Jour dateAbsente = new Jour("2012-01-10");

            Equipier equipier1 = new Equipier(1, "HUGO", "Victor", false);

            // ajout d'éléments : cas nominaux 
            ok = ok && equipier1.ajouterUnePresence(presence1);
            ok = ok && equipier1.ajouterUnePresence(presence2);
            ok = ok && equipier1.ajouterUnePresence(presence3);
            // ajout d'éléments : cas d'un doublon
            ok = ok && !equipier1.ajouterUnePresence(presence4);

            System.out.println(" Avant recherche et suppression : \n" + equipier1.versChaine());

            // rechercher un élément appartenant à la liste
            ok = ok && equipier1.rechercherUnePresence(date2).equals(presence2);
            // rechercher un élément n'appartenant pas à la liste
            ok = ok && (equipier1.rechercherUnePresence(dateAbsente) == null);
            // supprimer un élément appartenant à la liste
            ok = ok && equipier1.supprimerUnePresence(date2);
            // supprimer un élément n'appartenant pas à la liste
            ok = ok && !equipier1.supprimerUnePresence(dateAbsente);
            System.out.println(" Après recherche et suppression : \n" + equipier1.versChaine());
        } catch (ParseException ex) {
            Logger.getLogger(TestEquipier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ok;

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Tests unitaires classe Equipier");
        System.out.println("\nTest instanciation");

        if (testInstancier()) {
            System.out.println("+++ Réussite");
        } else {
            System.out.println("--- Echec");
        }
        System.out.println("\nTest liste présences");

        if (testPresencesEquipier()) {
            System.out.println("+++ Réussite");
        } else {
            System.out.println("--- Echec");
        }


    }
}
