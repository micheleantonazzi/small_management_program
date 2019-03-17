package small_management_program.controller.left.itemstrategy;

import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import small_management_program.controller.DuplicateMap;
import small_management_program.controller.queries.QueryWithResults;
import small_management_program.controller.queries.condo.CondoSelectMonths;
import small_management_program.model.database.Database;
import small_management_program.model.database.DatabaseException;
import small_management_program.model.Months;
import small_management_program.view.graphicutilities.TreeItemWhereParameters;

import java.sql.SQLException;
import java.util.List;

public class TreeViewItemMonths implements TreeViewItemStrategy {

    @Override
    public TreeItem getTreeViewItems() throws DatabaseException, SQLException {
        QueryWithResults queryWithResults = new CondoSelectMonths();
        Database.getInstance().executeQuery(queryWithResults);
        DuplicateMap<Integer, String> results = queryWithResults.getResults();
        List<String> months = results.get(0);

        TreeItemWhereParameters root = new TreeItemWhereParameters("Tutti i mesi di chiusura", "1 = 1");
        root.setExpanded(true);
        root.setGraphic(new ImageView(this.getClass().getResource("/images/icons/calendar-blank.png").toString()));

        for(String month : months){
            TreeItem item = new TreeItemWhereParameters(Months.getInstance().getMonthName(Integer.valueOf(month)), "month = " + month);
            item.setGraphic(new ImageView(this.getClass().getResource("/images/icons/calendar.png").toString()));
            root.getChildren().add(item);
        }
        return root;
    }
}
