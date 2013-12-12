package modele.dao;

import java.util.Collection;

/**
 * Modéle de classe DAO 
 *
 * @version 21 novembre 2013
 * @author nbourgeois
 */
public interface DaoInterface<TMetier, TIdMetier> {

    /**
     * create
     *
     * @param objet métier contenant les données nécessaires à l'ajout
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
     */
    public int create(TMetier objetMetier) throws Exception;

    /**
     * getOne
     *
     * @param identifiant métier de l'objet recherché
     * @return objet métier trouvé, ou null sinon
     */
    public TMetier getOne(TIdMetier idMetier) throws Exception;

    /**
     * getAll
     *
     * @return collection de l'ensemble des objets métier disponibles; elle peut
     * étre vide
     */
    public Collection<TMetier> getAll() throws Exception;

//    /**
//     * retrieveMany
//     * @param objet contenant les critéres de recherche, null si aucun critére (retourner tous les objets)
//     * @return collection des objets métier trouvé; elle peut étre vide
//     */
//    public Collection<TMetier> retrieveMany(String criteres) throws Exception;
    /**
     * update
     *
     * @param identifiant métier de l'objet é modifier
     * @param objet métier modifié
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
     */
    public int update(TIdMetier idMetier, TMetier objetMetier) throws Exception;

    /**
     * delete
     *
     * @param identifiant métier de l'objet é détruire
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
     */
    public int delete(TIdMetier idMetier) throws Exception;
}
