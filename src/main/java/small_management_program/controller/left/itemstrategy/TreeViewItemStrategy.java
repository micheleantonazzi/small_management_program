package small_management_program.controller.left.itemstrategy;

import javafx.scene.control.TreeItem;
import small_management_program.model.database.DatabaseException;

import java.sql.SQLException;

public interface TreeViewItemStrategy {

    TreeItem getTreeViewItems() throws DatabaseException, SQLException;
}
