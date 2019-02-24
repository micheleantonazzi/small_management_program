package small_management_program.controller.queries.bill;

import small_management_program.controller.queries.Query;
import small_management_program.controller.queries.QueryRevert;
import small_management_program.model.DatabaseException;
import small_management_program.model.Months;

public class BillAddNew implements QueryRevert {

    private int idCondo;
    private int year;
    private int month;
    private double price;

    public BillAddNew(int idCondo, String year, int month, double price) {
        this.idCondo = idCondo;
        this.year = Integer.valueOf(year);
        this.month = month;
        this.price = price;
    }

    @Override
    public String getQuery(){
        return "INSERT INTO bills (id_condo, year, month, price) VALUES (" + this.idCondo + ", " + this.year + ", " + this.month + ", " + this.price + ")";
    }

    @Override
    public Query getQueryRevert(){
        return new BillDelete(this.idCondo, this.year, this.month);
    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database", "Attenzione, non è stato possibile creare la fattura.");
    }
}
