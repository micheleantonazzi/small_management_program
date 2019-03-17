package small_management_program.view.left;

import javafx.scene.control.TreeView;
import small_management_program.controller.Observer;
import small_management_program.controller.Subject;
import small_management_program.controller.UpdateException;
import small_management_program.controller.left.TreeViewSubject;
import small_management_program.controller.parameters.WhereParameters;
import small_management_program.model.database.DatabaseException;
import small_management_program.view.graphicutilities.TreeItemWhereParameters;

import java.sql.SQLException;

public class TreeViewObserver extends TreeView implements Observer {

    private static TreeViewObserver instance;

    private TreeViewObserver(){
        this.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            if(newValue != null)
                TreeViewSubject.getInstance().setWhereParameters(((TreeItemWhereParameters) newValue).getWhereParameters());
        });
    }

    public static TreeViewObserver getInstance(){
        if (instance == null)
            instance = new TreeViewObserver();
        return instance;
    }

    @Override
    public void update(Subject subject) throws DatabaseException, SQLException {
        try {
            this.setRoot(subject.getTreeViewItems());

        }
        catch (UpdateException ex){}
    }

    public WhereParameters getWhereParameters(){
        return ((TreeItemWhereParameters) this.getSelectionModel().getSelectedItem()).getWhereParameters();
    }
}
