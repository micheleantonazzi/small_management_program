package small_management_program.controller.queries.billingperiod;

import small_management_program.controller.queries.Query;
import small_management_program.model.DatabaseException;

public class BillingPeriodRemoveMonth implements Query {

    private int month;

    public BillingPeriodRemoveMonth(int month){
        this.month = month;
    }

    @Override
    public String getQuery(){
        return "DELETE FROM billing_period_month WHERE id_month = " + this.month;
    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database", "Attenzione, impossibile rimuovere il mese dal periodo di fatturazione");
    }
}
