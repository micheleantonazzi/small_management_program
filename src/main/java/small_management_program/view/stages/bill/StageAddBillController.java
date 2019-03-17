package small_management_program.view.stages.bill;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import small_management_program.controller.queries.bill.BillAddNew;
import small_management_program.controller.queries.condo.CondoSelectAll;
import small_management_program.model.database.Database;
import small_management_program.model.database.DatabaseException;
import small_management_program.view.annotation.AnnotationShowAlertSuccess;
import small_management_program.view.graphicutilities.GraphicUtilities;

import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;

public class StageAddBillController implements Initializable {

    @FXML
    private ChoiceBox choiceBoxCondos;

    @FXML
    private ChoiceBox choiceBoxYears;

    @FXML
    private TextField textFieldTotal;

    @FXML
    private Button buttonAddBill;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            CondoSelectAll condoSelectAll = new CondoSelectAll();
            Database.getInstance().executeQuery(condoSelectAll);
            this.choiceBoxCondos.setItems(condoSelectAll.getChoiceBoxItems());
        }
        catch (DatabaseException ex){
            GraphicUtilities.getInstance().showAlertError(ex);
        }
        catch (SQLException ex){
            GraphicUtilities.getInstance().showAlertError("Operazione non riuscita", ex.getMessage());
        }

        this.choiceBoxCondos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.choiceBoxYears.setDisable(false);
            this.textFieldTotal.setDisable(false);
        });

        int year = Calendar.getInstance().get(Calendar.YEAR);
        ObservableList years = FXCollections.observableArrayList();
        years.addAll(year - 2, year - 1, year);
        this.choiceBoxYears.setItems(years);

        this.choiceBoxYears.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.enableButtonAdd());

        this.textFieldTotal.textProperty().addListener((observable, oldValue, newValue) -> {

            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
                this.textFieldTotal.setText(oldValue);
            }
            this.enableButtonAdd();
        });

        this.choiceBoxYears.setDisable(true);
        this.textFieldTotal.setDisable(true);
        this.buttonAddBill.setDisable(true);
    }

    private void enableButtonAdd(){
        if (this.textFieldTotal.getText().length() > 0 && this.choiceBoxYears.getSelectionModel().getSelectedItem() != null)
            this.buttonAddBill.setDisable(false);
        else
            this.buttonAddBill.setDisable(true);
    }

    @AnnotationShowAlertSuccess(message = "Nuova fattura aggiunta con successo")
    public void stageGoal() throws Throwable{
        BillAddNew query = new BillAddNew(this.choiceBoxCondos.getSelectionModel().getSelectedItem().hashCode(),
                Integer.valueOf(this.choiceBoxYears.getSelectionModel().getSelectedItem().toString()),
                Double.valueOf(this.textFieldTotal.getText()));
        Database.getInstance().executeQuery(query);
        this.choiceBoxCondos.getSelectionModel().select(-1);
        this.choiceBoxYears.getSelectionModel().select(-1);
        this.choiceBoxYears.setDisable(true);
        this.textFieldTotal.setText("");
        this.textFieldTotal.setDisable(true);
        this.buttonAddBill.setDisable(true);
    }

    public void closeStage(ActionEvent event){}
}
