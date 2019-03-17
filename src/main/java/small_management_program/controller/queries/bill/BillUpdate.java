package small_management_program.controller.queries.bill;

import small_management_program.controller.queries.Query;
import small_management_program.model.database.DatabaseException;

/**
 * Modifica una fattura.
 *
 * @author Michele Antonazzi
 */

public class BillUpdate implements Query {

    private int idCondo;
    private int year;
    private double total;

    public BillUpdate(int idCondo, int year, double total) {
        this.idCondo = idCondo;
        this.year = year;
        this.total = total;
    }

    @Override
    public String getQuery() {
        return "UPDATE billings SET total = " + this.total + " " +
                "WHERE id_condo = " + idCondo + " AND year = " + this.year;
    }

    @Override
    public DatabaseException getException() {
        return new DatabaseException("Errore database", "Attenzione, impossibile modificare la fatturazione.\n" +
                "Assicurarsi di non aver assegnato un anno già utilizzato per questo condominio.");
    }
}
