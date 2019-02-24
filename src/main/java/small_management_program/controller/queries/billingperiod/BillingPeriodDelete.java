package small_management_program.controller.queries.billingperiod;

import small_management_program.controller.queries.Query;
import small_management_program.model.DatabaseException;

public class BillingPeriodDelete implements Query {

    private int period;

    public BillingPeriodDelete(int period){
        this.period = period;
    }

    @Override
    public String getQuery(){
        return "DELETE FROM billing_periods WHERE id_period = " + period;
    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database", "Attenzione, impossibile rimuovere il periodo di fatturazione selezionato.");
    }
}
