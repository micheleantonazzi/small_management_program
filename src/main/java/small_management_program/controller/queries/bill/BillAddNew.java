package small_management_program.controller.queries.bill;

import small_management_program.controller.queries.Query;
import small_management_program.model.database.DatabaseException;

public class BillAddNew implements Query {

    private int idCondo;
    private int year;
    private Double total;

    public BillAddNew(int idCondo, int year, Double total) {
        this.idCondo = idCondo;
        this.year = year;
        this.total = total;
    }

    @Override
    public String getQuery() {
        return "INSERT INTO billings (id_condo, year, total) VALUES (" + this.idCondo + ", " + this.year + ", " + this.total + ")";
    }

    @Override
    public DatabaseException getException() {
        return new DatabaseException("Errore database", "Attenzione, impossibile impostare la fatturazione.\n" +
                "Assicurarsi di non aver gi\u00E0 assegnato due fatture nello stesso anno per il condiminio scelto.");
    }
}
