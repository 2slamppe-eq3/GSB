package controleur;

import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import modele.dao.*;
import modele.metier.*;
import vue.VuePresence;

/**
 * Contrôleur de la fenêtre VuePresence
 *
 * @author nbourgeois
 * @version 1 20 novembre 2013
 */
public class CtrlPresence extends CtrlAbstrait {
    
   private DaoPresence daoPresence = new DaoPresence();
   private DaoEquipier daoEquipier = new DaoEquipier();
   private DaoCodeEtat daoCodeEtat= new DaoCodeEtat();


    public CtrlPresence(CtrlPrincipal ctrlPrincipal) {
        super(ctrlPrincipal);
        vue = new VuePresence(this);
        actualiser();
    }

    public final void actualiser() {
        try {
            chargerListeEquipiers();
            chargerLesCodesEtat();
        } catch (DaoException ex) {
            JOptionPane.showMessageDialog(getVue(), "CtrlPresence - actualiser - " + ex.getMessage(), "Saisie des présences", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * presenceEnregistrer réaction au clic sur le bouton VALIDER de la vue
     * VuePresences
     */
    public void presenceEnregistrer() {
        // Déclarations de variables locales
        int nb = 0; // valeur de retour de l'opération de mise à jour
        String msg = ""; // message à afficher à l'issue de la mise à jour
        int typeMsg = 0;
        // Lire et contrôler les données du formulaire
        // récupérer l'Equipier sélectionné
        Equipier equipierSelect = (Equipier) getVue().getjComboBoxEquipier().getSelectedItem();
        Jour dateSelect = new Jour(getVue().getjDateChooserDatePresence().getDate().getTime());
        CodeEtat etatPresenceSelect = (CodeEtat) getVue().getjComboBoxEtatPresence().getSelectedItem();
        if (equipierSelect == null || dateSelect == null || etatPresenceSelect == null) {
            //Saisie incomplète
            msg = "Saisie incomplète";
            typeMsg = JOptionPane.WARNING_MESSAGE;
        } else {
            try {
                // n° d'identification de cet équipier
                int id = equipierSelect.getId();
                // récupérer la liste des présences actuelles pour cet équipier
                equipierSelect.setLesPresences(daoPresence.getListePresences(id));
                // rechercher la date saisie dans cette liste
                Presence presenceRecherchee = equipierSelect.rechercherUnePresence(dateSelect);
                if (presenceRecherchee != null) {
                    // si elle est déjà présente, la mettre à jour
                    presenceRecherchee.setEtatPresence(etatPresenceSelect);
                    nb = daoPresence.update(id, presenceRecherchee);
                    msg = "Présence mise à jour";
                    typeMsg = JOptionPane.INFORMATION_MESSAGE;
                } else {
                    // sinon, l'ajouter
                    presenceRecherchee = new Presence(dateSelect, etatPresenceSelect);
                    nb = daoPresence.create(id, presenceRecherchee);
                    msg = "Présence ajoutée";
                    typeMsg = JOptionPane.INFORMATION_MESSAGE;
                }
                // On vérifie qu'au moins un enregistrement a été ajouté ou modifié
                if (nb == 0) {
                    msg = "La mise à jour a échoué";
                    typeMsg = JOptionPane.WARNING_MESSAGE;
                }

            } catch (DaoException ex) {
                msg = "CtrlPresence - presenceEnregistrer() - " + ex.getMessage();
                typeMsg = JOptionPane.ERROR_MESSAGE;
            }
            JOptionPane.showMessageDialog(getVue(), msg, "Saisie des présences", typeMsg);
        }
    }

    /**
     * presenceAnnuler réaction au clic sur le bouton ANNULER de la vue Le
     * contrôle est rendu au contrôleur frontal
     */
    public void presenceQuitter() {
        this.getCtrlPrincipal().action(EnumAction.PRESENCE_QUITTER);
    }

    public void presenceAfficher() {
        String msg = ""; // message à afficher en cas d'erreur
        // récupérer les valeurs saisies
        if (getVue() != null) {
            Equipier equipierSelect = (Equipier) getVue().getjComboBoxEquipier().getSelectedItem();
            Date dateSaisie = getVue().getjDateChooserDatePresence().getDate();
            if (equipierSelect != null || dateSaisie != null) {
                Jour dateSelect = new Jour(dateSaisie.getTime());
                try {
                    // n° d'identification de cet équipier
                    int id = equipierSelect.getId();
                    // récupérer la liste des présences actuelles pour cet équipier
                    equipierSelect.setLesPresences(daoPresence.getListePresences(id));
                    // rechercher la date saisie dans cette liste
                    Presence presenceRecherchee = equipierSelect.rechercherUnePresence(dateSelect);
                    if (presenceRecherchee != null) {
                        // si elle est déjà présente, mettre à jour la vue
                        // code état concerné
                        CodeEtat ce = presenceRecherchee.getEtatPresence();
                        this.getVue().getModeleJComboBoxEtatPresence().setSelectedItem(ce);
//                        System.out.println("état : " + ce.toString());
                    }
                } catch (DaoException ex) {
                    msg = "CtrlPresence - presenceAfficher() - " + ex.getMessage();
                    JOptionPane.showMessageDialog(vue, msg, "Saisie des présences", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * chargerListeEquipiers renseigner le modèle du composant jComboBoxEquipier
     * à partir de la base de données
     *
     * @throws DaoException
     */
    private void chargerListeEquipiers() throws DaoException {
        List<Equipier> desEquipiers = daoEquipier.getAll();
        getVue().getModeleJComboBoxEquipier().removeAllElements();
        for (Equipier unEquipier : desEquipiers) {
            getVue().getModeleJComboBoxEquipier().addElement(unEquipier);
        }
    }

    /**
     * chargerLesCodesEtat renseigner le modèle du composant
     * jComboBoxEtatPresence à partir de la base de données
     *
     * @throws DaoException
     */
    private void chargerLesCodesEtat() throws DaoException {
        List<CodeEtat> desCodesEtat = daoCodeEtat.getAll();
        getVue().getModeleJComboBoxEtatPresence().removeAllElements();
        for (CodeEtat unCodeEtat : desCodesEtat) {
            getVue().getModeleJComboBoxEtatPresence().addElement(unCodeEtat);
        }
    }

    @Override
    public VuePresence getVue() {
        return (VuePresence) vue;
    }
    
}
