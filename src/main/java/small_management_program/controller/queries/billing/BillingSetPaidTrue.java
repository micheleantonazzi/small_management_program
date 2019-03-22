package small_management_program.controller.queries.billing;

import small_management_program.controller.queries.Query;
import small_management_program.controller.queries.QueryRevert;
import small_management_program.model.database.DatabaseException;

public class BillingSetPaidTrue implements QueryRevert {

    private int idCondo;
    private int year;

    public BillingSetPaidTrue(int idCondo, int year){
        this.idCondo = idCondo;
        this.year = year;
    }

    @Override
    public String getQuery(){
        return "UPDATE billings SET paid = 1 WHERE id_condo = " + this.idCondo + " AND year = " + this.year;
    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database", "Attenzione, non \u00E8 stato possibile impostare la fattura annuale come pagata.");
    }

    @Override
    public Query getQueryRevert(){
        return new BillingSetPaidFalse(this.idCondo, this.year);
    }

}
