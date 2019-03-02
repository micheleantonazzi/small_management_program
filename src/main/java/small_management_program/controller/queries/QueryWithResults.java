package small_management_program.controller.queries;

import small_management_program.controller.DuplicateMap;

import java.sql.ResultSet;

public abstract class QueryWithResults implements Query {

    private ResultSet resultSet;

    public final void setResultSet(ResultSet resultSet){
        this.resultSet = resultSet;
    }

    public final ResultSet getResultSet(){
        return this.resultSet;
    }

    public abstract DuplicateMap<Integer, String> getResults();
}
