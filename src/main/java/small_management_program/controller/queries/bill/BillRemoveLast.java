package small_management_program.controller.queries.bill;

import small_management_program.controller.queries.Query;
import small_management_program.model.DatabaseException;

public class BillRemoveLast implements Query {

    private int idCondo;
    private int year;
    private int month;

    public BillRemoveLast(int idCondo, int year, int month) {
        this.idCondo = idCondo;
        this.year = year;
        this.month = month;
    }

    @Override
    public String getQuery() {
        return "DELETE FROM bills WHERE id_condo = " + this.idCondo + " AND year = " + this.year + " AND month = " + this.month;
    }

    @Override
    public DatabaseException getException() {
        return new DatabaseException("Errore database", "Attenzione, non è stato possibile eliminare l'ultima fattura.");
    }
}
