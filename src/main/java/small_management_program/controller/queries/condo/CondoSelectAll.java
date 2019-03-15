package small_management_program.controller.queries.condo;

import small_management_program.controller.DuplicateMap;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.model.database.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CondoSelectAll extends QueryWithResults {

    @Override
    public String getQuery(){
        return "SELECT * FROM condos";
    }

    @Override
    public DuplicateMap<Integer, String> getResults(){
        ResultSet resultSet = getResultSet();
        DuplicateMap<Integer, String> ret = new DuplicateMap<>();
        try {
            while(resultSet.next()){
                int id = resultSet.getInt(1);
                ret.put(id, resultSet.getString(2));
                ret.put(id, String.valueOf(resultSet.getInt(3)));
                ret.put(id, String.valueOf(resultSet.getInt(4)));
                ret.put(id, resultSet.getString(5));
                ret.put(id, resultSet.getString(6));
                ret.put(id, resultSet.getString(7));
                ret.put(id, resultSet.getString(8));
                ret.put(id, resultSet.getString(9));
                ret.put(id, String.valueOf(resultSet.getInt(10)));
            }
        }
        catch (SQLException exception){}

        return ret;
    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Operazione non riuscita", "Attenzione, non \u00E8 stato possibile recuperare i dati dei condomini");
    }
}
