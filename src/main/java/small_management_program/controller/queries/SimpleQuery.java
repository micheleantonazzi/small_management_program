package small_management_program.controller.queries;

import small_management_program.model.DatabaseException;

public class SimpleQuery implements Query {

    @Override
    public String getQuery(){
        return "SELECT * FROM months";
    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database", "Attenzione, non è stato possibile portate a termine l'operazione desiderata");
    }
}
