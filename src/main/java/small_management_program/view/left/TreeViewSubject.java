package small_management_program.view.left;

import javafx.scene.control.TreeView;
import small_management_program.controller.Observer;
import small_management_program.controller.Subject;
import small_management_program.model.database.DatabaseException;

public class TreeViewSubject extends TreeView implements Observer {

    @Override
    public void update(Subject subject) throws DatabaseException {

    }
}
