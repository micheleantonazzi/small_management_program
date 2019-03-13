package small_management_program.controller.queries.administrator;

import small_management_program.controller.queries.Query;
import small_management_program.model.database.DatabaseException;

public class AdministratorAddNew implements Query {

    private String name;
    private String surname;
    private String acronym;

    public AdministratorAddNew(String name, String surname, String acronym){
        this.name = name;
        this.surname = surname;
        this.acronym = acronym;
    }

    @Override
    public String getQuery(){
        return "INSERT INTO administrators (name, surname, acronym) VALUES ('"+ this.name + "', '"+ this.surname + "', '" + this.acronym + "')";
    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database", "Attenzione, non \u00E8 stato possibile creare un nuovo amministratore.\n" +
                "Controllare che l'acronimo inserito non sia gi\u00E0 stato assegnato ad un'altro amministratore.");
    }
}
