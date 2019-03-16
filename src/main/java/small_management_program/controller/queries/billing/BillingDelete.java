/*
 * Michele Antonazzi
 */

package small_management_program.controller.queries.billing;

import small_management_program.controller.queries.Query;
import small_management_program.model.database.DatabaseException;

public class BillingDelete implements Query {

    private int idCondo;
    private int year;

    public BillingDelete(int idCondo, int year){
        this.idCondo = idCondo;
        this.year = year;
    }

    @Override
    public String getQuery(){
        return "DELETE FROM billings " +
                "WHERE id_condo = " +this.idCondo + " AND year = " + this.year;
    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database", "Attenzione, non è stato possibile rimuovere la fattura.");
    }
}
