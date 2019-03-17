package small_management_program.controller.left;

import javafx.scene.control.TreeItem;
import small_management_program.controller.Subject;
import small_management_program.controller.UpdateException;
import small_management_program.controller.left.itemstrategy.TreeViewItemStrategy;
import small_management_program.controller.parameters.WhereParameters;
import small_management_program.model.database.DatabaseException;
import small_management_program.view.graphicutilities.TreeItemWhereParameters;

import java.sql.SQLException;

public class TreeViewSubject extends Subject {

    //UPDATE FLAG
    private boolean updateTreeView = false;
    private boolean updateTable = false;


    private static TreeViewSubject instance = null;

    private TreeViewItemStrategy treeViewItemStrategy;

    private WhereParameters whereParameters;

    public static TreeViewSubject getInstance(){
        if (instance == null)
            instance = new TreeViewSubject();
        return instance;
    }

    private TreeViewSubject(){}

    public void setItemStrategy(TreeViewItemStrategy itemStrategy){
        this.treeViewItemStrategy = itemStrategy;
        this.updateTreeView = true;
        super.updateObservers();
        this.updateTreeView = false;
    }

    @Override
    public TreeItem getTreeViewItems() throws DatabaseException, SQLException, UpdateException {
        if(!this.updateTreeView)
            throw new UpdateException();

        TreeItem root = this.treeViewItemStrategy.getTreeViewItems();
        this.whereParameters = ((TreeItemWhereParameters) root).getWhereParameters();
        return root;
    }

    public void setWhereParameters(WhereParameters whereParameters){
        this.updateTable = true;
        this.whereParameters = whereParameters;
        updateObservers();
        this.updateTable = false;
    }

    @Override
    public WhereParameters getWhereParameters() throws UpdateException {
        if(!this.updateTable){
            throw new UpdateException();
        }
        return this.whereParameters;
    }

    public void updateAll(){
        if (this.treeViewItemStrategy != null)
            setItemStrategy(this.treeViewItemStrategy);

        //Chiamo il controller perché li è inserito il controllo per verificare se la tabella è ancora nulla

        TreeViewSubject.getInstance().setWhereParameters(this.whereParameters);
    }
}
