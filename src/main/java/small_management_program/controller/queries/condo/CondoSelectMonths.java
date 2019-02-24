package small_management_program.controller.queries.condo;

import small_management_program.controller.DuplicateMap;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.model.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CondoSelectMonths extends QueryWithResults {

    @Override
    public String getQuery(){
        return "SELECT distinct month FROM condos ORDER BY month";
    }

    @Override
    public DuplicateMap<Integer, String> getResults(){
        ResultSet resultSet = super.getResultSet();
        DuplicateMap<Integer, String> ret = new DuplicateMap<>();
        try{
            while (resultSet.next()){
                ret.put(0, resultSet.getString(1));
            }
        }
        catch (SQLException exception){}

        return ret;
    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database", "Attenzione, non è stato possibile recuperare i dati dal database.");
    }
}
