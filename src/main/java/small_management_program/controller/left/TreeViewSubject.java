package small_management_program.controller.left;

import javafx.scene.control.TreeItem;
import small_management_program.controller.Subject;
import small_management_program.controller.left.itemstrategy.TreeViewItemStrategy;
import small_management_program.controller.parameters.WhereParameters;
import small_management_program.model.database.DatabaseException;
import small_management_program.view.graphicutilities.TreeItemWhereParameters;

import java.sql.SQLException;

public class TreeViewSubject extends Subject {

    private static TreeViewSubject instance = null;

    private TreeViewItemStrategy strategy;

    private WhereParameters whereParameters;

    public static TreeViewSubject getInstance(){
        if (instance == null)
            instance = new TreeViewSubject();
        return instance;
    }

    private TreeViewSubject(){}

    public void setItemStrategy(TreeViewItemStrategy itemStrategy){
        this.strategy = itemStrategy;
        super.updateObservers();
    }

    @Override
    public TreeItem getTreeViewItems() throws DatabaseException, SQLException {

        TreeItem root = this.strategy.getTreeViewItems();
        this.whereParameters = ((TreeItemWhereParameters) root).getWhereParameters();
        return root;
    }


}
