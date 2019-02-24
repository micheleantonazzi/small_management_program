package small_management_program.controller.queries.billingperiod;

import small_management_program.controller.queries.QueryWithError;
import small_management_program.model.DatabaseException;

public class BillingPeriodInsertNew extends QueryWithError {
    private String name;
    private int[] months;

    public BillingPeriodInsertNew(String name, int[] months){
        this.name = name;
        this.months = months;
    }

    @Override
    public String getQuery(){
        StringBuilder query = new StringBuilder("START TRANSACTION;" +
                "INSERT INTO billing_periods (name) VALUES ('" + this.name + "');");
        for(int i = 0; i < this.months.length; ++i){
            query.append("INSERT INTO billing_period_month (id_month, id_period) SELECT " + Integer.toString(this.months[i]) +
                    ", MAX(id_period) FROM billing_periods;");
        }
        return query.toString();
    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database", "Attenzione, il database ha riscontrato degli errori durante l'inserimento dei dati.\n" +
                " Controlla di aver selezionato mesi diversi da associare al nuovo periodo di fatturazione");
    }
}
