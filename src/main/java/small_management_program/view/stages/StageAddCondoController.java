package small_management_program.view.stages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class StageAddCondoController implements Initializable {

    @FXML
    private Button buttonAdd;
    private Button buttonExit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void closeStage(ActionEvent event){}
}
