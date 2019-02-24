package small_management_program.controller.left.itemstrategy;

import javafx.scene.control.TreeItem;
import small_management_program.model.DatabaseException;

public interface TreeViewItemStrategy {

    TreeItem getTreeViewItems() throws DatabaseException;
}
