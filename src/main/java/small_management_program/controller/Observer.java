package small_management_program.controller;


import small_management_program.model.DatabaseException;

public interface Observer {
    void update(Subject subject) throws DatabaseException;
}
