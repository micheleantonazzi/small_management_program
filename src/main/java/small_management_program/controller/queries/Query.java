package small_management_program.controller.queries;

import small_management_program.model.database.DatabaseException;

public interface Query {

    String getQuery();

    DatabaseException getException();
}
