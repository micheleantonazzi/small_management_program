package small_management_program.view.stages.billing;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import small_management_program.controller.parameters.WhereParameters;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.controller.queries.billing.BillCondosSelectLastWithParameter;
import small_management_program.controller.queries.condo.CondoSelectAll;
import small_management_program.controller.right.billing.DataStrategyBilling;
import small_management_program.model.Months;
import small_management_program.model.database.Database;
import small_management_program.model.database.DatabaseException;
import small_management_program.model.databaseclasses.BillingRepresentation;
import small_management_program.view.algorithms.AlgorithmsBills;
import small_management_program.view.annotation.AnnotationShowFXML;
import small_management_program.view.graphicutilities.ChoiceBoxItemId;
import small_management_program.view.graphicutilities.GraphicUtilities;

import java.net.URL;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ResourceBundle;

public class StageBillMonthController implements Initializable {

    @FXML
    private ChoiceBox choiceBoxCondos;

    @FXML
    private ChoiceBox choiceBoxMonths;

    @FXML
    private Button buttonAddBillMonth;

    private BillingRepresentation billingRepresentation;

    private String month;

    private static Integer idCondo = null;

    private ObservableList getMonths(BillingRepresentation billingRepresentation){
        ObservableList<ChoiceBoxItemId> months = FXCollections.observableArrayList();

        ObservableList<ChoiceBoxItemId> listBefore;
        ObservableList<ChoiceBoxItemId> listAfter;

        //Aggiungo i mesi possibili per inserire una fattura
        if(billingRepresentation.monthLastBill() <= -1) {
            listBefore = Months.getInstance().getListMonths(0, billingRepresentation.getRealMonth() - 1);
            listAfter = Months.getInstance().getListMonths(billingRepresentation.getRealMonth() + 1, 11);
        }
        else if(billingRepresentation.monthLastBill() <= billingRepresentation.getRealMonth()){
            listBefore = Months.getInstance().getListMonths(billingRepresentation.monthLastBill() + 1, billingRepresentation.getRealMonth());
            listAfter = Months.getInstance().getListMonths(billingRepresentation.getRealMonth() + 1, 11);
        }
        else {
            listBefore = Months.getInstance().getListMonths(0, billingRepresentation.getRealMonth() - 1);
            listAfter = Months.getInstance().getListMonths(billingRepresentation.monthLastBill() + 1, 11);
        }

        //Aggiungo gli item alla lista
        for(ChoiceBoxItemId month : listBefore){
            month.setMessage(Integer.valueOf(billingRepresentation.getYear()) + 1 + " - " + month.toString());
            months.add(month);
        }
        for(ChoiceBoxItemId month : listAfter){
            month.setMessage(Integer.valueOf(billingRepresentation.getYear()) + " - " + month.toString() );
            months.add(month);
        }
        return months;
    }

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
            if (newValue != null){
                try{
                    WhereParameters whereParameters = new WhereParameters("id_condo = " + newValue.hashCode());
                    whereParameters.setIdentifier("c", 0, 1);
                    QueryWithResults query = new BillCondosSelectLastWithParameter(whereParameters);
                    DataStrategyBilling dataStrategyBilling = new DataStrategyBilling();
                    BillingRepresentation item = ((ObservableList<BillingRepresentation>) dataStrategyBilling.getData(query)).get(0);
                    if(item.getTotal().equals("")){
                        GraphicUtilities.getInstance().showAlertError("Attenzione",
                                "Non \u00E8 possibile creare una fattura per il condominio selezionato.\n" +
                                        "Controllare di aver impostato un fatturato e di non aver gi\u00E0 creato una fattura per il mese corrente.");
                        this.buttonAddBillMonth.setDisable(true);
                        this.choiceBoxMonths.setDisable(true);
                    }
                    else{
                        this.billingRepresentation = item;
                        this.choiceBoxMonths.setItems(this.getMonths(item));
                        this.choiceBoxMonths.setDisable(false);
                    }
                }
                catch (DatabaseException ex){
                    GraphicUtilities.getInstance().showAlertError(ex);
                }
                catch (SQLException ex){
                    GraphicUtilities.getInstance().showAlertError("Operazione non riuscita", ex.getMessage());
                }
            }
        });

        this.choiceBoxMonths.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           if(newValue != null) {
               this.month = newValue.toString();
               this.buttonAddBillMonth.setDisable(false);
           }
        });

        this.choiceBoxMonths.setDisable(true);
        this.buttonAddBillMonth.setDisable(true);

        if (idCondo != null) {
            var condos = this.choiceBoxCondos.getItems();
            boolean found = false;
            for(Iterator<ChoiceBoxItemId> iterator = condos.iterator(); iterator.hasNext() && !found;){
                ChoiceBoxItemId item = iterator.next();
                if(idCondo == item.hashCode()){
                    found = true;
                    this.choiceBoxCondos.getSelectionModel().select(item);
                }
            }
            idCondo = null;
        }
    }


    public void stageGoal(){
        int year = Integer.valueOf(billingRepresentation.getYear());
        int month = choiceBoxMonths.getSelectionModel().getSelectedItem().hashCode();

        //Se il mese di fatturazione è antecendete al mese di chiusura dell'esercizio allora l'anno in cui effettuare la fattura è il successivo
        if(month <= billingRepresentation.getRealMonth())
            year++;

        if(AlgorithmsBills.getInstance().createBill(billingRepresentation, year, month, true)){
            GraphicUtilities.getInstance().showAlertSuccess("Operazione riuscita", "Fattura per il mese di " + this.month + " creata con successo.");
            this.buttonAddBillMonth.setDisable(true);
            this.choiceBoxMonths.setDisable(true);
            this.choiceBoxCondos.getSelectionModel().select(-1);
            this.choiceBoxMonths.setItems(FXCollections.observableArrayList());
        }

    }

    @AnnotationShowFXML(FXMLName = "/FXML/stages/billing/StageBillMonth.fxml", Tilte = "Crea fattura per un mese")
    public static void show(){}

    public static void setIdCondo(int idCondo){
        StageBillMonthController.idCondo = idCondo;
    }

    public void closeStage(ActionEvent event){}
}
