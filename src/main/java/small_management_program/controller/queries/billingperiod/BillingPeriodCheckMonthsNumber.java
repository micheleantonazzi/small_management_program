package small_management_program.controller.queries.billingperiod;


import small_management_program.controller.DuplicateMap;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.model.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BillingPeriodCheckMonthsNumber extends QueryWithResults {

    private int period;

    public BillingPeriodCheckMonthsNumber(int period){
        this.period = period;
    }

    @Override
    public String getQuery(){
        return "SELECT id_period, id_month FROM billing_period_month WHERE id_period = " + this.period;
    }

    @Override
    public DuplicateMap<Integer, String> getResults(){
        DuplicateMap<Integer, String> ret = new DuplicateMap<>();
        ResultSet resultSet = getResultSet();
        try{
            while (resultSet.next()) {
                ret.put(resultSet.getInt(1), String.valueOf(resultSet.getInt(2)));
            }
        }
        catch (SQLException exception){        }
        return ret;
    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database", "Attenzione, non è stato possibile recuperare i dati richiesti");
    }
}
