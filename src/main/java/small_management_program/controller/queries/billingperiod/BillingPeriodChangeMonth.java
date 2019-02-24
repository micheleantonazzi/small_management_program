package small_management_program.controller.queries.billingperiod;

import small_management_program.controller.queries.Query;
import small_management_program.model.DatabaseException;

public class BillingPeriodChangeMonth implements Query {

    private int oldMonth;
    private int newMonth;

    public BillingPeriodChangeMonth(int oldMonth, int newMonth){
        this.oldMonth = oldMonth;
        this.newMonth = newMonth;
    }

    @Override
    public String getQuery(){
        return "UPDATE billing_period_month SET id_month = " + this.newMonth + " WHERE id_month = " + this.oldMonth;
    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database","Attenzione, impossibile associare il mese al periodo di fatturazione.\n" +
                "Controllare che questo mese non sia già stato assegnato ad un altro periodo di fatturazione");
    }
}
