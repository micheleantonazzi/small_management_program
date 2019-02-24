package small_management_program.controller.queries.condo;

import small_management_program.controller.DuplicateMap;
import small_management_program.controller.parameters.WhereParameters;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.model.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CondoSelectCompleteWithParameter extends QueryWithResults {

    private WhereParameters whereParameters;

    public CondoSelectCompleteWithParameter(WhereParameters whereParameters){
        this.whereParameters = whereParameters;
    }

    @Override
    public String getQuery(){
        return "SELECT c.id_condo, c.code, CONCAT(a.name, CONCAT(' ', a.surname)), c.month, c.name , c.province, c.city, c.address, c.cap, c.flats " +
        "FROM condos c JOIN administrators a ON c.id_administrator = a.id_administrator " +
        "WHERE " + whereParameters.toString();
    }

    @Override
    public DuplicateMap<Integer, String> getResults(){
        ResultSet resultSet = getResultSet();
        DuplicateMap<Integer, String> ret = new DuplicateMap<>();
        try {
            while(resultSet.next()){
                int id = resultSet.getInt(1);
                ret.put(id, resultSet.getString(2));
                ret.put(id, resultSet.getString(3));
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
        return new DatabaseException("Errore database", "Attenzione, non è stato possibile recuperare i dati dei condomini");
    }
}