package small_management_program.view.right;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import small_management_program.controller.Observer;
import small_management_program.controller.left.TreeViewSubject;
import small_management_program.controller.right.billing.DataBilling;
import small_management_program.controller.right.condos.DataCondos;
import small_management_program.view.Command;
import small_management_program.view.MainViewController;
import small_management_program.view.left.TreeViewObserver;
import small_management_program.view.right.bill.TableViewBilling;
import small_management_program.view.right.condos.TableViewCondos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChoiceBoxTableView extends ChoiceBox {

    private ObservableList<Command> items = FXCollections.observableArrayList();
    private List<Observer> observers = new ArrayList<>(Arrays.asList(DataCondos.getInstance(), DataBilling.getInstance()));

    MainViewController mainViewController;

    public ChoiceBoxTableView(MainViewController mainViewController){

        this.mainViewController = mainViewController;

        this.items.add(new TableViewCondosCommand());
        this.items.add(new TableViewBillingCommand());
        this.setItems(items);

        this.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Command>) (ObservableValue<? extends Command> observableValue, Command oldCommand, Command newCommand) -> {
            if(newCommand != null){
                this.detachAll();
                newCommand.execute();
            }
        });

    }

    private void detachAll(){
        for (Observer observer : observers)
            TreeViewSubject.getInstance().detach(observer);
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
            if(TreeViewObserver.getInstance().getSelectionModel().getSelectedItem() == null)
                TreeViewObserver.getInstance().getSelectionModel().selectFirst();
            else
                TreeViewSubject.getInstance().setWhereParameters(TreeViewObserver.getInstance().getWhereParameters());
        }
    }

    private class TableViewBillingCommand implements Command{

        @Override
        public String toString(){
            return "Dati fatturazione";
        }

        @Override
        public void execute(){
            TreeViewSubject.getInstance().attach(DataBilling.getInstance());
            DataBilling.getInstance().attach(TableViewBilling.getInstance());
            ChoiceBoxTableView.this.mainViewController.setRightComponents(TableViewBilling.getInstance(), TableViewBilling.getInstance().getActions());
            if(TreeViewObserver.getInstance().getSelectionModel().getSelectedItem() == null)
                TreeViewObserver.getInstance().getSelectionModel().selectFirst();
            else
                TreeViewSubject.getInstance().setWhereParameters(TreeViewObserver.getInstance().getWhereParameters());
        }
    }

}
