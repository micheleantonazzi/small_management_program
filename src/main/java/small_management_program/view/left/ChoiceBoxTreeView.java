package small_management_program.view.left;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import small_management_program.controller.left.TreeViewSubject;
import small_management_program.controller.left.itemstrategy.TreeViewItemAllCondo;
import small_management_program.controller.left.itemstrategy.TreeViewItemMonths;
import small_management_program.controller.left.itemstrategy.TreeViewItemMonthsCondos;
import small_management_program.view.Command;

public class ChoiceBoxTreeView extends ChoiceBox{

    private static ChoiceBoxTreeView instance = null;

    private ObservableList<Command> items = FXCollections.observableArrayList();

    public static ChoiceBoxTreeView getInstance(){
        if (instance == null)
            instance = new ChoiceBoxTreeView();
        return instance;
    }

    private ChoiceBoxTreeView(){
        this.setMaxWidth(Double.POSITIVE_INFINITY);
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
            TreeViewSubject.getInstance().setItemStrategy(new TreeViewItemAllCondo());
        }
    }

    private class ItemViewMonths implements Command{

        @Override
        public String toString(){
            return "Mesi di chiusura";
        }

        @Override
        public void execute(){
            TreeViewSubject.getInstance().setItemStrategy(new TreeViewItemMonths());
        }
    }

    private class ItemViewMonthsCondos implements Command{

        @Override
        public String toString(){
            return "Condomini divisi per mese di chiusura";
        }

        @Override
        public void execute(){
            TreeViewSubject.getInstance().setItemStrategy(new TreeViewItemMonthsCondos());
        }
    }

}
