/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.toedter.calendar.JDateChooser;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.dao.DaoException;
import modele.metier.Jour;

/**
 *
 * @author btssio
 */
public class TestJDateChooser {


    // test de lecture des enregistrements de la table PRESENCE pour un équipier donné
    public static boolean testJDateChooser() throws DaoException {
        boolean ok = true;
        Calendar cal= Calendar.getInstance();
        cal.set(2012, 3, 6);
        Jour maDate = new Jour(cal.getTime().getTime());       
        cal.set(2012, 3, 6);
        Jour maDate2 = new Jour(cal.getTime().getTime()); 
        JDateChooser jdc= new JDateChooser(maDate); 
        Jour maDateJdc= new Jour(jdc.getDate().getTime());
        System.out.println(maDate.getClass()+" : maDate : "+maDate);
        System.out.println(maDateJdc.getClass()+" : maDateJdc : "+maDateJdc);
        System.out.println(maDate2.getClass()+" : maDate2 : "+maDate2);
        System.out.println("d1 == d2 ?"+(maDate.equals(maDate2)));
//        ok= ok && (p!=null);
        ok= ok && (maDate.equals(maDate2));
        
        return ok;
    }
    
    
    

    public static void main(String[] args) {
        try {
            System.out.println("Tests unitaires JDateChooser");
            
            if (testJDateChooser()) {
                System.out.println("+++ Réussite");
            } else {
                System.out.println("--- Echec");
            }
 
 
        } catch (DaoException ex) {
            Logger.getLogger(TestJDateChooser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
