package small_management_program.controller.right.condos;

import javafx.collections.ObservableList;
import small_management_program.controller.Observer;
import small_management_program.controller.Subject;
import small_management_program.controller.UpdateException;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.controller.queries.condo.CondoSelectCompleteWithParameter;
import small_management_program.controller.right.billing.DataStrategyBilling;
import small_management_program.model.DatabaseException;

public class DataCondos extends Subject implements Observer {

    private boolean updateView = false;

    private ObservableList data;

    @Override
    public void update(Subject subject) throws DatabaseException {
        try {
            QueryWithResults query = new CondoSelectCompleteWithParameter(subject.getWhereParameters());
            this.data = new DataStrategyCondos().getData(query);
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
