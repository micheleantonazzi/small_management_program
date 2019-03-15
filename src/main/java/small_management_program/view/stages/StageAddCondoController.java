package small_management_program.view.stages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.controller.queries.administrator.AdministratorQueryWithResults;
import small_management_program.controller.queries.administrator.AdministratorSelectAll;
import small_management_program.model.Months;
import small_management_program.model.database.Database;
import small_management_program.model.database.DatabaseException;
import small_management_program.view.graphicutilities.GraphicUtilities;

import javax.xml.crypto.Data;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StageAddCondoController implements Initializable {

    @FXML
    private ChoiceBox choiceBoxAdministrators;

    @FXML
    private ChoiceBox choiceBoxMonths;

    @FXML
    private Button buttonAddCondo;

    @FXML
    private Button buttonExit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            //Load Administrator
            AdministratorQueryWithResults getAdministrator = new AdministratorSelectAll();
            Database.getInstance().executeQuery(getAdministrator);
            this.choiceBoxAdministrators.setItems(getAdministrator.getChoiceBoxItems());

            //Months
            this.choiceBoxMonths.setItems(Months.getInstance().getListAllMonths());


        }
        catch (DatabaseException ex){
            GraphicUtilities.getInstance().showAlertError(ex);
        }
        catch (SQLException ex){
            GraphicUtilities.getInstance().showAlertError("Operazione non riuscita", ex.getMessage());
        }

    }

    public void closeStage(ActionEvent event){}
}
