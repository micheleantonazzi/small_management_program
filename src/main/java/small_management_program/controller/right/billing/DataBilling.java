package small_management_program.controller.right.billing;

import javafx.collections.ObservableList;
import small_management_program.controller.Observer;
import small_management_program.controller.Subject;
import small_management_program.controller.UpdateException;
import small_management_program.controller.parameters.WhereParameters;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.controller.queries.billing.BillingCondosSelectLastWithParameter;
import small_management_program.model.DatabaseException;

public class DataBilling extends Subject implements Observer {

    private boolean updateView = false;

    private ObservableList data;

    @Override
    public void update(Subject subject) throws DatabaseException {
        try {
            WhereParameters whereParameters = subject.getWhereParameters();
            whereParameters.setIdentifier("c", 0, 1);
            QueryWithResults query = new BillingCondosSelectLastWithParameter(whereParameters);
            this.data = new DataStrategyBilling().getData(query);
            this.updateView = true;
            updateObservers();
            this.updateView = false;
        }
        catch (UpdateException exception){}
    }

    @Override
    public ObservableList getTableViewData() throws UpdateException{
        if(!this.updateView)
            throw new UpdateException();
        return this.data;
    }

}
