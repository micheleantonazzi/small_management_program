package small_management_program.view.left;

import javafx.scene.control.TreeView;
import small_management_program.controller.Observer;
import small_management_program.controller.Subject;
import small_management_program.controller.UpdateException;
import small_management_program.model.database.DatabaseException;

import java.sql.SQLException;

public class TreeViewObserver extends TreeView implements Observer {

    private static TreeViewObserver instance;

    private TreeViewObserver(){}

    public static TreeViewObserver getInstance(){
        if (instance == null)
            instance = new TreeViewObserver();
        return instance;
    }

    @Override
    public void update(Subject subject) throws DatabaseException {
        try{
            this.setRoot(subject.getTreeViewItems());
        }

        catch (SQLException ex){ }


    }
}
