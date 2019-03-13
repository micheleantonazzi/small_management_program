package small_management_program.view.stages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import small_management_program.controller.DuplicateMap;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.controller.queries.administrator.AdministratorSelectAll;
import small_management_program.model.database.Database;
import small_management_program.view.graphicutilities.AlertError;
import small_management_program.view.graphicutilities.ChoiceBoxItemId;
import small_management_program.view.graphicutilities.GraphicUtilities;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;

public class StageModifyAdministratorController implements Initializable {

    @FXML
    private ChoiceBox choiceBoxAdministrators;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            QueryWithResults getAdministrator = new AdministratorSelectAll();
            Database.getInstance().executeQuery(getAdministrator);
            DuplicateMap<Integer, String> results = getAdministrator.getResults();
            ObservableList<ChoiceBoxItemId> itemsAdministrators = FXCollections.observableArrayList();
            Set<Integer> administratorsIds = results.keySet();
            for(Iterator<Integer> it = administratorsIds.iterator(); it.hasNext();){
                int id = it.next();
                itemsAdministrators.add(new ChoiceBoxItemId(id, results.get(id, 1) + " " + results.get(id, 2)));
            }
            this.choiceBoxAdministrators.setItems(itemsAdministrators);
        }
        catch (Throwable ex){
            GraphicUtilities.getInstance().showAlertError("Operazione non riuscita",
                    ex.getMessage());
        }
    }
}
