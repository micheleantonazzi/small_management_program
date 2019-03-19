package small_management_program.view.stages.billing;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import small_management_program.controller.parameters.WhereParameters;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.controller.queries.billing.BillCondosSelectLastWithParameter;
import small_management_program.controller.queries.condo.CondoSelectAll;
import small_management_program.controller.right.billing.DataBilling;
import small_management_program.controller.right.billing.DataStrategyBilling;
import small_management_program.model.database.Database;
import small_management_program.model.database.DatabaseException;
import small_management_program.model.databaseclasses.BillingRepresentation;
import small_management_program.view.algorithms.AlgorithmsBills;
import small_management_program.view.graphicutilities.GraphicUtilities;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StageBillMonth implements Initializable {

    @FXML
    private ChoiceBox choiceBoxCondos;

    @FXML
    private ChoiceBox choiceBoxMonths;

    @FXML
    private Button buttonAddBillMonth;

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
                    System.out.println(query.getQuery());
                    BillingRepresentation item = ((ObservableList<BillingRepresentation>) dataStrategyBilling.getData(query)).get(0);
                    System.out.println(item.getId());
                    if(item.getTotal().equals("") || !AlgorithmsBills.getInstance().isPossible(item)){
                        GraphicUtilities.getInstance().showAlertError("Attenzione",
                                "Non \u00E8 possibile creare una fattura per il condominio selezionato.\n" +
                                        "Controllare di aver impostato un fatturato e di non aver gi\u00E0 creato una fattura per il mese corrente.");
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

        this.choiceBoxMonths.setDisable(true);
        this.buttonAddBillMonth.setDisable(true);
    }
}
