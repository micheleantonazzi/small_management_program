package small_management_program.view.graphicutilities;

import javafx.scene.control.TreeItem;
import small_management_program.controller.parameters.WhereParameters;

public class TreeItemWhereParameters extends TreeItem {

    private WhereParameters whereParameters;

    public TreeItemWhereParameters(String text, WhereParameters whereParameters){
        super(text);
        this.whereParameters = whereParameters;
    }

    public TreeItemWhereParameters(String text, String whereParameters){
        this(text, new WhereParameters(whereParameters));
    }

    public WhereParameters getWhereParameters() {
        return this.whereParameters.clone();
    }
}
