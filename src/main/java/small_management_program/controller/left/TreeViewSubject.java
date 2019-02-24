package small_management_program.controller.left;

import javafx.scene.control.TreeItem;
import small_management_program.controller.ControllerFacade;
import small_management_program.controller.UpdateException;
import small_management_program.controller.left.itemstrategy.TreeViewItemStrategy;
import small_management_program.controller.Subject;
import small_management_program.controller.parameters.WhereParameters;
import small_management_program.model.DatabaseException;
import small_management_program.view.graphicutilities.TreeItemWhereParameters;

public class TreeViewSubject extends Subject {

    //UPDATE FLAG
    private boolean updateTreeView = false;
    private boolean updateTable = false;

    private WhereParameters whereParameters;
    private TreeViewItemStrategy itemStrategy;

    @Override
    public TreeItem getTreeViewItems() throws DatabaseException, UpdateException {
        if(!this.updateTreeView)
            throw new UpdateException();
        TreeItem root = this.itemStrategy.getTreeViewItems();
        this.whereParameters = ((TreeItemWhereParameters) root).getWhereParameters();
        return root;
    }

    @Override
    public WhereParameters getWhereParameters() throws UpdateException{
        if(!this.updateTable){
            throw new UpdateException();
        }
        return this.whereParameters;
    }

    public void setItemStrategy(TreeViewItemStrategy itemStrategy){
        this.itemStrategy = itemStrategy;
        this.updateTreeView = true;
        super.updateObservers();
        this.updateTreeView = false;
    }

    public void setWhereParameters(WhereParameters whereParameters){
        this.updateTable = true;
        this.whereParameters = whereParameters;
        updateObservers();
        this.updateTable = false;
    }

    public void updateAll(){
        if (this.itemStrategy != null)
            setItemStrategy(this.itemStrategy);

        //Chiamo il controller perché li è inserito il controllo per verificare se la tabella è ancora nulla

        ControllerFacade.getInstance().setTreeViewSubjectWhereParameters(this.whereParameters);
    }
}
