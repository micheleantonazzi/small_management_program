package small_management_program.view.stages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class StageModifyCondoController implements Initializable {

    @FXML
    private  ChoiceBox choiceBoxCondos;

    @FXML
    private TextField textFieldId;

    @FXML
    private TextField textFieldCode;

    @FXML
    private ChoiceBox choiceBoxAdministrators;

    @FXML
    private ChoiceBox choiceBoxMonths;

    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldProvince;

    @FXML
    private TextField textFieldCity;

    @FXML
    private TextField textFieldAddress;

    @FXML
    private TextField textFieldCap;

    @FXML
    private TextField textFieldFlats;

    @FXML
    private Button buttonAddCondo;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        
    }

    public void closeStage(ActionEvent event){}
}
