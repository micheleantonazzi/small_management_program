package small_management_program.controller.queries.administrator;

import small_management_program.model.database.DatabaseException;

public class AdministratorSelectAll extends AdministratorQueryWithResults {

    @Override
    public String getQuery(){
        return "SELECT * FROM administrators";
    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database", "Attenzione, non \u00E8 stato possibile recuperare gli amministratori.");
    }
}
