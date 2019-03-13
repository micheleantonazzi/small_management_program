package small_management_program.controller.queries.administrator;

import small_management_program.controller.queries.Query;
import small_management_program.model.database.DatabaseException;

public class AdministratorModify implements Query {

    private int id;
    private String acronym;
    private String name;
    private String surname;

    public AdministratorModify(int id, String acronym, String name, String surname){
        this.id = id;
        this.acronym = acronym;
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String getQuery(){
        return "UPDATE administrators SET acronym = '" + this.acronym + "', name = '" + this.name + "', surname = '" + this.surname +
                "' WHERE id_administrator = " + this.id;
    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database", "Attenzione, non \u00E8 stato possibile modificare l'amministratore.\n" +
                "Controllare che l'acronimo non sia uguale a quello di un altro amministratore.");
    }
}
