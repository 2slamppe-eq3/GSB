package modele.metier;

import java.util.Calendar;

/**
 *
 * @author nbourgeois
 */
public class Presence {

    private Jour jour;
    private CodeEtat etatPresence;

    public Presence() {
        this.jour = new Jour(Calendar.getInstance().getTime().getTime());
        this.etatPresence = null;
    }

    public Presence(Jour jour, CodeEtat etatPresence) {
        this.jour = jour;
        this.etatPresence = etatPresence;
    }

    //----------------------------------------------------------------------
    //  toString
    //  Accesseurs et Mutateurs
    //----------------------------------------------------------------------    
    @Override
    public String toString() {
        return "Presence{" + "jour=" + jour + ", codePresence=" + etatPresence + '}';
    }

    public CodeEtat getEtatPresence() {
        return etatPresence;
    }

    public void setEtatPresence(CodeEtat etatPresence) {
        this.etatPresence = etatPresence;
    }

   

    public Jour getJour() {
        return jour;
    }

    public void setJour(Jour jour) {
        this.jour = jour;
    }
}
