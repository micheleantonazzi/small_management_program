package small_management_program.view.left;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeView;
import small_management_program.controller.ControllerFacade;
import small_management_program.controller.Observer;
import small_management_program.controller.Subject;
import small_management_program.controller.UpdateException;
import small_management_program.model.DatabaseException;
import small_management_program.view.graphicutilities.GraphicUtilities;
import small_management_program.view.graphicutilities.TreeItemWhereParameters;

public class TreeViewObserver extends TreeView implements Observer {

    ChangeListener<TreeItemWhereParameters> listener = (ObservableValue<? extends TreeItemWhereParameters> observable, TreeItemWhereParameters oldValue,
    TreeItemWhereParameters newValue) -> ControllerFacade.getInstance().setTreeViewSubjectWhereParameters(newValue.getWhereParameters());

    public TreeViewObserver(){
        this.getSelectionModel().selectedItemProperty().addListener(this.listener);
    }

    @Override
    public void update(Subject subject){
        try {
            this.getSelectionModel().selectedItemProperty().removeListener(this.listener);
            this.setRoot(subject.getTreeViewItems());
        }
        catch (DatabaseException exception){
            GraphicUtilities.getInstance().showAlertError(exception);
        }
        catch (UpdateException exception){}
        finally {
            this.getSelectionModel().selectedItemProperty().addListener(this.listener);
        }
    }
}
