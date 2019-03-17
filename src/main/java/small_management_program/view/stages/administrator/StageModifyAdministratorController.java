package small_management_program.view.stages.administrator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import small_management_program.controller.DuplicateMap;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.controller.queries.administrator.*;
import small_management_program.model.database.Database;

import small_management_program.model.database.DatabaseException;
import small_management_program.view.annotation.AnnotationMessageConfirmation;
import small_management_program.view.annotation.AnnotationShowAlertSuccess;
import small_management_program.view.graphicutilities.GraphicUtilities;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StageModifyAdministratorController implements Initializable {

    @FXML
    private ChoiceBox choiceBoxAdministrators;

    @FXML
    private TextField textFieldAcronym;

    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldSurname;

    @FXML
    private Button buttonModifyAdministrator;

    @FXML
    private Button buttonDeleteAdministrator;

    public boolean checkFields(){
        boolean ret = false;
        if (textFieldAcronym.getText().length() == 2 && textFieldName.getText().length() > 0 &&
                textFieldSurname.getText().length() > 0){
            ret = true;
            this.buttonModifyAdministrator.setDisable(false);

        }
        else{
            this.buttonModifyAdministrator.setDisable(true);
        }

        return ret;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.getAdministrator();

        this.choiceBoxAdministrators.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->{
            if (newValue != null){
                QueryWithResults query = new AdministratorGetOne(newValue.hashCode());

                try{
                    Database.getInstance().executeQuery(query);
                    DuplicateMap<Integer, String> results = query.getResults();

                    int id = results.keySet().iterator().next();

                    this.textFieldAcronym.setText(results.get(id, 0));
                    this.textFieldName.setText(results.get(id, 1));
                    this.textFieldSurname.setText(results.get(id, 2));
                    this.disable(false);
                    this.buttonDeleteAdministrator.setDisable(false);
                }
                catch (SQLException ex){
                    GraphicUtilities.getInstance().showAlertError("Operazione non riuscita", ex.getMessage());
                }
                catch (DatabaseException ex){
                    GraphicUtilities.getInstance().showAlertError(ex);
                }
            }
        });

        this.textFieldAcronym.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() >= 3)
                this.textFieldAcronym.setText(oldValue);
            else if (!newValue.matches("[a-zA-Z]+"))
                textFieldAcronym.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            else
                this.textFieldAcronym.setText(newValue.toUpperCase());
            this.checkFields();
        });

        this.textFieldName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z]+"))
                textFieldName.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            this.checkFields();
        });

        this.textFieldSurname.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z]+"))
                textFieldSurname.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            this.checkFields();
        });

        this.disable(true);
        this.buttonModifyAdministrator.setDisable(true);
        this.buttonDeleteAdministrator.setDisable(true);

    }

    private void getAdministrator(){
        try{
            AdministratorQueryWithResults getAdministrator = new AdministratorSelectAll();
            Database.getInstance().executeQuery(getAdministrator);
            this.choiceBoxAdministrators.setItems(getAdministrator.getChoiceBoxItems());
        }
        catch (Throwable ex){
            GraphicUtilities.getInstance().showAlertError("Operazione non riuscita", ex.getMessage());
        }
    }

    private void disable(boolean val){
        this.textFieldAcronym.setDisable(val);
        this.textFieldName.setDisable(val);
        this.textFieldSurname.setDisable(val);
    }

    @AnnotationShowAlertSuccess(message = "Amministratore modificato con successo.")
    public void stageGoal() throws Throwable{
        int id = this.choiceBoxAdministrators.getSelectionModel().getSelectedItem().hashCode();
        Database.getInstance().executeQuery(new AdministratorModify(id, this.textFieldAcronym.getText(),
                this.textFieldName.getText(), this.textFieldSurname.getText()));
        int index = this.choiceBoxAdministrators.getSelectionModel().getSelectedIndex();
        this.getAdministrator();
        this.choiceBoxAdministrators.getSelectionModel().select(index);
    }

    @AnnotationMessageConfirmation(message = "Vuoi davvero eliminare l'amministratore selezionato?")
    public void stageGoalDelete() throws Throwable{
        int id = this.choiceBoxAdministrators.getSelectionModel().getSelectedItem().hashCode();
        Database.getInstance().executeQuery(new AdministratorDelete(id));
        this.textFieldName.setText("");
        this.textFieldSurname.setText("");
        this.textFieldAcronym.setText("");
        this.disable(true);
        this.getAdministrator();
    }

    //captured by AspectCloseStage
    public void closeStage(ActionEvent event){}
}
