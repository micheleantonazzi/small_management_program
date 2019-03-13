package small_management_program.view.stages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import small_management_program.controller.queries.administrator.AdministratorAddNew;
import small_management_program.model.database.Database;
import small_management_program.model.database.DatabaseException;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StageAddAdministratorController implements Initializable {

    @FXML
    private TextField textFieldAcronym;

    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldSurname;

    @FXML
    private Button buttonAddAdministrator;

    public boolean checkFields(){
        boolean ret = false;
        if (textFieldAcronym.getText().length() == 2 && textFieldName.getText().length() > 0 &&
                textFieldSurname.getText().length() > 0){
            ret = true;
            this.buttonAddAdministrator.setDisable(false);
        }
        else
            this.buttonAddAdministrator.setDisable(true);
        return ret;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.buttonAddAdministrator.setDisable(true);
        this.textFieldAcronym.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() >= 3)
                this.textFieldAcronym.setText(oldValue);
            else if (!newValue.matches("[a-zA-Z]+"))
                textFieldAcronym.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            else
                this.textFieldAcronym.setText(newValue.toUpperCase());
            checkFields();
        });

        this.textFieldName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z]+"))
                textFieldName.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            checkFields();
        });

        this.textFieldSurname.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z]+"))
                textFieldSurname.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            checkFields();
        });
    }

    //captured by AspectShowAlerts
    public void StageGoal() throws SQLException, DatabaseException {
        Database.getInstance().executeQuery(new AdministratorAddNew(textFieldName.getText(), textFieldSurname.getText(),
                textFieldAcronym.getText()));
        textFieldAcronym.setText("");
        textFieldName.setText("");
        textFieldSurname.setText("");
        buttonAddAdministrator.setDisable(true);
    }

    //captured by AspectCloseStage
    public void closeStage(ActionEvent event){}
}
