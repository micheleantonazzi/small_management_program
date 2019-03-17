package small_management_program.controller.left.itemstrategy;

import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import small_management_program.controller.DuplicateMap;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.controller.queries.condo.CondoSelectMonthsCondos;
import small_management_program.model.database.Database;
import small_management_program.model.database.DatabaseException;
import small_management_program.model.Months;
import small_management_program.view.graphicutilities.TreeItemWhereParameters;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

public class TreeViewItemMonthsCondos implements TreeViewItemStrategy {

    @Override
    public TreeItem getTreeViewItems() throws DatabaseException, SQLException {
        QueryWithResults query = new CondoSelectMonthsCondos();
        Database.getInstance().executeQuery(query);
        DuplicateMap<Integer, String> results = query.getResults();

        TreeItem root = new TreeItemWhereParameters("Tutti i condomini", "1 = 1");
        root.setGraphic(new ImageView(this.getClass().getResource("/images/icons/home-modern.png").toString()));
        root.setExpanded(true);

        Set<Integer> months = results.keySet();
        for(Integer month : months){
            TreeItem itemMonth = new TreeItemWhereParameters(Months.getInstance().getMonthName(month), "month = " + month);
            itemMonth.setGraphic(new ImageView(this.getClass().getResource("/images/icons/calendar.png").toString()));
            root.getChildren().add(itemMonth);

            ArrayList<String> condos = results.get(month);
            for(int i = 0; i < condos.size(); ++i){
                String code = condos.get(i++);
                String name = condos.get(i);
                TreeItem item = new TreeItemWhereParameters(code + " - " + name, "id_condo = " +  code);
                item.setGraphic(new ImageView(this.getClass().getResource("/images/icons/home.png").toString()));
                itemMonth.getChildren().add(item);
            }
        }
        return root;
    }
}
