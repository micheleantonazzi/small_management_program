package small_management_program.controller.right;

import javafx.collections.ObservableList;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.model.database.DatabaseException;

import java.sql.SQLException;

public interface DataStrategy {

    ObservableList getData(QueryWithResults query) throws DatabaseException, SQLException;
}
