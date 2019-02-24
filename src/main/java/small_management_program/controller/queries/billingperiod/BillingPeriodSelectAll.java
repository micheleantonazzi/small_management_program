package small_management_program.controller.queries.billingperiod;

import small_management_program.controller.DuplicateMap;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.model.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BillingPeriodSelectAll extends QueryWithResults {

    @Override
    public String getQuery(){
        return "SELECT billing_periods.id_period, billing_periods.name, billing_period_month.id_month " +
                "FROM billing_periods JOIN billing_period_month " +
                "WHERE billing_periods.id_period = billing_period_month.id_period " +
                "ORDER BY billing_period_month.id_period;";
    }

    @Override
    public DuplicateMap<Integer, String> getResults(){
        ResultSet resultSet = super.getResultSet();
        DuplicateMap<Integer, String> ret = new DuplicateMap<>();
        try {
            int key = -1;
            while(resultSet.next()){
                if(resultSet.getInt(1) != key){
                    key = resultSet.getInt(1);
                    ret.put(key, resultSet.getString(2));
                    ret.put(key, Integer.toString(resultSet.getInt(3)));
                }
                else{
                    ret.put(key, Integer.toString(resultSet.getInt(3)));
                }
            }
        }
        catch (SQLException exception) {}
        return ret;
    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database", "Attenzione, il database ha riscontrato un errore durante il prelievo dei dati");
    }
}
