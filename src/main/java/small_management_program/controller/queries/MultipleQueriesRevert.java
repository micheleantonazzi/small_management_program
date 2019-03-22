package small_management_program.controller.queries;

import java.util.ArrayList;
import java.util.List;

public class MultipleQueriesRevert extends MultipleQueries implements QueryRevert {

    public MultipleQueriesRevert(){
        super(new ArrayList<>());
    }

    public MultipleQueriesRevert(List<Query> queries){
        super(queries);
    }

    @Override
    public Query getQueryRevert(){
        List<Query> queriesRevert = new ArrayList<>();
        List<Query> queries = super.getQueries();

        for (Query query : queries){
            if(query instanceof QueryRevert){
                QueryRevert q = (QueryRevert) query;
                queriesRevert.add(q.getQueryRevert());
            }
        }

        //Se la lista è vuota, inserisco una query nella lista
        if(queriesRevert.size() == 0){
            queriesRevert.add(new SimpleQuery());
        }

        return new MultipleQueries(queriesRevert);
    }
}
