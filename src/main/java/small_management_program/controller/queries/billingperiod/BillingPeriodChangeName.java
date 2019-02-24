package small_management_program.controller.queries.billingperiod;

import small_management_program.controller.queries.Query;
import small_management_program.model.DatabaseException;

public class BillingPeriodChangeName implements Query {

    private int id;
    private String name;

    public BillingPeriodChangeName(int id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String getQuery(){
        return "UPDATE billing_periods SET name = '" + name + "' WHERE id_period = " + id;
    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database", "Attenzione, non è stato possibile rinominare il periodo di fatturazione.\n" +
                "Assicurarsi di non avere due periodi di fatturazione che si chiamino con lo stesso nome");
    }
}
