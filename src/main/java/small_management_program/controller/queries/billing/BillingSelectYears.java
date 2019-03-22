
package small_management_program.controller.queries.billing;

import small_management_program.controller.DuplicateMap;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.model.database.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Dato un condominio ritorna gli anni delle fatture disponibili.
 * Questa query viene usata in StageAddBilling quando è aperto per modificare le fatture.
 *
 * @author Michele Antonazzi
 */
public class BillingSelectYears extends QueryWithResults {

    private int idCondo;

    public BillingSelectYears(int idCondo){
        this.idCondo = idCondo;
    }

    @Override
    public String getQuery(){
        return "SELECT year FROM billings WHERE id_condo = " + this.idCondo;
    }

    @Override
    public DuplicateMap<Integer, String> getResults(){
        DuplicateMap<Integer, String> ret = new DuplicateMap<>();
        ResultSet resultSet = super.getResultSet();
        try{
            while(resultSet.next()){
                ret.put(0, resultSet.getString(1));
            }

        }catch (SQLException exception){}

        return ret;
    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database", "Attenzione, non \u00E8 stato possibile recuperare i dati dal database.");
    }
}
