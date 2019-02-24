package small_management_program.view.left;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import small_management_program.controller.ControllerFacade;
//import small_management_program.controller.left.TreeViewAllConds;
import small_management_program.controller.left.TreeViewSubject;
import small_management_program.controller.left.itemstrategy.TreeViewItemAllCondo;
import small_management_program.controller.left.itemstrategy.TreeViewItemMonths;
import small_management_program.controller.left.itemstrategy.TreeViewItemMonthsCondos;
import small_management_program.view.Command;
import small_management_program.view.View;

public class ChoiceBoxTreeView extends ChoiceBox{

    private ObservableList<Command> items = FXCollections.observableArrayList();

    public ChoiceBoxTreeView(){
        items.add(new ItemViewAllCondos());
        items.add(new ItemViewMonths());
        items.add(new ItemViewMonthsCondos());
        this.setItems(items);

        this.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Command>) (ObservableValue<? extends Command> observableValue, Command oldCommand, Command newCommand) -> {
            if(newCommand != null){
                newCommand.execute();
            }
        });
    }

    //------------------- COMMAND -------------//

    private class ItemViewAllCondos implements Command{

        @Override
        public String toString(){
            return "Tutti i condomini";
        }

        @Override
        public void execute(){
            ControllerFacade.getInstance().setTreeViewItemStrategy(new TreeViewItemAllCondo());
        }
    }

    private class ItemViewMonths implements Command{

        @Override
        public String toString(){
            return "Mesi di chiusura";
        }

        @Override
        public void execute(){
            ControllerFacade.getInstance().setTreeViewItemStrategy(new TreeViewItemMonths());
        }
    }

    private class ItemViewMonthsCondos implements Command{

        @Override
        public String toString(){
            return "Condomini divisi per mese di chiusura";
        }

        @Override
        public void execute(){
            ControllerFacade.getInstance().setTreeViewItemStrategy(new TreeViewItemMonthsCondos());
        }
    }
}
