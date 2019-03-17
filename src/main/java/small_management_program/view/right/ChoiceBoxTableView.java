package small_management_program.view.right;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import small_management_program.controller.left.TreeViewSubject;
import small_management_program.controller.right.condos.DataCondos;
import small_management_program.view.Command;
import small_management_program.view.MainViewController;
import small_management_program.view.right.condos.TableViewCondos;

public class ChoiceBoxTableView extends ChoiceBox {

    private ObservableList<Command> items = FXCollections.observableArrayList();

    MainViewController mainViewController;

    public ChoiceBoxTableView(MainViewController mainViewController){

        this.mainViewController = mainViewController;

        this.items.add(new TableViewCondosCommand());
        //this.items.add(new TableViewBillingCommand());
        this.setItems(items);

        this.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Command>) (ObservableValue<? extends Command> observableValue, Command oldCommand, Command newCommand) -> {
            if(newCommand != null){
                newCommand.execute();
            }
        });

    }

    private class TableViewCondosCommand implements Command{

        @Override
        public String toString(){
            return "Dati condomini";
        }

        @Override
        public void execute(){
            TreeViewSubject.getInstance().attach(DataCondos.getInstance());
            DataCondos.getInstance().attach(TableViewCondos.getInstance());
            ChoiceBoxTableView.this.mainViewController.setRightComponents(TableViewCondos.getInstance(), new HBox());
            //ChoiceBoxTableView.this.view.setTreeViewItemSelected();
        }
    }

    /*private class TableViewBillingCommand implements Command{

        @Override
        public String toString(){
            return "Dati fatturazione";
        }

        @Override
        public void execute(){
            DataBilling dataBilling = new DataBilling();
            ControllerFacade.getInstance().setTableViewData(dataBilling);
            TableViewBilling tableViewBilling = new TableViewBilling();
            dataBilling.attach(tableViewBilling);
            ChoiceBoxTableView.this.view.setRightComponents(tableViewBilling, tableViewBilling.getActions());
            ChoiceBoxTableView.this.view.setTreeViewItemSelected();
        }
    }
    */
}
