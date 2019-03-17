package small_management_program.controller.queries.bill;

import small_management_program.controller.DuplicateMap;
import small_management_program.controller.parameters.WhereParameters;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.model.database.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Ritorna le fatture con determianti parametri.
 * Usato per esempio in StageAddBilling in fase di modifica per selezionare la fattura corretta da modificare.
 *
 * @author Michele Antonazzi
 */

public class BillSelectWithParameter extends QueryWithResults {

    private WhereParameters whereParameters;

    public BillSelectWithParameter(WhereParameters whereParameters){
        this.whereParameters = whereParameters;
    }

    public BillSelectWithParameter(String whereParameters){
        this(new WhereParameters(whereParameters));
    }

    public BillSelectWithParameter(){
        this("1 = 1");
    }

    @Override
    public String getQuery(){
        return "SELECT * " +
                "FROM billings " +
                "WHERE " + whereParameters.toString();
    }

    @Override
    public DuplicateMap<Integer, String> getResults(){
        ResultSet resultSet = super.getResultSet();
        DuplicateMap<Integer, String> ret = new DuplicateMap<>();
        try {
            resultSet.next();
            int id = resultSet.getInt(1);
            ret.put(id, resultSet.getString(2));
            ret.put(id, resultSet.getString(3));
        }
        catch (SQLException exception){}

        return ret;

    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database", "Attenzione, impossibile recuperare la fatturazione dal database.");
    }
}
