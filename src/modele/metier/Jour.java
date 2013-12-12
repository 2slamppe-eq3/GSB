package modele.metier;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Gestion de date simplifiée :
 * la comparaison ainsi que le hash-code ignorent la partie heure
 * objectifs : 
 * - permettre la gestion d'une HashMap<Jour,Object>
 * Avec un type Date d'origine, l'heure rend aléatoire la comparaison de 2 dates identiques
 * - fournir des méthodes de conversion vers java.sql.Date et java.util.Date
 * @author nbourgeois
 * @version 06/04/2012
 * 
 */
public class Jour extends java.util.Date{
    
    public static final String FORMAT_FRANCAIS= "dd/MM/yyyy";
    public static final String FORMAT_ISO= "yyyy-MM-dd";
    
    SimpleDateFormat sdf= new SimpleDateFormat(FORMAT_ISO);


    public Jour(String strDate) throws ParseException {
        this.setTime(sdf.parse(strDate).getTime());
    }
        
    public Jour(long date) {
        super(date);
    }

    public Jour() {
    }

    @Override
    public boolean equals(Object obj) {
        boolean ok= false;
        if (obj == null) {
            ok=false;
        }else if (getClass() != obj.getClass()) {
            ok=false;
        }else{
            final Jour other = (Jour) obj;
            ok= sdf.format(this).equals(sdf.format(other));            
        }        
        return ok;
    }
    
    @Override
    public int hashCode() {
        return sdf.format(this).hashCode();
    }

    @Override
    public String toString() {
        return sdf.format(this);
    }
    
    public java.sql.Date toSqlDate(){
        return new java.sql.Date(this.getTime());
    }

    public java.util.Date toUtilDate(){
        return new java.util.Date(this.getTime());
    }
    
    public void setDate(java.util.Date uneDate){
        this.setTime(uneDate.getTime());
    }
    
    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }
    
    
}
