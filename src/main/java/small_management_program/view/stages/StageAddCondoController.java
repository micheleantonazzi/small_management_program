package small_management_program.view.stages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.controller.queries.administrator.AdministratorSelectAll;
import java.net.URL;
import java.util.ResourceBundle;

public class StageAddCondoController implements Initializable {

    @FXML
    private ChoiceBox choiceBoxAdministrators;

    @FXML
    private Button buttonAddCondo;

    @FXML
    private Button buttonExit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        QueryWithResults getAdministrator = new AdministratorSelectAll();
    }

    public void closeStage(ActionEvent event){}
}
