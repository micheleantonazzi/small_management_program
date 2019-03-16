package small_management_program.view.stages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import small_management_program.controller.DuplicateMap;
import small_management_program.controller.parameters.WhereParameters;
import small_management_program.controller.queries.Query;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.controller.queries.administrator.AdministratorQueryWithResults;
import small_management_program.controller.queries.administrator.AdministratorSelectAll;
import small_management_program.controller.queries.billing.BillingSelectWithParameter;
import small_management_program.controller.queries.condo.CondoDelete;
import small_management_program.controller.queries.condo.CondoModify;
import small_management_program.controller.queries.condo.CondoSelectAll;
import small_management_program.controller.queries.condo.CondoSelectWithParameters;
import small_management_program.model.Months;
import small_management_program.model.database.Database;
import small_management_program.model.database.DatabaseException;
import small_management_program.view.annotation.AnnotationMessageConfirmation;
import small_management_program.view.annotation.AnnotationShowAlertSuccess;
import small_management_program.view.graphicutilities.ChoiceBoxItemId;
import small_management_program.view.graphicutilities.GraphicUtilities;

import javax.xml.crypto.Data;
import java.net.URL;
import java.sql.SQLException;
import java.util.Iterator;
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
    private Button buttonModifyCondo;

    @FXML
    private Button buttonDeleteCondo;

    private int idCondo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try{
            //Load condos
            CondoSelectAll getCondos = new CondoSelectAll();
            Database.getInstance().executeQuery(getCondos);
            this.choiceBoxCondos.setItems(getCondos.getChoiceBoxItems());

            //Load administrators
            AdministratorQueryWithResults getAdministrator = new AdministratorSelectAll();
            Database.getInstance().executeQuery(getAdministrator);
            this.choiceBoxAdministrators.setItems(getAdministrator.getChoiceBoxItems());

            this.choiceBoxCondos.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
                if(newValue != null) {
                    try {
                        QueryWithResults querySelectCondo = new CondoSelectWithParameters(new WhereParameters("id_condo = " + newValue.hashCode()));
                        Database.getInstance().executeQuery(querySelectCondo);
                        DuplicateMap<Integer, String> result = querySelectCondo.getResults();
                        this.idCondo = result.keySet().iterator().next();

                        this.textFieldId.setText(String.valueOf(idCondo));
                        this.textFieldCode.setText(result.get(idCondo, 0));

                        int idAdministrator = Integer.valueOf(result.get(idCondo, 1));
                        Iterator<ChoiceBoxItemId> itAdministrator = this.choiceBoxAdministrators.getItems().iterator();
                        int countAdministrator = 0;
                        for (; idAdministrator != itAdministrator.next().hashCode(); ++countAdministrator) {
                        }
                        this.choiceBoxAdministrators.getSelectionModel().select(countAdministrator);

                        int idMonth = Integer.valueOf(result.get(idCondo, 2));
                        Iterator<ChoiceBoxItemId> itMonths = this.choiceBoxMonths.getItems().iterator();
                        int countMonth = 0;
                        for (; idMonth != itMonths.next().hashCode(); ++countMonth) {
                        }
                        this.choiceBoxMonths.getSelectionModel().select(countMonth);

                        this.textFieldName.setText(result.get(idCondo, 3));
                        this.textFieldProvince.setText(result.get(idCondo, 4));
                        this.textFieldCity.setText(result.get(idCondo, 5));
                        this.textFieldAddress.setText(result.get(idCondo, 6));
                        this.textFieldCap.setText(result.get(idCondo, 7));
                        this.textFieldFlats.setText(result.get(idCondo, 8));

                        this.setDisabled(false);

                        //Se ci sono fatture diabilito la possibilità di cambiare mese di chiusura dell'esercizio,
                        //altrimenti verranno introdotti errori

                        QueryWithResults billingSelect = new BillingSelectWithParameter("id_condo=" + idCondo);
                        Database.getInstance().executeQuery(billingSelect);
                        if (billingSelect.getResults().keySet().size() > 0)
                            this.choiceBoxMonths.setDisable(true);

                    } catch (DatabaseException exception) {
                        GraphicUtilities.getInstance().showAlertError(exception);
                    } catch (SQLException ex) {
                        GraphicUtilities.getInstance().showAlertError("Operazione non riuscita", ex.getMessage());
                    }
                }
            });


        }
        catch (DatabaseException ex){
            GraphicUtilities.getInstance().showAlertError(ex);
        }
        catch (SQLException ex){
            GraphicUtilities.getInstance().showAlertError("Operazione non riuscita", ex.getMessage());
        }

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

        this.setDisabled(true);

    }

    private void setDisabled(boolean value){
        this.textFieldId.setDisable(value);
        this.textFieldCode.setDisable(value);
        this.choiceBoxAdministrators.setDisable(value);
        this.choiceBoxMonths.setDisable(value);
        this.textFieldName.setDisable(value);
        this.textFieldProvince.setDisable(value);
        this.textFieldCity.setDisable(value);
        this.textFieldAddress.setDisable(value);
        this.textFieldCap.setDisable(value);
        this.textFieldFlats.setDisable(value);
        this.buttonDeleteCondo.setDisable(value);

        if(value){
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
    }

    private boolean checkFields(){
        boolean ret = false;
        if(textFieldId.getText().length() > 0 && textFieldCode.getText().length() == 11 && choiceBoxAdministrators.getSelectionModel().getSelectedIndex() > -1 &&
                choiceBoxMonths.getSelectionModel().getSelectedIndex() > -1 && textFieldName.getText().length() > 0 && textFieldProvince.getText().length() == 2 &&
                textFieldCity.getText().length() > 0 && textFieldAddress.getText().length() > 0 && textFieldCap.getText().length() == 5 && textFieldFlats.getText().length() > 0) {
            ret = true;
            this.buttonModifyCondo.setDisable(false);
        }
        else
            this.buttonModifyCondo.setDisable(true);
        return ret;
    }

    @AnnotationMessageConfirmation(message = "Vuoi davvero eliminare il condominio selezionato?")
    public void stageGoalDelete() throws Throwable{
        Query condoDelete = new CondoDelete(this.idCondo);
        Database.getInstance().executeQuery(condoDelete);
        this.setDisabled(true);
        CondoSelectAll query = new CondoSelectAll();
        Database.getInstance().executeQuery(query);
        this.choiceBoxCondos.setItems(query.getChoiceBoxItems());
    }

    @AnnotationShowAlertSuccess(message = "Condominio modificato con successo.")
    public void stageGoal() throws Throwable{
        Query queryModifyCondo = new CondoModify(this.idCondo, Integer.valueOf(this.textFieldId.getText()), this.textFieldCode.getText(),
                Integer.valueOf(this.choiceBoxAdministrators.getSelectionModel().getSelectedItem().hashCode()), Integer.valueOf(this.choiceBoxMonths.getSelectionModel().getSelectedItem().hashCode()),
                this.textFieldName.getText(), this.textFieldProvince.getText(), this.textFieldCity.getText(), this.textFieldAddress.getText(),
                this.textFieldCap.getText(), Integer.valueOf(this.textFieldFlats.getText()));
        Database.getInstance().executeQuery(queryModifyCondo);

        this.setDisabled(true);
        CondoSelectAll query = new CondoSelectAll();
        Database.getInstance().executeQuery(query);
        this.choiceBoxCondos.setItems(query.getChoiceBoxItems());
    }

    public void closeStage(ActionEvent event){}
}
