/*
 * Michele Antonazzi
 */

package small_management_program.controller.queries.bill;

import small_management_program.controller.queries.Query;
import small_management_program.model.database.DatabaseException;

public class BillDelete implements Query {

    private int idCondo;
    private int year;

    public BillDelete(int idCondo, int year){
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
        return new DatabaseException("Errore database", "Attenzione, non \u00E8 stato possibile rimuovere la fattura.");
    }
}
