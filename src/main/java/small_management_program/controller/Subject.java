package small_management_program.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import small_management_program.controller.parameters.WhereParameters;
import small_management_program.model.DatabaseException;
import small_management_program.view.graphicutilities.GraphicUtilities;

import java.util.ArrayList;

public abstract class Subject{
    private ArrayList<Observer> observers = new ArrayList<>();

    public void updateObservers(){
        for(Observer observer : observers) {
            try{
                observer.update(this);
            }
            catch (DatabaseException exception){
                GraphicUtilities.getInstance().showAlertError(exception);
            }
        }
    }

    public final void attach(Observer observer){
        this.observers.add(observer);
    }

    public final void detach(Observer observer){
        this.observers.remove(observer);
    }

    public WhereParameters getWhereParameters() throws UpdateException{
        return new WhereParameters();
    }

    public ObservableList getTableViewData() throws UpdateException{
        return FXCollections.observableArrayList();
    }

    public TreeItem getTreeViewItems() throws DatabaseException, UpdateException{
        return new TreeItem();
    }
}
