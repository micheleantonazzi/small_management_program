package small_management_program.view.stages.condo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import small_management_program.controller.queries.administrator.AdministratorQueryWithResults;
import small_management_program.controller.queries.administrator.AdministratorSelectAll;
import small_management_program.controller.queries.condo.CondoAddNew;
import small_management_program.model.Months;
import small_management_program.model.database.Database;
import small_management_program.model.database.DatabaseException;
import small_management_program.view.annotation.AnnotationShowAlertSuccess;
import small_management_program.view.graphicutilities.GraphicUtilities;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StageAddCondoController implements Initializable {

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
        try{
            //Load Administrator
            AdministratorQueryWithResults getAdministrator = new AdministratorSelectAll();
            Database.getInstance().executeQuery(getAdministrator);
            this.choiceBoxAdministrators.setItems(getAdministrator.getChoiceBoxItems());
        }
        catch (DatabaseException ex){
            GraphicUtilities.getInstance().showAlertError(ex);
        }
        catch (SQLException ex){
            GraphicUtilities.getInstance().showAlertError("Operazione non riuscita", ex.getMessage());
        }

        //Months
        this.choiceBoxMonths.setItems(Months.getInstance().getListAllMonths());

        //TextFields
        this.textFieldId.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() > 3)
                this.textFieldId.setText(oldValue);
            if (!newValue.matches("\\d*")) {
                this.textFieldId.setText(newValue.replaceAll("[^\\d]", ""));
            }
            this.checkFields();
        });
        this.textFieldCode.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() > 11)
                this.textFieldCode.setText(oldValue);
            if (!newValue.matches("\\d*")) {
                this.textFieldCode.setText(newValue.replaceAll("[^\\d]", ""));
            }
            this.checkFields();
        });
        this.textFieldName.textProperty().addListener((observable, oldValue, newValue) -> this.checkFields());
        this.textFieldProvince.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() >= 3)
                this.textFieldProvince.setText(oldValue);
            else if (!newValue.matches("[a-zA-Z]+"))
                textFieldProvince.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            else
                this.textFieldProvince.setText(newValue.toUpperCase());
            this.checkFields();
        });
        this.textFieldCity.textProperty().addListener((observable, oldValue, newValue) -> this.checkFields());
        this.textFieldAddress.textProperty().addListener((observable, oldValue, newValue) -> this.checkFields());
        this.textFieldCap.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() > 5)
                this.textFieldCap.setText(oldValue);
            if (!newValue.matches("\\d*")) {
                this.textFieldCap.setText(newValue.replaceAll("[^\\d]", ""));
            }
            this.checkFields();
        });
        this.textFieldFlats.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                this.textFieldFlats.setText(newValue.replaceAll("[^\\d]", ""));
            }
            this.checkFields();
        });

        this.buttonAddCondo.setDisable(true);

    }

    private boolean checkFields(){
        boolean ret = false;
        if(textFieldId.getText().length() > 0 && textFieldCode.getText().length() == 11 && choiceBoxAdministrators.getSelectionModel().getSelectedIndex() > -1 &&
                choiceBoxMonths.getSelectionModel().getSelectedIndex() > -1 && textFieldName.getText().length() > 0 && textFieldProvince.getText().length() == 2 &&
                textFieldCity.getText().length() > 0 && textFieldAddress.getText().length() > 0 && textFieldCap.getText().length() == 5 && textFieldFlats.getText().length() > 0) {
            ret = true;
            this.buttonAddCondo.setDisable(false);
        }
        else
            this.buttonAddCondo.setDisable(true);
        return ret;
    }

    @AnnotationShowAlertSuccess(message = "Nuovo condominio creato con successo.")
    public void stageGoal() throws Throwable{
        Database.getInstance().executeQuery(new CondoAddNew(Integer.valueOf(this.textFieldId.getText()), this.textFieldCode.getText(),
                this.choiceBoxAdministrators.getSelectionModel().getSelectedItem().hashCode(), this.choiceBoxMonths.getSelectionModel().getSelectedItem().hashCode(),
                this.textFieldName.getText(), this.textFieldProvince.getText(), this.textFieldCity.getText(), this.textFieldAddress.getText(), this.textFieldCap.getText(),
                Integer.valueOf(this.textFieldFlats.getText())));
        this.textFieldId.setText("");
        this.textFieldCode.setText("");
        this.choiceBoxAdministrators.getSelectionModel().select(-1);
        this.choiceBoxMonths.getSelectionModel().select(-1);
        this.textFieldName.setText("");
        this.textFieldProvince.setText("");
        this.textFieldCity.setText("");
        this.textFieldAddress.setText("");
        this.textFieldCap.setText("");
        this.textFieldFlats.setText("");
    }

    public void closeStage(ActionEvent event){}
}
