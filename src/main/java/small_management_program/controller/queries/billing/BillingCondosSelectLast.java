package small_management_program.controller.queries.billing;

import small_management_program.controller.DuplicateMap;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.model.database.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Ritorna, per ogni condominio, l'ultima fattura ancora da saldare.
 *
 *
 * @author Michele Antonazzi
 *
 */

public class BillingCondosSelectLast extends QueryWithResults {



    @Override
    public String getQuery(){
        return "SELECT c.id_condo, c.name, c.month, lb.year, lb.total, b.month, b.price " +
                "FROM (condos c LEFT JOIN last_billings lb ON c.id_condo = lb.id_condo) LEFT JOIN bills b ON lb.id_condo = b.id_condo AND lb.year = b.year " +
                "ORDER BY c.id_condo, b.month";
    }

    @Override
    public DuplicateMap<Integer, String> getResults(){
        DuplicateMap<Integer, String> ret = new DuplicateMap<>();
        ResultSet resultSet = super.getResultSet();
        int idCondo = -1;
        try{
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                if(idCondo != id){
                    idCondo = id;
                    ret.put(id, resultSet.getString(2));
                    ret.put(id, resultSet.getString(3));
                    ret.put(id, resultSet.getString(4));
                    ret.put(id, resultSet.getString(5));
                }
                String billMonth = resultSet.getString(6);
                if(billMonth != null){
                    ret.put(id, billMonth);
                    ret.put(id, resultSet.getString(7));
                }
            }
        }catch (SQLException exception){}

        return ret;
    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database", "Attenzione, non \u00E8 stato possibile recuperare le informazioni di fatturazione dal database.");
    }
}
