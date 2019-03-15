package small_management_program.controller.queries.condo;

import small_management_program.controller.queries.Query;
import small_management_program.model.database.DatabaseException;

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
        return new DatabaseException("Operazione non riuscita", "Attenzione, non \u00E8 stato possibile eliminare il condominio desiderato.");
    }
}
