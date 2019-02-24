package small_management_program.controller.queries.administrator;

import small_management_program.controller.queries.Query;
import small_management_program.model.DatabaseException;

public class AdministratorDelete implements Query {

    private int id;

    public AdministratorDelete(int id){
        this.id = id;
    }

    @Override
    public String getQuery(){
        return "DELETE FROM administrators WHERE id_administrator = " + id;
    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database", "Attenzione, impossibile eliminare l'amministratore.\n" +
                "Assicurarsi che non stia amministrando qualche condominio.");
    }
}
