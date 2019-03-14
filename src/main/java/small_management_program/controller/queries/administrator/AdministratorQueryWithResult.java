package small_management_program.controller.queries.administrator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import small_management_program.controller.DuplicateMap;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.view.graphicutilities.ChoiceBoxItemId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

public abstract class AdministratorQueryWithResult extends QueryWithResults {

    public ObservableList<ChoiceBoxItemId> getChoiceBoxItems(){
        DuplicateMap<Integer, String> results = getResults();
        ObservableList<ChoiceBoxItemId> itemsAdministrators = FXCollections.observableArrayList();
        Set<Integer> administratorsIds = results.keySet();
        for(Iterator<Integer> it = administratorsIds.iterator(); it.hasNext();){
            int id = it.next();
            itemsAdministrators.add(new ChoiceBoxItemId(id, results.get(id, 1) + " " + results.get(id, 2)));
        }
        return itemsAdministrators;
    }

    @Override
    public DuplicateMap<Integer, String> getResults(){
        ResultSet resultSet = getResultSet();
        DuplicateMap<Integer, String> ret = new DuplicateMap<>();
        try{
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                ret.put(id, resultSet.getString(2));
                ret.put(id, resultSet.getString(3));
                ret.put(id, resultSet.getString(4));
            }
        }
        catch (SQLException exception){}
        return ret;
    }
}
