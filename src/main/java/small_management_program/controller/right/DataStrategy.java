package small_management_program.controller.right;

import javafx.collections.ObservableList;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.model.DatabaseException;

public interface DataStrategy {

    ObservableList getData(QueryWithResults query) throws DatabaseException;
}
