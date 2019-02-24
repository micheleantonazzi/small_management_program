package small_management_program.controller.queries.condo;

import small_management_program.controller.DuplicateMap;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.model.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CondoSelectMonthsCondos extends QueryWithResults {

    @Override
    public String getQuery(){
        return "SELECT  id_condo, month, name FROM condos ORDER BY id_condo";
    }

    @Override
    public DuplicateMap<Integer, String> getResults(){
        ResultSet resultSet = super.getResultSet();
        DuplicateMap<Integer, String> ret = new DuplicateMap<>();
        try {
            while (resultSet.next()){
                int month = resultSet.getInt(2);
                ret.put(month, resultSet.getString(1));
                ret.put(month, resultSet.getString(3));
            }
        }
        catch (SQLException exception){}
        return ret;
    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database", "Attenzione, non è stato possibile recuperare i dati desiderati dal database.");
    }
}
