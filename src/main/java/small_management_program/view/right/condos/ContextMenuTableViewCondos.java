package small_management_program.view.right.condos;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.image.ImageView;
import small_management_program.controller.left.TreeViewSubject;
import small_management_program.controller.queries.Query;
import small_management_program.controller.queries.condo.CondoDelete;
import small_management_program.model.database.Database;
import small_management_program.model.database.DatabaseException;
import small_management_program.model.databaseclasses.CondoRepresentation;
import small_management_program.view.graphicutilities.GraphicUtilities;
import small_management_program.view.stages.StageModifyCondoController;

import java.sql.SQLException;

public class ContextMenuTableViewCondos extends ContextMenu {

    TableRow<CondoRepresentation> row;

    MenuItem menuItemModifyCondo;
    MenuItem menuItemDeleteCondo;

    public ContextMenuTableViewCondos(TableRow<CondoRepresentation> row){

        this.row = row;

        this.getItems().addAll(this.getMenuItemModifyCondo(), this.getMenuItemDeleteCondo());
    }

    private MenuItem getMenuItemModifyCondo(){

        this.menuItemModifyCondo = new MenuItem("Modifica condominio");
        this.menuItemModifyCondo.setGraphic(new ImageView(this.getClass().getResource("/images/icons/home-modify.png").toString()));
        this.menuItemModifyCondo.setOnAction(event -> {
            event.consume();
            StageModifyCondoController.setIdCondo(row.getItem().getId());
            StageModifyCondoController.show();
        });

        return this.menuItemModifyCondo;
    }

    private MenuItem getMenuItemDeleteCondo(){
        this.menuItemDeleteCondo = new MenuItem("Elimina condominio");
        this.menuItemDeleteCondo.setGraphic(new ImageView(this.getClass().getResource("/images/icons/home-delete.png").toString()));

        this.menuItemDeleteCondo.setOnAction(event -> {
            event.consume();
            if(GraphicUtilities.getInstance().showAlertConfirmationDelete("Elimina condominio",
                    "Attenzione, sei sicuro di voler eliminare questo condominio?")){
                try {
                    Query condoDelete = new CondoDelete(this.row.getItem().getId());
                    Database.getInstance().executeQuery(condoDelete);
                    TreeViewSubject.getInstance().updateAll();
                }
                catch (DatabaseException exception){
                    GraphicUtilities.getInstance().showAlertError(exception);
                }
                catch (SQLException ex){
                    GraphicUtilities.getInstance().showAlertError("Operazione non riuscita", ex.getMessage());
                }
            }
        });

        return this.menuItemDeleteCondo;
    }
}
