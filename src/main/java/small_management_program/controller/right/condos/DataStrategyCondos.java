package small_management_program.controller.right.condos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import small_management_program.controller.ControllerFacade;
import small_management_program.controller.DuplicateMap;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.controller.right.DataStrategy;
import small_management_program.model.DatabaseException;
import small_management_program.model.databaseclasses.CondoRepresentation;

import java.util.Iterator;
import java.util.Set;

public class DataStrategyCondos implements DataStrategy {

    @Override
    public ObservableList getData(QueryWithResults query) throws DatabaseException {
        ControllerFacade.getInstance().executeQuery(query);
        DuplicateMap<Integer, String> results = query.getResults();

        ObservableList<CondoRepresentation> ret = FXCollections.observableArrayList();

        Set<Integer> ids = results.keySet();
        for(Iterator<Integer> it = ids.iterator(); it.hasNext();){
            int id = it.next();
            ret.add(new CondoRepresentation(id, results.get(id, 0), results.get(id, 1), Integer.valueOf(results.get(id, 2)),
                    results.get(id, 3), results.get(id, 4), results.get(id, 5), results.get(id, 6), results.get(id, 7),
                    Integer.valueOf(results.get(id, 8))));
        }
        return ret;
    }
}
