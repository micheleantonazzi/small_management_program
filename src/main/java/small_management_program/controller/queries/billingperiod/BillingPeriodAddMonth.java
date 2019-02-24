package small_management_program.controller.queries.billingperiod;

import small_management_program.controller.queries.Query;
import small_management_program.model.DatabaseException;

public class BillingPeriodAddMonth implements Query {

    private int month;
    private int period;

    public BillingPeriodAddMonth(int period, int month){
        this.period = period;
        this.month = month;
    }

    @Override
    public String getQuery(){
        return "INSERT INTO billing_period_month (id_period, id_month) VALUES (" + this.period + ", " + this.month + ")";
    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database", "Attenzione, impossibile assegnare il mese al periodo di fatturazione.\n" +
                "Controllare che questo mese non sia già stato assegnato ad un'altro periodo di fatturazione.");
    }
}
