package small_management_program.controller;


import small_management_program.model.database.DatabaseException;

import java.sql.SQLException;

public interface Observer {
    void update(Subject subject) throws DatabaseException, SQLException;
}
