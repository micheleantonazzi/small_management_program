package small_management_program.controller.left.itemstrategy;

import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import small_management_program.controller.DuplicateMap;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.controller.parameters.WhereParameters;
import small_management_program.controller.queries.condo.CondoSelectAll;
import small_management_program.model.database.Database;
import small_management_program.model.database.DatabaseException;
import small_management_program.view.graphicutilities.TreeItemWhereParameters;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

public class TreeViewItemAllCondo implements TreeViewItemStrategy {

    @Override
    public TreeItem getTreeViewItems() throws DatabaseException, SQLException {
        QueryWithResults query = new CondoSelectAll();
        Database.getInstance().executeQuery(query);
        DuplicateMap<Integer, String> results = query.getResults();
        TreeItemWhereParameters root = new TreeItemWhereParameters("Tutti i condimini", new WhereParameters("1 = 1"));
        root.setGraphic(new ImageView(this.getClass().getResource("/images/icons/home-modern.png").toString()));
        Set<Integer> ids = results.keySet();
        for(Iterator<Integer> it = ids.iterator(); it.hasNext();){
            int id = it.next();
            WhereParameters whereParameters = new WhereParameters("id_condo = " + id);
            TreeItemWhereParameters treeItemId = new TreeItemWhereParameters(String.valueOf(id) + " - " + results.get(id, 3), whereParameters);
            treeItemId.setGraphic(new ImageView(this.getClass().getResource("/images/icons/home.png").toString()));
            root.getChildren().add(treeItemId);
        }
        root.setExpanded(true);
        return root;
    }
}
