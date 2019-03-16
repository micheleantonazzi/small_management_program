package small_management_program.controller;


import small_management_program.model.database.DatabaseException;

public interface Observer {
    void update(Subject subject) throws DatabaseException;
}
