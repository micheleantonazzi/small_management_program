package small_management_program.controller.right.condos;

import javafx.collections.ObservableList;
import small_management_program.controller.Observer;
import small_management_program.controller.Subject;
import small_management_program.controller.UpdateException;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.controller.queries.condo.CondoSelectCompleteWithParameter;
import small_management_program.model.database.DatabaseException;

import java.sql.SQLException;

public class DataCondos extends Subject implements Observer {

    private static DataCondos instance;

    private ObservableList data;

    private DataCondos(){}

    public static DataCondos getInstance(){
        if (instance == null)
            instance = new DataCondos();
        return instance;
    }

    @Override
    public void update(Subject subject) throws DatabaseException, SQLException {
        try {
            QueryWithResults query = new CondoSelectCompleteWithParameter(subject.getWhereParameters());
            this.data = new DataStrategyCondos().getData(query);
            updateObservers();
        }
        catch (UpdateException exception){}
    }

    @Override
    public ObservableList getTableViewData(){
        return this.data;
    }
}
