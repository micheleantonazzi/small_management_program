package small_management_program.controller.queries.condo;

import small_management_program.controller.queries.Query;
import small_management_program.model.DatabaseException;

public class CondoDelete implements Query {

    private int id;

    public CondoDelete(int id){
        this.id = id;
    }

    @Override
    public String getQuery(){
        return "DELETE FROM condos WHERE id_condo = " + this.id;
    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database", "Attenzione, non è stato possibile eliminare il condominio desiderato.");
    }
}
