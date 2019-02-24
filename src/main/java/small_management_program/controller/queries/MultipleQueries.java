package small_management_program.controller.queries;

import small_management_program.model.DatabaseException;

import java.util.ArrayList;
import java.util.List;

public class MultipleQueries implements Query {

    private List<Query> queries;

    public MultipleQueries(){
        queries = new ArrayList<>();
    }

    public MultipleQueries(List<Query> queries){
        this.queries = queries;
    }

    public void add(Query query){
        if(query != null){
            this.queries.add(query);
        }
    }

    public List<Query> getQueries(){
        List<Query> ret = new ArrayList<>();
        for (Query query : this.queries){
            ret.add(query);
        }
        return ret;
    }

    @Override
    public String getQuery(){
        StringBuilder ret = new StringBuilder();
        for(Query query : this.queries){
            ret.append(query.getQuery() + ';');
        }
        return ret.toString();
    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database", "Attenzione, non è stato possibile eseguire la serie di query desiderata.");
    }

}
