package small_management_program.controller.queries.billingperiod;

import small_management_program.controller.DuplicateMap;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.model.DatabaseException;
import small_management_program.model.Months;
import small_management_program.view.graphicutilities.GraphicUtilities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BillingPeriodGetFreeMonths extends QueryWithResults {

    @Override
    public String getQuery(){
        return "SELECT * FROM months WHERE id_month NOT IN (SELECT id_month FROM billing_period_month);";
    }

    @Override
    public DuplicateMap<Integer, String> getResults(){
        ResultSet resultSet = getResultSet();
        DuplicateMap<Integer, String> ret = new DuplicateMap<>();
        try {
            while(resultSet.next()){
                int index = resultSet.getInt(1);
                ret.put(index, Months.getInstance().getMonthName(index));
            }
        }
        catch (SQLException exception){}
        return ret;
    }

    @Override
    public DatabaseException getException(){
        return new DatabaseException("Errore database", "Attenzione, non è stato possibile recuperare i dati da database.");
    }
}
