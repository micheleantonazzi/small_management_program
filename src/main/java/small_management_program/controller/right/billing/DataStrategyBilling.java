package small_management_program.controller.right.billing;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import small_management_program.controller.DuplicateMap;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.controller.right.DataStrategy;
import small_management_program.model.database.Database;
import small_management_program.model.database.DatabaseException;
import small_management_program.model.databaseclasses.BillingRepresentation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

public class DataStrategyBilling implements DataStrategy {

    @Override
    public ObservableList getData(QueryWithResults query) throws DatabaseException, SQLException {
        Database.getInstance().executeQuery(query);
        DuplicateMap<Integer, String> results = query.getResults();
        ObservableList ret = FXCollections.observableArrayList();
        Set<Integer> ids = results.keySet();

        for (Integer id : ids){
            ArrayList<String> parameters = results.get(id);
            BillingRepresentation billingRepresentation = new BillingRepresentation();

            billingRepresentation.setId(id);
            billingRepresentation.setName(parameters.get(0));
            billingRepresentation.setMonth(Integer.valueOf(parameters.get(1)));

            String year = parameters.get(2);
            billingRepresentation.setYear(year);
            if(year == null){
                year = "";
            }
            else{
                year = " (" + year.substring(2, year.indexOf('-')) + ")";
            }

            String total = parameters.get(3);
            if (total == null){
                total = "";
            }
            billingRepresentation.setTotal(total + year);

            billingRepresentation.setBills(results.get(id).subList(4, parameters.size()));

            ret.add(billingRepresentation);
        }
        return ret;
    }
}
