package small_management_program.controller.queries.administrator;

import small_management_program.model.database.DatabaseException;

public class AdministratorGetOne extends AdministratorQueryWithResults {

    private int id;

    public AdministratorGetOne(int id){
        this.id = id;
    }



    @Override
    public String getQuery() {
        return "SELECT * FROM administrators WHERE id_administrator = " + this.id;
    }

    @Override
    public DatabaseException getException() {
        return new DatabaseException("Errore database", "Attenzione, non \u00E8 stato possibile recuperare i dati dell'amministratore.");
    }
}
