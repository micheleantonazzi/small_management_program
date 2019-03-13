package small_management_program.controller.queries.administrator;

import small_management_program.controller.DuplicateMap;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.model.database.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministratorGetOne extends QueryWithResults {

    private int id;

    public AdministratorGetOne(int id){
        this.id = id;
    }

    @Override
    public DuplicateMap<Integer, String> getResults() {
        ResultSet resultSet = getResultSet();
        DuplicateMap<Integer, String> ret = new DuplicateMap<>();
        try{
            resultSet.next();
            int id = resultSet.getInt(1);
            ret.put(id, resultSet.getString(2));
            ret.put(id, resultSet.getString(3));
            ret.put(id, resultSet.getString(4));
        }
        catch (SQLException exception){}
        return ret;
    }

    @Override
    public String getQuery() {
        return "SELECT * FROM administrators WHERE id_administrator = " + this.id;
    }

    @Override
    public DatabaseException getException() {
        return new DatabaseException("Errore database", "Attenzione, non \u00E8 stato possibile recuperare i dati dell'amministratore.");
    }
}
